package com.github2013.ml.classification.nb;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class LabelProbability {
    private String label;
    private double probability;
    private double percentProp;

    public LabelProbability(String label, double probability) {
        this.label = label;
        this.probability = probability;
    }

    public String getLabel() {
        return label;
    }

    public double getProbability() {
        return probability;
    }

    public double getPercentProp() {
        return percentProp;
    }

    public void setPercentProp(double percentProp) {
        this.percentProp = percentProp;
    }

    @Override
    public String toString() {
        return "LabelProbability{" +
                "label='" + label + '\'' +
                ", probability=" + probability +
                ", percentProp=" + percentProp +
                '}';
    }
}
