package com.ubs.forex.validations.utils;

import java.util.Arrays;

public class ListUtils {

    public static <T> T[] concatArrays(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
