package com.tuan.exercise.collection_util;

public class Circle implements Comparable<Circle> {

    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int compareTo(Circle circle) {
        return (this.radius - circle.getRadius());
    }
}
