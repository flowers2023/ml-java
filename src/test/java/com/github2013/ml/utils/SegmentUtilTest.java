package com.github2013.ml.utils;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;

/**
 * Created by shuangfu on 17-4-1.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class SegmentUtilTest extends TestCase {
    public void testRun() throws Exception {
        String str = "I am  kane.!,#%";
        System.out.println(Arrays.toString(SegmentUtil.run(str)));
        Assert.assertArrayEquals(SegmentUtil.run(str), new String[]{"i", "am", "kane"});
    }

}