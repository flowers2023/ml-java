package com.github2013.ml.classification.nb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 * 存放句子的label和词表
 */

public class LabeledPoint {
    private String label;
    private List<String> words;

    public LabeledPoint(String label, List<String> words) {
        this.label = label;
        this.words = words;
    }

    public LabeledPoint(String label, String[] words) {
        this.label = label;
        this.words = new ArrayList<String>();
        for (String w : words) {
            this.words.add(w);
        }
    }

    public String getLabel() {
        return label;
    }

    public List<String> getWords() {
        return words;
    }

    public String[] getVordsVecotr() {
        String[] vector = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            vector[i] = words.get(i);
        }
        return vector;
    }
}
