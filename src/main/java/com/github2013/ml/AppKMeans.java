package com.github2013.ml;

import com.github2013.ml.utils.FileVectors;
import com.github2013.ml.clustering.KMeans;
import com.github2013.ml.clustering.Vector;

import java.util.List;

/**
 * kmeans Main run test
 */
public class AppKMeans {
    public static void main(String[] args) throws Exception {
        int threadSize = 20;
        int maxIter = 20;
        int k = 5;
        double epsilon = 1e-4;
        String paths = "/home/shuangfu/code/kmeans-test/sentence-vector.txt";
        if (args.length >= 3) {
            threadSize = Integer.parseInt(args[0]);
            maxIter = Integer.parseInt(args[1]);
            epsilon = Double.parseDouble(args[2]);
            k = Integer.parseInt(args[3]);
            paths = args[4];
        }

        System.out.println("threadSize:" + threadSize);
        System.out.println("iterSize:" + maxIter);
        System.out.println("k:" + k);
        System.out.println("epsilon:" + epsilon);
        System.out.println("paths:" + paths);

        System.out.println("stat to read file..........");
        long startTime = System.currentTimeMillis();

        List<Vector> vectors = FileVectors.getKMeansVectors(paths);
        System.out.println("File read and convert to vector finished,total time:[" + (System.currentTimeMillis() - startTime) / 1000 + "] seconds.");

        KMeans kMeans = new KMeans();
        kMeans.setThreadSize(threadSize)
                .setMaxIter(maxIter)
                .setEpsilon(epsilon)
                .setK(k);

        kMeans.fit(vectors);
        //System.out.println("end time:" + new Date());
        long endTime = System.currentTimeMillis();
        //System.out.println("Total time:" + (endTime - startTime) / 1000 + " s");

        kMeans.printMsg();
    }
}
