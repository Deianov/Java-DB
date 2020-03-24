package com.cardealer.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomUtilImpl implements RandomUtil {
    private static final long MIN_ID = 1L;
    private static final int MIN_INDEX = 0;

    public RandomUtilImpl() {
    }

    @Override
    public int randomIndex(int exclusiveMax) {
        return ThreadLocalRandom.current().nextInt(MIN_INDEX, exclusiveMax);
    }

    @Override
    public long randomId (long inclusiveMaxId){
        return this.randomId(MIN_ID, inclusiveMaxId);
    }

    @Override
    public long randomId(long inclusiveMinId, long inclusiveMaxId){
        if(inclusiveMinId < MIN_ID || inclusiveMaxId < inclusiveMinId){
            throw new IllegalArgumentException("Bad RandomUtilI input.");

        } else if (inclusiveMinId == inclusiveMaxId){
            return inclusiveMaxId;
        }
        return ThreadLocalRandom.current().nextLong(inclusiveMinId, (inclusiveMaxId + 1L));
    }
}
