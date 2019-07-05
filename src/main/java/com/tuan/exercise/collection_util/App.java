package com.tuan.exercise.collection_util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class App {
    public static void main(String[] args) throws IOException {

        final int circleNum = 100;
        List<Circle> listCircle = new ArrayList<>();

        for (int i = 0; i < circleNum; i++) {
            listCircle.add(new Circle(new Random().nextInt(99) + 1));
        }
        // sort the list of circle
        Collections.sort(listCircle);

        // read and validate input for circle's area
        InputStreamReader inReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inReader);
        boolean inputCorrect = false;
        float area = 0.0f;
        while (!inputCorrect) {
            System.out.print("Enter your choice of Circle area: ");

            try {
                area = Float.valueOf(bufferedReader.readLine());

                if (area < 0.0f) {
                    throw new NegativeNumberException();
                }

                inputCorrect = true;

            } catch (NumberFormatException ex) {
                System.out.println("Must enter an real number");
            } catch (NegativeNumberException e) {
                System.out.println("Must enter a positive number");
            }
        }

        printClosestAreaAndIndexes(area, listCircle);
    }

    private static void printClosestAreaAndIndexes(float area, List<Circle> sortedCircleList) {
        if (sortedCircleList.size() == 0)
            return;

        float cmpRadius = (float) Math.sqrt(area / Math.PI);

        int closestRad = 0;
        int sameCount = 0;
        int firstInd = 0;
        float diff = Math.abs(cmpRadius - sortedCircleList.get(0).getRadius());
        // 
        for (int i = 1; i < sortedCircleList.size(); i++) {
            int radius = sortedCircleList.get(i).getRadius();
            float newDiff = Math.abs(cmpRadius - radius);

            if (newDiff < diff) {
                diff = newDiff;
                closestRad = radius;
                sameCount = 1;
                firstInd = i;
            } else if (newDiff == diff) {
                sameCount++;
            }
        }

        System.out.println("List of index with area " + (closestRad * closestRad * Math.PI) + ": ");
        for (int i = firstInd; i < firstInd + sameCount; i++) {
            System.out.println(i);
        }
    }
}
