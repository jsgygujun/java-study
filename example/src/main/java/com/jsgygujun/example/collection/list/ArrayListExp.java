package com.jsgygujun.example.collection.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jsgygujun@gmail.com
 * @since 2020/7/24 10:32 上午
 */
public class ArrayListExp {

    @Test
    public void create() {
        ArrayList<Integer> arrayList = new ArrayList<>(); // 默认容量： 10
        ArrayList<Integer> arrayList2 = new ArrayList<>(100); // 指定容量：100
        ArrayList<Integer> arrayList3 = new ArrayList<>(Collections.singletonList(1)); // 用 Collection 来初始化 ArrayList
        ArrayList<Integer> arrayList4 = new ArrayList<Integer>(){{
            add(1);
            add(2);
        }}; // 指定初始值
    }

    @Test
    public void add() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1); // [1]
        arrayList.add(0, 2); // [2, 1]
        arrayList.addAll(new ArrayList<Integer>(){{add(1); add(2);}}); // [2, 1, 1, 2]
    }


}
