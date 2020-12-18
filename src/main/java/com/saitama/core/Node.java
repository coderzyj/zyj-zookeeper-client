package com.saitama.core;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhangyongjie
 * @version : 1.0
 * @createTime : 2020/12/18 10:47
 * @description : zk节点
 */
@Data
public class Node {

    private String name;

    private String path;

    private List<Node> children = new ArrayList<>();
}
