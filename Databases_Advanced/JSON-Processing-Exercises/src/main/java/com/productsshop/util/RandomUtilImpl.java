package com.productsshop.util;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomUtilImpl implements RandomUtil {
    private static final long MIN_ID = 1L;

    public RandomUtilImpl() {
    }

    @Override
    public Optional<Long> randomId (Long maxId){
        return this.randomId(MIN_ID, maxId);
    }

    @Override
    public Optional<Long> randomId(long minId, Long maxId){
        if(maxId == null || minId < MIN_ID || maxId < minId){
            return Optional.empty();
        } else if (minId == maxId){
            return Optional.of(maxId);
        }
        return Optional.of(ThreadLocalRandom.current().nextLong(minId, (maxId + 1L)));
    }
}
