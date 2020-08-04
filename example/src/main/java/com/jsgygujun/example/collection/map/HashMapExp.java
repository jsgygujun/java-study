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

    @Test
    public void put() {
        /**
         * put 函数流程
         * 1. 对key的hashCode()做hash，然后再计算index;
         * 2. 如果没碰撞直接放到bucket里；
         * 3. 如果碰撞了，以链表的形式存在buckets后；
         * 4. 如果碰撞导致链表过长(大于等于TREEIFY_THRESHOLD)，就把链表转换成红黑树；
         * 5. 如果节点已经存在就替换old value(保证key的唯一性)
         * 6. 如果bucket满了(超过load factor*current capacity)，就要resize。
         */
    }

    @Test
    public void get() {
        /**
         * get 函数流程
         * 1. bucket里的第一个节点，直接命中；
         * 2. 如果有冲突，则通过key.equals(k)去查找对应的entry；
         * 3. 若为树，则在树中通过key.equals(k)查找，O(logn)；
         * 4. 若为链表，则在链表中通过key.equals(k)查找，O(n)。
         */
    }

    /**
     * 获取HashMap元素性能
     * 两步走：
     * 1. 首先根据hashCode()做hash，然后确定bucket的index；
     * 2. 如果bucket的节点的key不是我们需要的，则通过keys.equals()在链中找。
     * 在Java 8之前的实现中是用链表解决冲突的，在产生碰撞的情况下，进行get时，两步的时间复杂度是O(1)+O(n)。因此，当碰撞很厉害的时候n很大，O(n)的速度显然是影响速度的。
     * 因此在Java 8中，利用红黑树替换链表，这样复杂度就变成了O(1)+O(logn)了，这样在n很大的时候，能够比较理想的解决这个问题
     */
}
