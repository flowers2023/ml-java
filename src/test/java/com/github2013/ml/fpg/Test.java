package com.github2013.ml.fpg;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by shuangfu on 17-4-17.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> intList = new LinkedList<Integer>();
        for (int i = 1; i <= 1000000; i++) {
            intList.add(i);
        }
        intList.stream().forEach(i -> {
            i.intValue();
        });


        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
    }
}
