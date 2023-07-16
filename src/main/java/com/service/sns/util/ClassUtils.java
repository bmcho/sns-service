package com.service.sns.util;

import java.util.Optional;

public class ClassUtils {

    public static <T> Optional<T> getSafeCastInstance(Object obj, Class<T> clazz) {
        return clazz != null && clazz.isInstance(obj) ? Optional.of(clazz.cast(obj)) : Optional.empty();
    }
}
