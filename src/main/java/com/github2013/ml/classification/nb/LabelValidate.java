package com.github2013.ml.classification.nb;

/**
 * Created by shuangfu on 17-4-1.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class LabelValidate {
    private String label;
    private int correctCount;
    private int wrongCount;

    public LabelValidate(String label) {
        this.label = label;
        correctCount = 0;
        wrongCount = 0;
    }

    /**
     * @param c
     */
    public void increaseCorrectCount(int c) {
        correctCount += c;
    }

    public void increaseWrongCount(int c) {
        wrongCount += c;
    }

    public String getLabel() {
        return label;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    @Override
    public String toString() {
        double rate = ((double) correctCount / (correctCount + wrongCount)) * 100;
        return "{" +
                "label='" + label +
                "\t, correctCount=" + correctCount +
                "\t, wrongCount=" + wrongCount +
                "\t,正确率：" + (((int) (rate * 100)) / 100.0) + "%" +
                '}';
    }
}
