package com.saitama.core;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zyj
 * @time: 2020/12/18  8:02
 * @description: zookeeper连接能力提供
 */
@Component
public class ZkConnectionManager {

    private  CuratorFramework  client;

    /**
     * session time out
     */
    private static final int SESSION_TIMEOUT = 30 * 1000;

    /**
     * connect time out
     */
    private static final int DEFAULT_TIMEOUT = 1000;

    public  void connect(String host, String port, String timeout) throws InterruptedException {
        client = CuratorFrameworkFactory.builder()
                .connectString(host + ":" + port)
                .connectionTimeoutMs(StringUtils.isBlank(timeout) ? DEFAULT_TIMEOUT : Integer.parseInt(timeout))
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(new RetryOneTime(1000))
                .build();
        client.start();
        client.blockUntilConnected();



    }

    public List<String> getAllNode() throws Exception {
        return client.getChildren().forPath("/");
    }

}
