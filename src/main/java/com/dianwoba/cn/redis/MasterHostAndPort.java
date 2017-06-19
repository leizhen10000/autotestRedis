package com.dianwoba.cn.redis;

import redis.clients.jedis.HostAndPort;

/**
 * Created by leizhen on 2017/5/16 0016.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class MasterHostAndPort {
    private String masterName;

    private HostAndPort hostAndPort;

    public MasterHostAndPort(String masterName, String host, int port) {
        this.hostAndPort = new HostAndPort(host, port);
        this.masterName = masterName;
    }

    public MasterHostAndPort(String masterName, HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
        this.masterName = masterName;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }

    public String getHost() {
        return hostAndPort.getHost();
    }

    public int getPort() {
        return hostAndPort.getPort();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MasterHostAndPort) {
            MasterHostAndPort hp = (MasterHostAndPort) obj;
            return this.masterName.equals(hp.getMasterName()) && this.hostAndPort.equals(hp.getHostAndPort());
        }
        return false;
    }

    @Override
    public String toString() {
        return masterName + "@" + hostAndPort;
    }
}
