package com.github2013.ml.utils;

import com.github2013.ml.clustering.Vector;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shuangfu on 17-3-28.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class FileReader implements Runnable {
    private String path;
    private List<Vector> vectors;

    public FileReader(String path) {
        this.path = path;
    }

    public void run() {
        try {
            vectors = new LinkedList<Vector>();
            List<String> lines = FileUtils.readLines(new File(path), "UTF8");
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

    public List<Vector> getVectors() {
        return vectors;
    }
}
