package com.github2013.ml.classification.nb;

import com.github2013.ml.classification.nb.LabelProbability;
import com.github2013.ml.classification.nb.LabeledPoint;
import com.github2013.ml.classification.nb.NaiveBayes;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuangfu on 17-3-31.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class MultinomialNBTest extends TestCase {
    NaiveBayes model = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        List<LabeledPoint> list = new ArrayList<LabeledPoint>();
        list.add(new LabeledPoint("china", "Chinese Beijing Chinese".split(" ")));
        list.add(new LabeledPoint("china", "Chinese Chinese Shanghai".split(" ")));
        list.add(new LabeledPoint("china", "Chinese Macao".split(" ")));
        list.add(new LabeledPoint("japan", "Tokyo Japan Chinese".split(" ")));
        model = new NaiveBayesMulti();
        model.fit(list);
    }

    public void testPriorProb() throws Exception {


        model.printProb("Chinese");

        assertEquals(model.getPriorProb("china"), (double) 3 / 4, 1e-6);
        assertEquals(model.getPriorProb("japan"), (double) 1 / 4, 1e-6);
    }

    public void testWordProbByLabel() {
        assertEquals(model.getWordProbByLabel("china", "Chinese"), (double) 3 / 7, 1e-6);
        assertEquals(model.getWordProbByLabel("china", "Japan"), (double) 1 / 14, 1e-6);

        assertEquals(model.getWordProbByLabel("japan", "Chinese"), (double) 2 / 9, 1e-6);
        assertEquals(model.getWordProbByLabel("japan", "Japan"), (double) 2 / 9, 1e-6);

    }

    public void testPredict() {
        String[] testVector = "Chinese Chinese Chinese Tokyo Japan".split(" ");
        LabelProbability prob = model.predictProbability(testVector);
        System.out.println("0000000000000:"+prob.toString());


        assertEquals(prob.getLabel(), "china");
//        double expected = ((double) 3 / 4) * Math.pow(((double) 3 / 7), 3) * Math.pow(((double) 1 / 14), 2);
//        System.out.println("expected:" + expected);
//        assertEquals(prob.getProbability(), expected, 1e-6);
        double expected = Math.log((double) 3 / 4)
                + Math.log(Math.pow(((double) 3 / 7), 3))
                + Math.log(Math.pow(((double) 1 / 14), 2));
        System.out.println("===========expected:" + expected);
        assertEquals(prob.getProbability(), expected, 1e-6);
    }
}