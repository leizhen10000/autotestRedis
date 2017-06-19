package com.dianwoba.cn.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leizhen on 2017/5/16 0016.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class RedisDto implements Serializable {

    private static final long serialVersionUID = 295331103146071402L;

    private String content;

    private List<String> orderIdList;

    public List<String> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<String> orderIdList) {
        this.orderIdList = orderIdList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
