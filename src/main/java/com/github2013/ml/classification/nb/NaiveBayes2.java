package com.github2013.ml.classification.nb;

import java.util.*;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 * 本示例采用 斯坦福 朴素贝叶斯分类算法进行分类。参考文章如下：
 * https://nlp.stanford.edu/IR-book/html/htmledition/naive-bayes-text-classification-1.html
 */

public class NaiveBayes2 {
    private Set<String> vocabulary;
    private long docCount;
    private List<NBMultinomialModel> NBMultinomialModels;

    public NaiveBayes2() {
        vocabulary = new HashSet<String>();
        NBMultinomialModels = new ArrayList<NBMultinomialModel>();
        this.docCount = 0;
    }

    /**
     * 开始对数据进行训练
     * trainMultinomialNB
     */
    private void trainMultinomialNB(List<LabeledPoint> labeledPoints) {
        Iterator<LabeledPoint> vectorIter = labeledPoints.iterator();

        long count = 0;
        long allCount = labeledPoints.size();
        System.out.println("start to train data.......");
        while (vectorIter.hasNext()) {
            count++;
            if (count % 1000 == 0) {
                System.out.println("All count:" + allCount + "\t,current:" + count);
            }
            LabeledPoint words = vectorIter.next();
            if (!checkLableExist(words.getLabel())) {
                NBMultinomialModels.add(new NBMultinomialModel(words.getLabel(), words.getWords()));
            } else {
                getLabelMode(words.getLabel()).updateWords(1, words.getWords());
            }
            for (String w : words.getWords()) {
                if (!vocabulary.contains(w)) {
                    vocabulary.add(w);
                }
            }
        }

        this.docCount += count;
        //update prior probability of every labels

        updateLabelProbability(docCount);
        System.out.println("train data end.......");
    }

    /**
     * 检测label是否存在
     *
     * @param label
     * @return
     */
    private boolean checkLableExist(String label) {
        for (NBMultinomialModel l : NBMultinomialModels) {
            if (l.getName().equals(label)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取label的模型
     *
     * @param label
     * @return
     */
    private NBMultinomialModel getLabelMode(String label) {
        for (NBMultinomialModel l : NBMultinomialModels) {
            if (l.getName().equals(label)) {
                return l;
            }
        }
        return null;
    }

    /**
     * 更新label的先验概率和词的后验概率
     *
     * @param docCount
     */
    private void updateLabelProbability(long docCount) {
        System.out.println("-----------------update probability-----------------");
        for (NBMultinomialModel label : NBMultinomialModels) {
            label.calculatePriorProb(docCount);
            label.calculateCondProb(vocabulary);
        }
        System.out.println("-----------------update probability end-----------------");
    }

    /**
     * @param labeledPoints
     */
    public void fit(List<LabeledPoint> labeledPoints) {
        trainMultinomialNB(labeledPoints);
    }

    /**
     * 预测
     *
     * @param words
     * @return
     */
    public String predict(String[] words) {
        return predictProbability(words).getLabel();
    }

    public List<String> predict(List<String[]> sentenceList) {
        List<String> retLabel = new LinkedList<String>();
        List<LabelProbability> labelProbabilityList = predictProbability(sentenceList);
        for (LabelProbability lp : labelProbabilityList) {
            retLabel.add(lp.getLabel());
        }
        return retLabel;
    }

    /**
     * 预测词属于哪个类别，并输出概率
     *
     * @param words
     * @return
     */
    public LabelProbability predictProbability(String[] words) {
        String retLabel = "";
        double tmpProb = 0;
        for (NBMultinomialModel label : NBMultinomialModels) {
            double prob = label.getPriorProb();
            for (String word : words) {
                prob *= label.getCondProb(word);
//                System.out.println(label.getName() + ":" + word + ":" + label.getCondProb(word));
            }
            if (prob > tmpProb) {
                tmpProb = prob;
                retLabel = label.getName();
            }
        }
        return new LabelProbability(retLabel, tmpProb);
    }


    public List<LabelProbability> predictProbability(List<String[]> sentenceArray) {
        List<LabelProbability> retList = new ArrayList<LabelProbability>();
        for (String[] words : sentenceArray) {
            String retLabel = "";
            double tmpProb = 0;
            for (NBMultinomialModel label : NBMultinomialModels) {
                double prob = label.getPriorProb();
                for (String word : words) {
                    prob *= label.getCondProb(word);
                }
                if (prob > tmpProb) {
                    tmpProb = prob;
                    retLabel = label.getName();
                }
                retList.add(new LabelProbability(retLabel, tmpProb));
            }
        }
        return retList;
    }

    public List<LabelValidate> crossTest(List<LabeledPoint> testLabeledPoints) {
        System.out.println("--------------crossTest--------------");
        List<LabelValidate> retList = new ArrayList<LabelValidate>();
        for (LabeledPoint labeledPoint : testLabeledPoints) {
            String predictLabel = predict(labeledPoint.getVordsVecotr());
            if (predictLabel.equals(labeledPoint.getLabel())) {
                getLabelValidate(retList, labeledPoint.getLabel()).increaseCorrectCount(1);
            } else {
                getLabelValidate(retList, labeledPoint.getLabel()).increaseWrongCount(1);
            }
        }
        System.out.println("--------------crossTest end--------------");

        return retList;
    }

    private LabelValidate getLabelValidate(List<LabelValidate> labelValidateList, String label) {
        for (LabelValidate lv : labelValidateList) {
            if (lv.getLabel().equals(label)) {
                return lv;
            }
        }
        LabelValidate lv = new LabelValidate(label);

        labelValidateList.add(lv);
        return lv;
    }

    /**
     * for test
     *
     * @param word
     */
    public void printProb(String word) {
        for (NBMultinomialModel label : NBMultinomialModels) {
            System.out.println(label.getName() + "\t先验概率:" + label.getPriorProb());
            System.out.println(word + "\t概率:" + label.getCondProb(word));
        }
    }

    /**
     * 获取列别的先验概率
     *
     * @param label
     * @return
     */
    public double getPriorProb(String label) {
        for (NBMultinomialModel l : NBMultinomialModels) {
            if (l.getName().equals(label)) {
                return l.getPriorProb();
            }
        }
        return 0;
    }

    /**
     * 获取词在某一label下的后验概率
     *
     * @param label
     * @param word
     * @return
     */
    public double getWordProbByLabel(String label, String word) {
        for (NBMultinomialModel l : NBMultinomialModels) {
            if (l.getName().equals(label)) {
                return l.getCondProb(word);
            }
        }
        return 0;
    }
}
