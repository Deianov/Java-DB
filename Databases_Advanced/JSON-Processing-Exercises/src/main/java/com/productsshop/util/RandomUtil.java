package com.productsshop.util;

import java.util.Optional;

public interface RandomUtil {
    Optional<Long> randomId (Long maxId);
    Optional<Long> randomId(long minId, Long maxId);
}
