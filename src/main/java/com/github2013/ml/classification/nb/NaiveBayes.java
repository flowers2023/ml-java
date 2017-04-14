package com.github2013.ml.classification.nb;

import java.util.*;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 * 本示例采用 斯坦福 朴素贝叶斯分类算法进行分类。参考文章如下：
 * https://nlp.stanford.edu/IR-book/html/htmledition/naive-bayes-text-classification-1.html
 */

public interface NaiveBayes {
    /**
     * 开始对数据进行训练
     * trainMultinomialNB
     */
    void trainMultinomialNB(List<LabeledPoint> labeledPoints);

    /**
     * 更新label的先验概率和词的后验概率
     *
     * @param docCount
     */
    void updateLabelProbability(long docCount);

    /**
     * @param labeledPoints
     */
    void fit(List<LabeledPoint> labeledPoints);

    /**
     * 预测
     *
     * @param words
     * @return
     */
    String predict(String[] words);

    /**
     * 计算后验概率，并计算最大概率的类别
     *
     * @param sentenceList
     * @return
     */
    List<String> predict(List<String[]> sentenceList);

    /**
     * 预测词属于哪个类别，并输出概率
     *
     * @param words
     * @return
     */
    LabelProbability predictProbability(String[] words);

    /**
     * 计算后验概率，并计算最大概率的类别
     *
     * @param sentenceArray
     * @return
     */
    List<LabelProbability> predictProbability(List<String[]> sentenceArray);

    /**
     * 交叉验证
     *
     * @param testLabeledPoints
     * @return
     */
    List<LabelValidate> crossTest(List<LabeledPoint> testLabeledPoints, double lowProp);

    /**
     * for test
     *
     * @param word
     */
    void printProb(String word);

    /**
     * 获取列别的先验概率
     *
     * @param label
     * @return
     */
    double getPriorProb(String label);

    /**
     * 获取词在某一label下的后验概率
     *
     * @param label
     * @param word
     * @return
     */
    double getWordProbByLabel(String label, String word);
}
