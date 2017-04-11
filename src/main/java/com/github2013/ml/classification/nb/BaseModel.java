package com.github2013.ml.classification.nb;

import java.util.*;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 * 存放每个label的信息，包括本label下数据的类别名，词总量，词汇总量，先验概率，词后验概率。
 */
public abstract class BaseModel{
    protected String name;
    protected long numberOfDoc;
    protected double priorProb;
    protected List<String> words; //
    protected Map<String, Long> vocabularyCount;//this label vecabulary count;
    protected Map<String, Double> condProb; //all vecabulary in this label's probability.

    public BaseModel() {
        this.name = name;
        numberOfDoc = 1;
        this.words = new LinkedList<String>();
        this.words.addAll(words);
        vocabularyCount = new HashMap<String, Long>();
        condProb = new TreeMap<String, Double>();
        priorProb = 0D;
    }


    /**
     * 更新本类别的词列表，把所有词连接到数组中。
     *
     * @param c
     * @param words
     */
    public void updateWords(int c, List<String> words) {
        this.numberOfDoc += c;
        this.words.addAll(words);
    }

    /**
     * 更新词汇表，并统计每个词的词频。
     */
    protected void updateVocabularyCondProb() {
        for (String word : words) {
            if (vocabularyCount.containsKey(word)) {
                long count = vocabularyCount.get(word);
                vocabularyCount.put(word, count + 1);
            } else {
                vocabularyCount.put(word, 1L);
            }
        }
    }

    /**
     * 获取某个词在此label下的后验概率
     *
     * @param word
     * @return
     */
    public double getCondProb(String word) {
        if (condProb.containsKey(word)) {
            return condProb.get(word);
        } else {
            return 1;//如果词汇表无这个词，返回1，则表明不参与计算
        }
    }

    /**
     * 计算此label的先验概率
     *
     * @param allDocCount
     */
    public void calculatePriorProb(long allDocCount) {
        this.priorProb = (double) numberOfDoc / allDocCount;
    }

    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public Map<String, Double> getAllVocabularyCondProb() {
        return condProb;
    }

    public double getPriorProb() {
        return priorProb;
    }

    public long getNumberOfDoc() {
        return numberOfDoc;
    }

    public List<String> getWords() {
        return words;
    }

    public Map<String, Long> getVocabularyCount() {
        return vocabularyCount;
    }

    public Map<String, Double> getCondProb() {
        return condProb;
    }
}
