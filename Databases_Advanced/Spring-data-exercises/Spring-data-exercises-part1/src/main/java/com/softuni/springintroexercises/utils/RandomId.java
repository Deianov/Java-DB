package com.softuni.springintroexercises.utils;

import com.softuni.springintroexercises.exception.IllegalRepositoryIndexException;

import java.util.concurrent.ThreadLocalRandom;

public class RandomId {
    private static final long MIN_ID = 1;

    public RandomId() {
    }

    public long get (long maxId){
        return this.get(MIN_ID, maxId);
    }

    public long get(long minId, long maxId){
        if(minId < MIN_ID || maxId < minId){
            throw new IllegalRepositoryIndexException("Illegal repository index : min=" + minId + ", max=" + maxId);
        }
        return ThreadLocalRandom.current().nextLong(minId, maxId + 1L);
    }
}
