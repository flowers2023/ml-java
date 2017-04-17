package com.github2013.ml.fpg;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shuangfu on 17-4-17.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class FPGrowthTest extends TestCase {

    FPGrowth model;

    public void setUp() throws Exception {
        super.setUp();
        model = new FPGrowth();
    }

    public void testGenFreqItems() throws Exception {
        List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        data.add(new ArrayList<String>(Arrays.asList("牛奶", "鸡蛋", "面包", "薯片")));
        data.add(new ArrayList<String>(Arrays.asList("鸡蛋", "爆米花", "薯片", "啤酒")));
        data.add(new ArrayList<String>(Arrays.asList("鸡蛋", "面包", "薯片")));
        data.add(new ArrayList<String>(Arrays.asList("牛奶", "鸡蛋", "面包", "爆米花", "薯片", "啤酒")));
        data.add(new ArrayList<String>(Arrays.asList("牛奶", "面包", "啤酒")));
        data.add(new ArrayList<String>(Arrays.asList("鸡蛋", "面包", "啤酒")));
        /**
         * 面包:5
         * 啤酒:4
         * 牛奶:3
         * 薯片:4
         * 鸡蛋:5
         * 爆米花:2
         */
        ArrayList<String> ret = (ArrayList<String>) model.genFreqItems(data, 3);
        String[] expert = {"面包", "啤酒", "牛奶", "薯片", "鸡蛋"};

        ret.forEach(System.out::println);
        Assert.assertArrayEquals(expert, ret.toArray());

    }

    public void testRun() throws Exception {
    }

}