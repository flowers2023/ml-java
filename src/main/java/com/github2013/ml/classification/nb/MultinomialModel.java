package com.github2013.ml.classification.nb;

import java.util.*;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 * 存放每个label的信息，包括本label下数据的类别名，词总量，词汇总量，先验概率，词后验概率。
 */
public class MultinomialModel extends BaseModel {

    public MultinomialModel(String name, List<String> words) {
        super();
        this.name = name;
        numberOfDoc = 1;
        this.words = new LinkedList<String>();
        this.words.addAll(words);
        vocabularyCount = new HashMap<String, Long>();
        condProb = new TreeMap<String, Double>();
        priorProb = 0D;
    }

    /**
     * 计算后验概率
     *
     * @param vocabularys
     */
    public void calculateCondProb(Set<String> vocabularys) {
        super.updateVocabularyCondProb();
        int B = vocabularys.size();
        long allWords = words.size();
        for (String word : vocabularys) {
            double wc = 0D;
            if (vocabularyCount.containsKey(word)) {
                wc = vocabularyCount.get(word);
            }
            double cb = (double) (wc + 1) / (allWords + B);
            condProb.put(word, cb);
        }
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

    public void updateVocabularyCondProb() {

    }

    /**
     * 计算后验概率，并计算最大概率的类别
     *
     * @param words
     * @return
     */
    public LabelProbability predictProbability(String[] words) {
//        double prob = this.getPriorProb();
        double prob = Math.log(this.getPriorProb());
        for (String word : words) {
//            prob *= getCondProb(word);
            prob += Math.log(getCondProb(word));//取对数，提高精度
        }
        return new LabelProbability(this.getName(), prob);
    }
}
