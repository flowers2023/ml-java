package com.github2013.ml.clustering;

import java.util.*;

/**
 * Created by shuangfu on 17-3-23.
 * Author : DRUNK
 * 采用多线程模式，对向量进行聚类
 */
public class KMeans {
    private String threadPrex = "KMeans_00";
    private int k = 4; //Kmeans的集群个数
    private int maxIter = 20;//迭代的次数
    private double epsilon = 1e-4;
    private int threadSize = 5;
    private KMeansModel model;
    private int iterSize;
    private double tol;

    public KMeans() {
    }

//    public KMeans(int numClass, int iterSize, int threadSize) {
//        this.numClass = numClass;
//        this.iterSize = iterSize;
//        this.threadSize = threadSize;
//    }


    public KMeans(int k, int maxIter, double epsilon, int threadSize) {
        this.k = k;
        this.maxIter = maxIter;
        this.epsilon = epsilon;
        this.threadSize = threadSize;
    }

    /**
     * @param vectors
     */
    public void fit(List<Vector> vectors) {
        if (vectors.size() < threadSize) {
            System.out.println("data can not less than thread,but got dataSize:" +
                    +vectors.size() + ",thread count:" + threadSize + ",Please set suitable params.");
            return;
        }
        Map<String, Integer> threadMap = new HashMap<String, Integer>();
        for (int i = 0; i < threadSize; i++) {
            threadMap.put(threadPrex + i, i);
        }
        model = new KMeansModel(vectors, threadMap, k);
//        for (int k = 0; k < iterSize; k++) {
////            System.out.println("iter count:" + iterSize + ",\tcurrent number:" + k);
//            model.cleanClusterAndUpdateVectorCenter();
//            runThread(model);
//        }
        int iterCount = 0;
        while (true) {
            boolean ret = model.checkConvergedAndUpdateVectorCenter(epsilon);

            if (ret || iterCount >= maxIter) {
                if (iterCount > 1) {
                    this.iterSize = iterCount;
                    break;
                }
            }
            model.cleanClusterVector();
            iterCount++;
            System.out.println("----------iter count:" + iterCount);
            runThread(model);
        }

    }

    /**
     *
     */
    public void printMsg() {
        System.out.println("Total row count:" + model.getVectors().size());
        System.out.println(toString());
        List<ClusterCenter> clusterCenters = model.getCluseterCenter();
        for (ClusterCenter clusterCenter : clusterCenters) {
            System.out.println(clusterCenter.toString());
        }

        int n = 10;
        if (model.getVectors().size() < n) {
            n = model.getVectors().size();
        }
    }

//    public void getC() {
//        int n = model.getVectors().size();
//        List<KMeansResult> ret = model.getVectors();
//        for (int i = 0; i < n; i++) {
//            System.out.println(ret.get(i).toString());
//        }
//        return ret;
//    }

    /**
     * 多线程运行
     *
     * @param model
     */
    private void runThread(KMeansModel model) {
        Thread[] myThread = new Thread[threadSize];
        for (int k = 0; k < threadSize; k++) {
            Thread thread = new Thread(model);
            thread.setName(threadPrex + k);
            thread.start();
            myThread[k] = thread;
        }
        for (int k = 0; k < threadSize; k++) {
            try {
                myThread[k].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "KMeans{" +
                "threadPrex='" + threadPrex + '\'' +
                ", k=" + k +
                ", epsilon=" + epsilon +
                ", maxIter=" + maxIter +
                ", iterSize=" + iterSize +
                ", threadSize=" + threadSize +
                '}';
    }

    public double getEpsilon() {
        return epsilon;
    }


    public KMeans setEpsilon(double epsilon) {
        this.epsilon = epsilon;
        return this;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public KMeans setMaxIter(int maxIter) {
        this.maxIter = maxIter;
        return this;
    }

    public String getThreadPrex() {
        return threadPrex;
    }

    public void setThreadPrex(String threadPrex) {
        this.threadPrex = threadPrex;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getThreadSize() {
        return threadSize;
    }

    public KMeans setThreadSize(int threadSize) {
        this.threadSize = threadSize;
        return this;
    }
}
