package com.github2013.ml.fpg;

/**
 * Created by shuangfu on 17-4-17.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class FreqItemset {
    private String[] items;
    private long freq;

    public FreqItemset(String[] items, long freq) {
        this.items = items;
        this.freq = freq;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public long getFreq() {
        return freq;
    }

    public void setFreq(long freq) {
        this.freq = freq;
    }
}
