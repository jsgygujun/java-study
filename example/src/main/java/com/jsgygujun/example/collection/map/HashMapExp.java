package com.jsgygujun.example.collection.map;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author jsgygujun@gmail.com
 * @since 2020/8/4 2:22 下午
 */
public class HashMapExp {

    @Test
    public void create() {
        // 默认负载因子0.75，初使容量16，最大容量2^30
        // 链表碰撞超过8则转化为红黑树
        HashMap<Integer, Integer> map = new HashMap<>();
    }
}
