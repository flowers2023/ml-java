package com.github2013.ml.clustering;

import java.util.*;

/**
 * Created by shuangfu on 17-3-24.
 * Author : DRUNK
 */
public class KMeansModel implements Runnable {
    private List<Cluster> clusters; //Kmeans 的集群集合
    private List<Vector> vectorList;
    private Map<String, Integer> threadMap;

    public KMeansModel(List<Vector> vectorList, Map<String, Integer> threadMap, int k) {
        this.vectorList = vectorList;
        this.threadMap = threadMap;
        clusters = new ArrayList<Cluster>();
        ArrayList<Vector> randomVectorList = getRandomVector(threadMap.size());

        for (int i = 0; i < k; i++) {
            clusters.add(new Cluster(i, randomVectorList.get(i)));
        }
    }

    public void run() {
        List<Vector> vectors = getThreadVectorList();
        for (Vector v : vectors) {
            Cluster c = getClosestCluster(v);
            c.addVector(v);
        }
    }

    public boolean checkConvergedAndUpdateVectorCenter(double epsilon) {
        boolean isStop = true;
        for (Cluster c : clusters) {
            double d = c.updateAndGetCenterVector();
            if (d > epsilon) {
                isStop = false;
            }
            System.out.println("cluster:" + c.getNum() + " centor distance:" + d + ",size:" + c.getVectors().size());
        }
        return isStop;
    }

    public void cleanClusterVector() {

        for (Cluster c : clusters) {
            double d = c.updateAndGetCenterVector();
            c.cleanVector();
        }
    }

    private ArrayList<Vector> getRandomVector(int k) {
        ArrayList<Vector> randomVectorList = new ArrayList<Vector>();
        Random random = new Random();
        Vector v = vectorList.get(random.nextInt(vectorList.size()));
        if (k >= vectorList.size()) {
            System.exit(1);
        }
        while (true) {
            if (!randomVectorList.contains(v)) {
                randomVectorList.add(v);
            }
            v = vectorList.get(random.nextInt(vectorList.size()));
            if (randomVectorList.size() == k) {
                break;
            }
        }
        return randomVectorList;
    }

    /**
     * get closest cluster center by Euclidean distance
     */
    private Cluster getClosestCluster(Vector v) {
        Iterator<Cluster> it = clusters.iterator();
        double distance = Double.MAX_VALUE;
        Cluster cluster = clusters.get(0);
        while (it.hasNext()) {
            Cluster entry = it.next();
            double tmpDistance = v.getDistance(entry.getCenterVecter());
            if (tmpDistance < distance) {
                distance = tmpDistance;
                cluster = entry;
            }
        }
        return cluster;
    }

    private List<Vector> getThreadVectorList() {
        int stepSize = vectorList.size() / threadMap.size();
        int fromIndex = threadMap.get(Thread.currentThread().getName()) * stepSize;
        int toIndex = fromIndex + stepSize;
        if (toIndex >= vectorList.size()) {
            toIndex = vectorList.size() - 1;
        }

        return vectorList.subList(fromIndex, toIndex);
    }

    public List<KMeansResult> getVectors() {
        List<KMeansResult> ret = new ArrayList<KMeansResult>();
        Iterator<Cluster> it = clusters.iterator();
        while (it.hasNext()) {
            Cluster cluster = it.next();
            Iterator<Vector> vectorIterator = cluster.getVectors().iterator();
            while (vectorIterator.hasNext()) {
                Vector v = vectorIterator.next();
                ret.add(new KMeansResult(cluster.getNum(), v));
            }
        }
        return ret;
    }

    public List<ClusterCenter> getCluseterCenter() {
        List<ClusterCenter> list = new ArrayList<ClusterCenter>();
        for (Cluster c : clusters) {
            list.add(new ClusterCenter(c.getNum(), c.getVectors().size(), c.getCenterVecter()));
        }

        return list;
    }


}
