package me.Skid.utils.particles;

import java.util.Collection;
import java.util.LinkedList;

public final class EvictingList extends LinkedList {

    private final int maxSize;

    public EvictingList(int maxSize) {
        this.maxSize = maxSize;
    }

    public EvictingList(Collection c, int maxSize) {
        super(c);
        this.maxSize = maxSize;
    }

    public boolean add(Object t) {
        if (this.size() >= this.maxSize) {
            this.removeFirst();
        }

        return super.add(t);
    }

    public boolean isFull() {
        return this.size() >= this.maxSize;
    }
}
