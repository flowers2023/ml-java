package com.github2013.ml;

import com.github2013.ml.classification.nb.*;
import com.github2013.ml.utils.FileVectors;

import java.util.List;

/**
 * kmeans Main run test
 */
public class AppNaiveBayes {
    public static void main(String[] args) throws Exception {
        String happyPath = "/home/shuangfu/code/ml-java/data/happy.txt";
        String happyTestPath = "/home/shuangfu/code/ml-java/data/happy-test.txt";
        String sadPath = "/home/shuangfu/code/ml-java/data/sad.txt";
        String sadTestPath = "/home/shuangfu/code/ml-java/data/sad-test.txt";
        String type = "multinomial";
        double lowProp = 0;

        if (args.length >= 4) {
            happyPath = args[0];
            happyTestPath = args[1];
            sadPath = args[2];
            sadTestPath = args[3];
            type = args[4];
            lowProp = Double.valueOf(args[5]);
        }

        System.out.printf("path list:" +
                "\nhappy path:" + happyPath +
                "\nhappy test path:" + happyTestPath +
                "\nsad path:" + sadPath +
                "\nsad test path:" + sadTestPath +
                "\ntype:" + type

        );

        System.out.println("\n\nstat to read file..........");
        long toatalStartTime = System.currentTimeMillis();

        List<LabeledPoint> happyLabeledPoints = FileVectors.getNaiveBayesVector("happy", happyPath);
        List<LabeledPoint> happyTestLabeledPoints = FileVectors.getNaiveBayesVector("happy", happyTestPath);
        List<LabeledPoint> sadLabeledPoints = FileVectors.getNaiveBayesVector("sad", sadPath);
        List<LabeledPoint> sadTestLabeledPoints = FileVectors.getNaiveBayesVector("sad", sadTestPath);
        System.out.println("File read and convert to vector finished,total time:[" + (System.currentTimeMillis() - toatalStartTime) / 1000 + "] seconds.");

        System.out.println("-----------------start to train mode-------------------");
        long startTime = System.currentTimeMillis();
        NaiveBayes model = null;
        if (type.equals("multinomial")) {
            model = new NaiveBayesMulti();
        } else if (type.equals("bernoulli")) {
            model = new NaiveBayesBer();
        } else {
            System.out.println("type: not exists.");
            System.exit(1);
        }

        List<LabeledPoint> trainLabeledPoints = happyLabeledPoints;
        trainLabeledPoints.addAll(sadLabeledPoints);
        model.fit(trainLabeledPoints);
        System.out.println("Model train end,total time:[" + (System.currentTimeMillis() - startTime) / 1000 + "] seconds.");

//        List<String[]> testWords = new LinkedList<String[]>();
//        for (LabeledPoint v : happyTestLabeledPoints) {
//            String predictLabel = model.predict(v.getVordsVecotr());
//            System.out.println("predict:" + predictLabel + "\t,label:" + v.getLabel() +
//                    "\t,sentence:" + Arrays.toString(v.getVordsVecotr()));
//        }
//        for (LabeledPoint v : sadTestLabeledPoints) {
//            String predictLabel = model.predict(v.getVordsVecotr());
//            System.out.println("predict:" + predictLabel + "\t,label:" + v.getLabel() +
//                    "\t,sentence:" + Arrays.toString(v.getVordsVecotr()));
//        }

        System.out.println("-----------------start to cross validate-------------------");
        startTime = System.currentTimeMillis();
        List<LabeledPoint> testLabeledPoints = happyTestLabeledPoints;
        testLabeledPoints.addAll(sadTestLabeledPoints);
//        List<LabelValidate> testValidate = model.crossTest(testLabeledPoints);
        List<LabelValidate> testValidate = model.crossTest(testLabeledPoints, lowProp);
        System.out.println("cross validate end,total time:[" + (System.currentTimeMillis() - startTime) / 1000 + "] seconds.");
        System.out.println("-------------------cross validate result-------------------");
        long correct = 0;
        long wrong = 0;
        for (LabelValidate lv : testValidate) {
            correct += lv.getCorrectCount();
            wrong += lv.getWrongCount();
            System.out.println(lv.toString());
        }

        double accuracy = (double) correct / (correct + wrong);
        long testCount = correct + wrong;
        System.out.println("-----------low than prop：[" + lowProp + "],\n总条数：" + testLabeledPoints.size() +
                "\n测试条数：:" + testCount +
                "\n测试占比:" + ((int) (((double) testCount / testLabeledPoints.size()) * 100 * 100) / 100) + "%"
        );
        System.out.println("-----------准确率：" + (((int) (accuracy * 100 * 100)) / 100.0) + "%");

        System.out.println("Total time:[" + (System.currentTimeMillis() - toatalStartTime) / 1000 + "] seconds.");

    }
}
