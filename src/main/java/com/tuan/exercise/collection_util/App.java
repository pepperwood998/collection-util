package com.tuan.exercise.collection_util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class App {

    private static final String MSG_INPUT_REQ = "Enter your choice of Circle area: ";
    private static final String MSG_ERROR_NUMBER_FORMAT = "Must enter an real number";
    private static final String MSG_ERROR_POSITIVE_NUMBER = "Must enter a positive number";

    public static void main(String[] args) throws IOException {

        final int circleNum = 100;
        List<Circle> listCircleSorted = new ArrayList<>();

        for (int i = 0; i < circleNum; i++) {
            listCircleSorted.add(new Circle(new Random().nextInt(99) + 1));
        }
        List<Circle> listCircle = new ArrayList<>(listCircleSorted);

        // read and validate input for circle's area
        InputStreamReader inReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inReader);
        boolean inputCorrect = false;
        float area = 0.0f;
        while (!inputCorrect) {
            System.out.print(MSG_INPUT_REQ);

            try {
                area = Float.valueOf(bufferedReader.readLine());

                if (area < 0.0f) {
                    throw new NegativeNumberException();
                }

                inputCorrect = true;

            } catch (NumberFormatException ex) {
                System.out.println(MSG_ERROR_NUMBER_FORMAT);
            } catch (NegativeNumberException e) {
                System.out.println(MSG_ERROR_POSITIVE_NUMBER);
            }
        }

        // sort the list of circle
        Collections.sort(listCircleSorted);
        printClosestAreaAndIndexesForSorted(area, listCircleSorted);

        printClosestAreaAndIndexes(area, listCircle);
    }

    private static void printClosestAreaAndIndexesForSorted(float cmpArea, List<Circle> sortedCircleList) {
        if (sortedCircleList == null || sortedCircleList.size() == 0)
            return;

        float cmpRadius = (float) Math.sqrt(cmpArea / Math.PI);

        int closestRad = 0;
        int frequency = 0;
        int firstInd = 0;
        float radDiff = Math.abs(cmpRadius - sortedCircleList.get(0).getRadius());
        // iterate through the list to find the closest radius and its first appearance
        for (int i = 1; i < sortedCircleList.size(); i++) {
            int radius = sortedCircleList.get(i).getRadius();
            float newDiff = Math.abs(cmpRadius - radius);

            if (newDiff < radDiff) {
                radDiff = newDiff;
                closestRad = radius;
                frequency = 1;
                firstInd = i;
            } else if (newDiff == radDiff) {
                frequency++;
            }
        }

        System.out.println("Closest area: " + (closestRad * closestRad * Math.PI) + ", list of index: ");
        for (int i = firstInd; i < firstInd + frequency; i++) {
            System.out.println(i);
        }
    }

    private static void printClosestAreaAndIndexes(float cmpArea, List<Circle> listCircle) {
        if (listCircle == null || listCircle.size() == 0)
            return;

        float cmpRadius = (float) Math.sqrt(cmpArea / Math.PI);

        // This tree map has the key as the difference between the 2 radiuses
        TreeMap<Float, List<Integer>> map = new TreeMap<>();

        for (int i = 0; i < listCircle.size(); i++) {
            Circle circle = listCircle.get(i);
            float diff = Math.abs(cmpRadius - circle.getRadius());

            List<Integer> listIndex = map.get(diff);
            if (listIndex == null) {
                listIndex = new LinkedList<Integer>();
                map.put(diff, listIndex);
            }
            listIndex.add(i);
        }

        // The first element in the tree map is the one with the closest area
        List<Integer> resultIndexes = map.get(map.firstKey());
        Circle resCircle = listCircle.get(resultIndexes.get(0));
        int closestRadius = resCircle.getRadius();
        System.out.println("Closest area: " + (closestRadius * closestRadius * Math.PI) + ", list of index: ");
        System.out.println(resultIndexes);
    }
}
