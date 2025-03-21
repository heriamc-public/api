package fr.heriamc.proxy.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AutoUpdatingList<T> {
    private final List<T> list;
    private final Comparator<? super T> comparator;

    public AutoUpdatingList(Comparator<? super T> comparator) {
        this.list = new ArrayList<>();
        this.comparator = comparator;
    }

    public AutoUpdatingList(int defaultSize, Comparator<? super T> comparator) {
        this.list = new ArrayList<>(defaultSize);
        this.comparator = comparator;
    }

    public synchronized void add(T element) {
        list.add(element);
        Collections.sort(list, comparator);
    }

    public synchronized List<T> getList() {
        return new ArrayList<>(list);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    public synchronized int size() {
        return list.size();
    }

    public synchronized void remove(T element) {
        list.remove(element);
        Collections.sort(list, comparator);
    }
}