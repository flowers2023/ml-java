package com.github2013.ml.clustering;

import java.util.Arrays;

/**
 * Created by shuangfu on 17-3-23.
 * Author : DRUNK
 * 聚簇质心信息，仅在获取质心信息使用
 */
public class ClusterCenter {
    private int classID;
    private int size;
    private Vector vcenter;

    public ClusterCenter(int classID, int size, Vector vcenter) {
        this.classID = classID;
        this.size = size;
        this.vcenter = vcenter;
    }

    public int getClassID() {
        return classID;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String vectorStr = Arrays.toString(vcenter.getData());
        if (vectorStr.length() > 100) {
            vectorStr = vectorStr.substring(0, 100) + "...]";
        }
        return "class:" + classID + ",\tsize:" + size + ",\tcneter vector:" + vectorStr;
    }
}
