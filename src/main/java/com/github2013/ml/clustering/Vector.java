package com.github2013.ml.clustering;

/**
 * Created by shuangfu on 17-3-23.
 * Author : DRUNK
 */

/**
 * calcute the cluster distance.
 */
public class Vector {
    private String text;
    private Float[] data;

    public Vector(Float[] data) {
        this.data = data;
    }

    public Vector(String[] dataStr) {
        data = new Float[dataStr.length];
        for (int i = 0; i < dataStr.length; i++) {
            data[i] = Float.parseFloat(dataStr[i]);
        }
    }

    public Vector(String text, String[] dataStr) {
        this.text = text;
        data = new Float[dataStr.length];
        for (int i = 0; i < dataStr.length; i++) {
            data[i] = Float.parseFloat(dataStr[i]);
        }
    }

    public Float[] getData() {
        return data;
    }

    public float get(int dimension) {
        return data[dimension];
    }

    public int getLength() {
        return data.length;
    }

    public String toString() {
        if (data.length < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        sb.append(data[0]);
        for (int i = 1; i < data.length; i++) {
            sb.append(",");
            sb.append(data[i]);
        }
        return sb.toString();
    }

    /**
     * 计算像个向量的距离
     *
     * @param v
     * @return
     */
    public Double getDistance(Vector v) {
        Double distance = 0d;
        for (int i = 0; i < data.length; i++) {
            distance += Math.pow(data[i] - v.get(i), 2);
        }
        return Math.sqrt(distance);
    }

    @Override
    public boolean equals(Object obj) {
        Vector v = (Vector) obj;
        for (int i = 0; i < data.length; i++) {
            try {
                if (data[i] != v.get(i)) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public String getText() {
        return text;
    }
}
