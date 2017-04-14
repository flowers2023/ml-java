package com.github2013.ml.clustering;

import java.util.Arrays;

/**
 * Created by shuangfu on 17-3-23.
 * Author : DRUNK
 */
public class KMeansResult {
    private int classID;
    private Vector vector;
    private String text;

    public KMeansResult(int classID, Vector vector) {
        this.classID = classID;
        this.vector = vector;
    }

    public KMeansResult(int classID, Vector vector, String text) {
        this.classID = classID;
        this.vector = vector;
        this.text = text;
    }

    public int getClassID() {
        return classID;
    }

    public Vector getVector() {
        return vector;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "class:" + classID + ",\ttext:" + text + ",\tvector:" + Arrays.toString(vector.getData());
    }
}
