package com.github2013.ml.classification.nb;

import java.util.*;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 * 本示例采用 斯坦福 朴素贝叶斯分类算法进行分类。参考文章如下：
 * System.out.println(l.getName());
 */

public class NaiveBayesBer implements NaiveBayes {
    private Set<String> vocabulary;
    private long docCount;
    private List<BernoulliModel> models;

    public NaiveBayesBer() {
        vocabulary = new HashSet<String>();
        models = new ArrayList<BernoulliModel>();
        this.docCount = 0;
    }


    /**
     * 开始对数据进行训练
     * trainMultinomialNB
     */
    public void trainMultinomialNB(List<LabeledPoint> labeledPoints) {
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
                models.add(new BernoulliModel(words.getLabel(), words.getWords()));
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
        for (BernoulliModel l : models) {
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
    private BernoulliModel getLabelMode(String label) {
        for (BernoulliModel l : models) {
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
    public void updateLabelProbability(long docCount) {
        System.out.println("-----------------update probability-----------------");
        for (BernoulliModel label : models) {
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

    /**
     * 计算后验概率，并计算最大概率的类别
     *
     * @param sentenceList
     * @return
     */
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
        ArrayList<String[]> oneSentence = new ArrayList<String[]>();
        oneSentence.add(words);
        return predictProbability(oneSentence).get(0);
    }


    /**
     * 计算后验概率，并计算最大概率的类别
     *
     * @param sentenceArray
     * @return
     */
    public List<LabelProbability> predictProbability(List<String[]> sentenceArray) {
        List<LabelProbability> retList = new ArrayList<LabelProbability>();
        for (String[] words : sentenceArray) {
            LabelProbability expectLP = new LabelProbability("", -Double.MAX_VALUE);
            double sumAllProp = 0d;
            for (BernoulliModel model : models) {
                LabelProbability tmpPrep = model.predictProbability(words);
                sumAllProp += Math.exp(tmpPrep.getProbability());
                if (tmpPrep.getProbability() > expectLP.getProbability()) {
                    expectLP = tmpPrep;
                }
//                System.out.println(tmpPrep.getLabel() + "\tprop:" + tmpPrep.getProbability());
            }
            expectLP.setPercentProp((double) Math.exp(expectLP.getProbability()) / sumAllProp);

//            System.out.println("percent:" + expectLP.getPercentProp());
            retList.add(expectLP);
        }
        return retList;
    }

    /**
     * 交叉验证
     *
     * @param testLabeledPoints
     * @return
     */
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

    /**
     * 交叉验证
     *
     * @param testLabeledPoints
     * @return
     */
    public List<LabelValidate> crossTest(List<LabeledPoint> testLabeledPoints, double lowProp) {
        System.out.println("--------------crossTest--------------");
        List<LabelValidate> retList = new ArrayList<LabelValidate>();
        for (LabeledPoint labeledPoint : testLabeledPoints) {
            LabelProbability predictLabel = predictProbability(labeledPoint.getVordsVecotr());
//            System.out.println(predictLabel.toString());
            if (predictLabel.getPercentProp() < lowProp) {
                continue;
            }
            if (predictLabel.getLabel().equals(labeledPoint.getLabel())) {
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
        for (BernoulliModel label : models) {
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
        for (BernoulliModel l : models) {
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
        for (BernoulliModel l : models) {
            if (l.getName().equals(label)) {
                System.out.println(l.getCondProb(word));
                return l.getCondProb(word);
            }
        }
        return 0;
    }
}
