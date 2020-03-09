package com.springdata.exercises.utils;

import com.springdata.exercises.exception.IllegalRepositoryIndexException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
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
        return ThreadLocalRandom.current().nextLong(minId, (maxId + 1));
    }
}
