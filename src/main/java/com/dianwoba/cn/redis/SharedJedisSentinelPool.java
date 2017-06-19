package com.dianwoba.cn.redis;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;


/**
 * Created by leizhen on 2017/5/16 0016.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class SharedJedisSentinelPool extends Pool<ShardedJedis> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int MAX_RETRY_SENTINEL = 10;

    protected GenericObjectPoolConfig poolConfig;

    protected int timeout = Protocol.DEFAULT_TIMEOUT;

    private int sentinelRetry = 0;

    protected String password;

    protected int database = Protocol.DEFAULT_DATABASE;

    protected Set<MasterListener> masterListeners = new HashSet<MasterListener>();

    private volatile List<MasterHostAndPort> currentHostMasters;

    public SharedJedisSentinelPool(String masterNames, Set<String> sentinels) {
        this(masterNames, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, null,
                Protocol.DEFAULT_DATABASE);
    }

    public SharedJedisSentinelPool(String masterNames, Set<String> sentinels, String password) {
        this(masterNames, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, password);
    }

    public SharedJedisSentinelPool(final GenericObjectPoolConfig poolConfig, String masterNames,
                                    Set<String> sentinels) {
        this(masterNames, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
    }

    public SharedJedisSentinelPool(String masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig,
                                    int timeout, final String password) {
        this(masterNames, sentinels, poolConfig, timeout, password, Protocol.DEFAULT_DATABASE);
    }

    public SharedJedisSentinelPool(String masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig,
                                    final int timeout) {
        this(masterNames, sentinels, poolConfig, timeout, null, Protocol.DEFAULT_DATABASE);
    }

    public SharedJedisSentinelPool(String masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig,
                                    final String password) {
        this(masterNames, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, password);
    }

    public SharedJedisSentinelPool(String masterNames, Set<String> sentinels, final GenericObjectPoolConfig poolConfig,
                                    int timeout, final String password, final int database) {
        this.poolConfig = poolConfig;
        this.timeout = timeout;
        this.password = password;
        this.database = database;
        if (StringUtils.isEmpty(masterNames)) {
            logger.error("masters can't be empty");
            throw new RuntimeException("masters can't be empty");
        }
        List<String> masters = new ArrayList<String>(Arrays.asList(masterNames.split(",")));
        List<MasterHostAndPort> masterList = initSentinels(sentinels, masters);
        initPool(masterList);
    }

    @Override
    public void destroy() {
        for (MasterListener m : masterListeners) {
            m.shutdown();
        }

        super.destroy();
    }

    public List<MasterHostAndPort> getCurrentHostMaster() {
        return currentHostMasters;
    }

    private void initPool(List<MasterHostAndPort> masters) {
        if (!equals(currentHostMasters, masters)) {
            StringBuffer sb = new StringBuffer();
            for (MasterHostAndPort master : masters) {
                sb.append(master.toString());
                sb.append(" ");
            }
            logger.info("Created ShardedJedisPool to master at [" + sb.toString() + "]");
            List<JedisShardInfo> shardMasters = makeShardInfoList(masters);
            initPool(poolConfig, new ShardedJedisFactory(shardMasters, Hashing.MURMUR_HASH, null));
            currentHostMasters = masters;
        } else {
            logger.info("current:{}, new:{}, equal", currentHostMasters, masters);
        }
    }

    private boolean equals(List<MasterHostAndPort> currentShardMasters, List<MasterHostAndPort> shardMasters) {
        if (currentShardMasters != null && shardMasters != null) {
            if (currentShardMasters.size() == shardMasters.size()) {
                for (int i = 0; i < currentShardMasters.size(); i++) {
                    if (!currentShardMasters.get(i).equals(shardMasters.get(i)))
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    private List<JedisShardInfo> makeShardInfoList(List<MasterHostAndPort> masters) {
        List<JedisShardInfo> shardMasters = new ArrayList<JedisShardInfo>();
        for (MasterHostAndPort master : masters) {
            JedisShardInfo jedisShardInfo = new JedisShardInfo(master.getHost(), master.getPort(), timeout,
                    master.getMasterName());
            jedisShardInfo.setPassword(password);

            shardMasters.add(jedisShardInfo);
        }
        return shardMasters;
    }

    private List<MasterHostAndPort> initSentinels(Set<String> sentinels, final List<String> masters) {

        Map<String, HostAndPort> masterMap = new HashMap<String, HostAndPort>();
        List<MasterHostAndPort> shardMasters = new ArrayList<MasterHostAndPort>();

        logger.info("Trying to find all master from available Sentinels...");

        for (String masterName : masters) {
            HostAndPort master = null;
            boolean fetched = false;

            while (!fetched && sentinelRetry < MAX_RETRY_SENTINEL) {
                for (String sentinel : sentinels) {
                    final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));

                    logger.info("Connecting to Sentinel " + hap);

                    try {
                        @SuppressWarnings("resource")
                        Jedis jedis = new Jedis(hap.getHost(), hap.getPort());
                        master = masterMap.get(masterName);
                        if (master == null) {
                            List<String> hostAndPort = jedis.sentinelGetMasterAddrByName(masterName);
                            if (hostAndPort != null && hostAndPort.size() > 0) {
                                master = toHostAndPort(hostAndPort);
                                logger.info("Found RedisService master at " + master);
                                shardMasters.add(new MasterHostAndPort(masterName, master));
                                masterMap.put(masterName, master);
                                fetched = true;
                                jedis.disconnect();
                                break;
                            }
                        }
                    } catch (JedisConnectionException e) {
                        logger.warn("Cannot connect to sentinel running @ " + hap + ". Trying next one.");
                    }
                }

                if (null == master) {
                    try {
                        logger.error("All sentinels down, cannot determine where is " + masterName
                                + " master is running... sleeping 1000ms, Will try again.");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fetched = false;
                    sentinelRetry++;
                }
            }

            // Try MAX_RETRY_SENTINEL times.
            if (!fetched && sentinelRetry >= MAX_RETRY_SENTINEL) {
                logger.error("All sentinels down and try " + MAX_RETRY_SENTINEL + " times, Abort.");
                throw new JedisConnectionException("Cannot connect all sentinels, Abort.");
            }
        }

        // All shards master must been accessed.
        if (masters.size() != 0 && masters.size() == shardMasters.size()) {

            logger.info("Starting Sentinel listeners...");
            for (String sentinel : sentinels) {
                final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
                MasterListener masterListener = new MasterListener(masters, hap.getHost(), hap.getPort());
                masterListeners.add(masterListener);
                masterListener.start();
            }
        }

        return shardMasters;
    }

    private HostAndPort toHostAndPort(List<String> getMasterAddrByNameResult) {
        String host = getMasterAddrByNameResult.get(0);
        int port = Integer.parseInt(getMasterAddrByNameResult.get(1));

        return new HostAndPort(host, port);
    }

    /**
     * PoolableObjectFactory custom impl.
     */
    protected static class ShardedJedisFactory implements PooledObjectFactory<ShardedJedis> {
        private List<JedisShardInfo> shards;
        private Hashing algo;
        private Pattern keyTagPattern;

        public ShardedJedisFactory(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
            this.shards = shards;
            this.algo = algo;
            this.keyTagPattern = keyTagPattern;
        }

        public PooledObject<ShardedJedis> makeObject() throws Exception {
            ShardedJedis jedis = new ShardedJedis(shards, algo, keyTagPattern);
            return new DefaultPooledObject<ShardedJedis>(jedis);
        }

        public void destroyObject(PooledObject<ShardedJedis> pooledShardedJedis) throws Exception {
            final ShardedJedis shardedJedis = pooledShardedJedis.getObject();
            for (Jedis jedis : shardedJedis.getAllShards()) {
                try {
                    try {
                        jedis.quit();
                    } catch (Exception e) {

                    }
                    jedis.disconnect();
                } catch (Exception e) {

                }
            }
        }

        public boolean validateObject(PooledObject<ShardedJedis> pooledShardedJedis) {
            try {
                ShardedJedis jedis = pooledShardedJedis.getObject();
                for (Jedis shard : jedis.getAllShards()) {
                    if (!shard.ping().equals("PONG")) {
                        return false;
                    }
                }
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        public void activateObject(PooledObject<ShardedJedis> p) throws Exception {

        }

        public void passivateObject(PooledObject<ShardedJedis> p) throws Exception {

        }
    }

    protected class JedisPubSubAdapter extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
        }
    }

    protected class MasterListener extends Thread {

        protected List<String> masters;
        protected String host;
        protected int port;
        protected long subscribeRetryWaitTimeMillis = 5000;
        protected Jedis jedis;
        protected AtomicBoolean running = new AtomicBoolean(false);

        protected MasterListener() {
        }

        public MasterListener(List<String> masters, String host, int port) {
            this.masters = masters;
            this.host = host;
            this.port = port;
        }

        public MasterListener(List<String> masters, String host, int port, long subscribeRetryWaitTimeMillis) {
            this(masters, host, port);
            this.subscribeRetryWaitTimeMillis = subscribeRetryWaitTimeMillis;
        }

        @Override
        public void run() {

            running.set(true);

            while (running.get()) {

                jedis = new Jedis(host, port);

                try {
                    jedis.subscribe(new JedisPubSubAdapter() {
                        @Override
                        public void onMessage(String channel, String message) {
                            logger.info("Sentinel " + host + ":" + port + " published: " + message + ".");

                            String[] switchMasterMsg = message.split(" ");

                            if (switchMasterMsg.length > 3) {

                                int index = masters.indexOf(switchMasterMsg[0]);
                                if (index >= 0) {
                                    HostAndPort newHostMaster = toHostAndPort(
                                            Arrays.asList(switchMasterMsg[3], switchMasterMsg[4]));
                                    List<MasterHostAndPort> newHostMasters = new ArrayList<MasterHostAndPort>();
                                    for (int i = 0; i < masters.size(); i++) {
                                        newHostMasters.add(null);
                                    }
                                    Collections.copy(newHostMasters, currentHostMasters);
                                    newHostMasters.set(index, new MasterHostAndPort(switchMasterMsg[0], newHostMaster));

                                    initPool(newHostMasters);
                                } else {
                                    StringBuffer sb = new StringBuffer();
                                    for (String masterName : masters) {
                                        sb.append(masterName);
                                        sb.append(",");
                                    }
                                    logger.info("Ignoring message on +switch-master for master name "
                                            + switchMasterMsg[0] + ", our monitor master name are [" + sb + "]");
                                }

                            } else {
                                logger.error("Invalid message received on Sentinel " + host + ":" + port
                                        + " on channel +switch-master: " + message);
                            }
                        }
                    }, "+switch-master");

                } catch (JedisConnectionException e) {

                    if (running.get()) {
                        logger.error("Lost connection to Sentinel at " + host + ":" + port
                                + ". Sleeping 5000ms and retrying.");
                        try {
                            Thread.sleep(subscribeRetryWaitTimeMillis);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        logger.info("Unsubscribing from Sentinel at " + host + ":" + port);
                    }
                }
            }
        }

        public void shutdown() {
            try {
                logger.info("Shutting down listener on " + host + ":" + port);
                running.set(false);
                // This isn't good, the Jedis object is not thread safe
                jedis.disconnect();
            } catch (Exception e) {
                logger.error("Caught exception while shutting down: " + e.getMessage());
            }
        }
    }
}