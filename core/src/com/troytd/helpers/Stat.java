package com.troytd.helpers;

/**
 * A class to hold a stat.
 *
 * @param <T> Type of the stat value
 */
public class Stat<T> {
    private String name;
    private T value;
    private int level;

    public Stat(String name, T value, int level) {
        this.name = name;
        this.value = value;
        this.level = level;
    }

    public Stat(String name, T value) {
        this(name, value, 0);
    }

    public Stat(T value) {
        this("", value);
    }

    public Stat() {
        this("", null);
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel(int level) {
        this.level += level;
    }

    public String toString() {
        return name + ": " + value + " (" + level + ")";
    }

    public void set(Stat<T> stat) {
        this.name = stat.name;
        this.value = stat.value;
        this.level = stat.level;
    }

    public void set(String name, T value, int level) {
        this.name = name;
        this.value = value;
        this.level = level;
    }

    public void set(String name, T value) {
        this.name = name;
        this.value = value;
        this.level = 0;
    }
}
