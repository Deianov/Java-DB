package com.productshop.util;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomUtilImpl implements RandomUtil {
    private static final long MIN_ID = 1L;
    private static final int MIN_INT = 0;

    public RandomUtilImpl() {
    }

    @Override
    public int randomInt(int maxId) {
        return ThreadLocalRandom.current().nextInt(MIN_INT, maxId + 1);
    }

    @Override
    public long randomId (long maxId){
        return this.randomId(MIN_ID, maxId);
    }

    @Override
    public long randomId(long minId, long maxId){
        if(minId < MIN_ID || maxId < minId){
            throw new IllegalArgumentException("Bad random max index.");
        } else if (minId == maxId){
            return maxId;
        }
        return ThreadLocalRandom.current().nextLong(minId, (maxId + 1L));
    }
}
