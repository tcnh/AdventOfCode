package com.github.tcnh.advent.util;

/**
* Created by Tom on 15-12-2017.
*/
public class Node {
    private int size;
    private String name;
    private boolean hasChildren = false;
    private String[] children;

    public boolean hasChildren() {
        return hasChildren;
    }

    public void setHasChildren() {
        this.hasChildren = true;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }
}
