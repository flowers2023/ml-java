package com.github2013.ml.clustering;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuangfu on 17-4-7.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class KMeansTest extends TestCase {
    KMeans kMeans = new KMeans();

    public void setUp() throws Exception {
        super.setUp();
        List<Vector> vectors = new ArrayList<Vector>();
        vectors.add(new Vector(new Float[]{100.0f, 100.0f}));
        vectors.add(new Vector(new Float[]{101.0f, 101.0f}));
        vectors.add(new Vector(new Float[]{102.0f, 102.0f}));
        vectors.add(new Vector(new Float[]{103.0f, 103.0f}));
        vectors.add(new Vector(new Float[]{104.0f, 104.0f}));

        vectors.add(new Vector(new Float[]{-100.0f, 100.0f}));
        vectors.add(new Vector(new Float[]{-101.0f, 101.0f}));
        vectors.add(new Vector(new Float[]{-102.0f, 102.0f}));
        vectors.add(new Vector(new Float[]{-103.0f, 103.0f}));
        vectors.add(new Vector(new Float[]{-104.0f, 104.0f}));

        vectors.add(new Vector(new Float[]{100.0f, -100.0f}));
        vectors.add(new Vector(new Float[]{101.0f, -101.0f}));
        vectors.add(new Vector(new Float[]{102.0f, -102.0f}));
        vectors.add(new Vector(new Float[]{103.0f, -103.0f}));
        vectors.add(new Vector(new Float[]{104.0f, -104.0f}));

        vectors.add(new Vector(new Float[]{-100.0f, -100.0f}));
        vectors.add(new Vector(new Float[]{-101.0f, -101.0f}));
        vectors.add(new Vector(new Float[]{-102.0f, -102.0f}));
        vectors.add(new Vector(new Float[]{-103.0f, -103.0f}));
        vectors.add(new Vector(new Float[]{-104.0f, -104.0f}));
        kMeans.setK(4);
        kMeans.fit(vectors);
    }

    public void testPrintMsg() throws Exception {
        kMeans.printMsg();
    }

}