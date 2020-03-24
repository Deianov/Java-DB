package com.cardealer.util;

public interface RandomUtil {
    int randomIndex(int exclusiveMax);
    long randomId(long inclusiveMaxId);
    long randomId(long inclusiveMinId, long inclusiveMaxId);
}
