package com.phaeris.rolex.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author wyh
 * @since 2024/8/5
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeUtil {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getActualType(Class<?> clazz) {
        //获取构造器类型中的泛型参数的实际类型
        //转换为ParameterizedType
        ParameterizedType paramType = (ParameterizedType) clazz.getGenericSuperclass();
        //获取实际的类型参数
        Type[] actualTypeArguments = paramType.getActualTypeArguments();
        //将Type转换为Class
        return (Class<T>) actualTypeArguments[0];
    }
}
