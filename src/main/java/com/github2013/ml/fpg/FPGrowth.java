package com.github2013.ml.fpg;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by shuangfu on 17-4-17.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class FPGrowth {
    private double minSupport;


    public FPGrowth() {
        this(0.3);
    }

    public FPGrowth(double minSupport) {
        this.minSupport = minSupport;
    }

    public FPGrowthModel run(List<ArrayList<String>> data) {
        long count = data.size();
        long minCount = (long) Math.ceil(count * minSupport);

        List<String> freqItems = genFreqItems(data, minCount);

        return null;
    }

    public List<String> genFreqItems(List<ArrayList<String>> data, long minCount) {
        Map<String, Long> retMap = data.parallelStream()
                .filter(t -> t.size() == new HashSet(t).size())
                .flatMap((itemList) -> itemList.stream())
                .collect(Collectors.toList())
                .parallelStream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<String> freqItems = new ArrayList<>();

        retMap.forEach((k, v) -> {
            if (v >= minCount) {
                freqItems.add(k);
            }
        });

        return freqItems;
    }

    public List<FreqItemset> genFreqItemsets(List<ArrayList<String>> data, long minCount, long freqItems) {

        return null;
    }

    public double getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(double minSupport) {
        this.minSupport = minSupport;
    }
}
