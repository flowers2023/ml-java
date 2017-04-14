package com.github2013.ml.clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shuangfu on 17-3-23.
 * Author : DRUNK
 * 聚簇信息，包括向量列表，向量个数，质心
 */
public class Cluster {
    private List<Vector> vectors;
    private int num;
    private Vector centerVecter;

    public Cluster(int num, Vector centerVecter) {
        vectors = new ArrayList<Vector>();
        this.num = num;
        this.centerVecter = centerVecter;
        vectors.add(centerVecter);
    }

    public void cleanVector() {
//        vectors = new ArrayList<LabeledPoint>();
        vectors = new ArrayList<Vector>();
    }

    /**
     * add vector to cluster
     * @param v
     */
    public synchronized void addVector(Vector v) {
        vectors.add(v);
    }


    /**
     * 更新聚簇的中心位置
		 * 并返回新的质心位置
     */
    public synchronized double updateAndGetCenterVector() {
        int vecterSize = vectors.get(0).getLength();
        Float[] a = new Float[vecterSize];
        Arrays.fill(a, 0f);
        for (Vector v : vectors) {
            for (int i = 0; i < vecterSize; i++) {
                a[i] += v.get(i);
            }
        }
        for (int i = 0; i < vecterSize; i++) {
            a[i] = a[i] / vectors.size();
        }
        Vector newVector = new Vector(a);
        double d = newVector.getDistance(centerVecter);
        centerVecter = newVector;
        return d;
    }

    public int getNum() {
        return num;
    }

    public Vector getCenterVecter() {
        return centerVecter;
    }

    public List<Vector> getVectors() {
        return vectors;
    }
}
