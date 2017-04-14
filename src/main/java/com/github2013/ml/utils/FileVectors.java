package com.github2013.ml.utils;

import com.github2013.ml.classification.nb.LabeledPoint;
import com.github2013.ml.clustering.Vector;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuangfu on 17-3-29.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */

public class FileVectors {
    public static List<Vector> getKMeansVectors(String paths) {

        long startTime = System.currentTimeMillis();
        List<Vector> vectors = new LinkedList<Vector>();
        if (paths.indexOf(",") > 0) {
            String[] filesPath = paths.split(",");
            FileReader[] fileReader = new FileReader[filesPath.length];
            for (int i = 0; i < filesPath.length; i++) {
                System.out.println("dataPath:" + filesPath[i]);
                FileReader fr = new FileReader(filesPath[i]);
                fileReader[i] = fr;
            }

            Thread[] myThread = new Thread[fileReader.length];
            for (int i = 0; i < fileReader.length; i++) {
                Thread thread = new Thread(fileReader[i]);
                thread.setName("File_read" + i);
                thread.start();
                myThread[i] = thread;
            }
            for (int i = 0; i < fileReader.length; i++) {
                try {
                    myThread[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < fileReader.length; i++) {
                vectors.addAll(fileReader[i].getVectors());
            }
        } else {
            System.out.println("dataPath:" + paths);
            try {
                List<String> lines = FileUtils.readLines(new File(paths), "UTF8");

                Iterator<String> iter = lines.iterator();
                while (iter.hasNext()) {
                    String line = iter.next();
                    Vector v = new Vector(line.split("\\|")[0], line.split("\\|")[2].split(","));
                    vectors.add(v);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return vectors;
    }

    public static List<LabeledPoint> getNaiveBayesVector(String label, String path) {
        List<LabeledPoint> labeledPoints = new LinkedList<LabeledPoint>();
        try {
            List<String> lines = FileUtils.readLines(new File(path), "UTF8");
            Iterator iter = lines.iterator();
            while (iter.hasNext()) {
                String line = (String) iter.next();
                labeledPoints.add(new LabeledPoint(label, SegmentUtil.run(line)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return labeledPoints;
    }
}
