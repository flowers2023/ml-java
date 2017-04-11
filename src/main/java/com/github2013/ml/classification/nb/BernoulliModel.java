package com.github2013.ml.classification.nb;

import java.util.*;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 * 存放每个label的信息，包括本label下数据的类别名，词总量，词汇总量(需要去重，与多项式不同的地方)，先验概率，词后验概率。
 */
public class BernoulliModel extends BaseModel {
    private double score = 0D;

    public BernoulliModel(String name, List<String> words) {
        super();
        this.name = name;
        numberOfDoc = 1;
        this.words = new LinkedList<String>();
        this.words.addAll(getUniqe(words));
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
        for (String word : vocabularys) {
            double nc = 0D;
            if (vocabularyCount.containsKey(word)) {
                nc = vocabularyCount.get(word);
            }
            double cb = (double) (nc + 1) / (numberOfDoc + 2);
//            System.out.println(this.getName() + ",\t" + word +
//                    ",\tcount:" + nc + ",\tnumberOfDoc:" +
//                    numberOfDoc + ",\tprop:" + (nc + 1) + "/" + (numberOfDoc + 2)
//                    + ",\tresult:" + cb);
            getCondProb().put(word, cb);
        }
        for (String v : vocabularys) {
            score += Math.log(1 - getCondProb(v));
        }
    }

    /**
     * 更新本类别的词列表，把所有词连接到数组中。(需要去重，与多项式不同的地方)
     *
     * @param c
     * @param words
     */
    public void updateWords(int c, List<String> words) {
        this.numberOfDoc += c;
        this.words.addAll(getUniqe(words));
    }

    private List<String> getUniqe(List<String> words) {
        List<String> newWords = new ArrayList<String>();
        for (String w : words) {
            if (!newWords.contains(w)) {
                newWords.add(w);
            }
        }

        return newWords;
    }

    public void updateVocabularyCondProb() {
        super.updateVocabularyCondProb();
    }

    /**
     * 计算后验概率，并计算最大概率的类别
     *
     * @param words
     * @return
     */
    public LabelProbability predictProbability(String[] words) {
        double prob = Math.log(this.getPriorProb());
        Set<String> predictWords = new HashSet<String>();
        for (String word : words) {
            if (!predictWords.contains(word)) {
                prob += Math.log(getCondProb(word));
                predictWords.add(word);
            }
        }
        prob += score;
        for (String word : predictWords) {
            prob -= Math.log(1 - getCondProb(word));
        }

        return new LabelProbability(this.getName(), prob);
    }

}
