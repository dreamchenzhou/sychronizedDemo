package com.wefree.demo.lockutils.semaphore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 *  有界HashSet
 * @param <T>
 */
public class BoundHashSet<T> {
    private Semaphore semaphore;
    private Set<T> set;

    private Object lock = new Object();

    private BoundHashSet(){

    }
    public BoundHashSet(int bounds){
        semaphore = new Semaphore(bounds);
        set = Collections.synchronizedSet(new HashSet<>());
    }

    public boolean add(T item) throws InterruptedException {
        semaphore.acquire();
        boolean wasAddTrue = false;
        try {
            wasAddTrue= set.add(item);
            return wasAddTrue;
        }finally {
            if(!wasAddTrue){
                semaphore.release();
            }
        }
    }

    public boolean remove(T item){
        boolean wasRemove = false;
        try{
            wasRemove = set.remove(item);
            return wasRemove;
        }finally {
            if(wasRemove){
                semaphore.release();
            }
        }
    }

    public boolean removeNext(){
        boolean wasRemove = false;
        try {
            Iterator<T> iterable = set.iterator();
            if (iterable.hasNext()) {
                wasRemove = set.remove(set.iterator().next());
            }
            return wasRemove;
        }finally {
            if(wasRemove){
                semaphore.release();
            }
        }
    }

    public int size() {
        return set.size();
    }

}
