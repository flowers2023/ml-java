package com.github2013.ml.fpg;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.min;
import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByKey;

/**
 * Created by shuangfu on 17-4-17.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 * FPG模型入口
 */
public class FPGrowth {
    //最小支持度
    private double minSupport;


    public FPGrowth() {
        this(0.3);
    }

    public FPGrowth(double minSupport) {
        this.minSupport = minSupport;
    }

    public FPGrowthModel run(List<ArrayList<String>> data) {
        long count = data.parallelStream().flatMap(list -> list.stream()).count();//all item count
        long minCount = (long) Math.ceil(count * minSupport);

        List<String> freqItems = genFreqItems(data, minCount);

        return null;
    }


    /**
     * 对数据进行频繁项提取，并按照其频繁进行降序
     *
     * @param data
     * @param minCount 最小项数
     * @return
     */
    public List<String> genFreqItems(List<ArrayList<String>> data, long minCount) {
        Map<String, Long> retMap = data.parallelStream()
                .filter(t -> t.size() == new HashSet(t).size())
                .flatMap((itemList) -> itemList.stream())
                .collect(Collectors.toList())
                .parallelStream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        List<String> freqItems = new ArrayList<>();
        retMap.entrySet().parallelStream()
                .sorted(reverseOrder(Map.Entry.<String, Long>comparingByValue()))
                .forEachOrdered(x -> {
                    if (x.getValue() >= minCount) {
                        freqItems.add(x.getKey());
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
