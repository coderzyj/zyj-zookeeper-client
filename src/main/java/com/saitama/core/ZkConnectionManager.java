package com.saitama.core;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    public void getNodes(Node node) throws Exception {
        String path = node.getPath();
        System.out.println(path);
        List<String> children = client.getChildren().forPath(path);
        if(CollectionUtil.isEmpty(children)){
            return;
        }
        node.getChildren().addAll(children.stream().map(ch->{
            Node n = new Node();
            n.setName(ch);
            n.setPath(node.getPath().equals("/")?node.getPath()+ch:node.getPath()+"/"+ch);
            return n;
        }).collect(Collectors.toList()));
        for (Node no : node.getChildren()) {
            getNodes(no);
        }
    }

    /**
     * get node meta data info
     * @param path
     * @return
     * @throws Exception
     */
    public Map<String,Object> getNodeMetaData(String path) throws Exception {
        Stat stat = new Stat();
        Map<String,Object> result = Maps.newHashMap();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        if(bytes != null && bytes.length > 0){
            result.put("value",new String(bytes));
        }
        result.put("stat",stat);
        return result;
    }


    public static void main(String[] args) throws Exception {
//        CuratorFramework client = CuratorFrameworkFactory.builder()
//                .connectString("localhost:2181")
//                .connectionTimeoutMs(StringUtils.isBlank("1000") ? DEFAULT_TIMEOUT : Integer.parseInt("1000"))
//                .sessionTimeoutMs(SESSION_TIMEOUT)
//                .retryPolicy(new RetryOneTime(1000))
//                .build();
//        client.start();
//        client.blockUntilConnected();

//        List<String> list = client.getChildren().forPath("/zookeeper");

        ZkConnectionManager zk = new ZkConnectionManager();
        zk.client=CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .connectionTimeoutMs(StringUtils.isBlank("1000") ? DEFAULT_TIMEOUT : Integer.parseInt("1000"))
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(new RetryOneTime(1000))
                .build();
        zk.client.start();
        zk.client.blockUntilConnected();
        Node node = new Node();
        node.setPath("/");
        node.setName("/");
        zk.getNodes(node);
        System.out.println(node);

//        System.out.println(list);

        Stat stat = new Stat();

        List<ACL> acls = zk.client.getACL().forPath("/dubbo");
        byte[] bytes = zk.client.getData().storingStatIn(stat).forPath("/test");
        System.out.println("数据为："+new String(bytes));
        System.out.println("stat为："+stat);
        System.out.println(acls);
    }
}
