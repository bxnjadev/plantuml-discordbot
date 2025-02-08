package net.ibxnjadev.plantuml.util;

import java.lang.reflect.Array;

public class PlantumlArrays {

    private PlantumlArrays() {}

    /**
     * Slice the array obtained only a part
     * For example, if we have the next integer array:
     * A = [20,10,15, 80, 90]
     * We can obtain the next array making a slice between
     * the index 1 to 3
     * A = [10,15,80]
     *
     * @param array The array for slice
     * @param clazz The clazz type
     * @param start the index where the array start to slice
     * @param end the index where the array finish to slice
     * @return a new array with just the values specified
     * @param <T> the type array
     */

    public static <T> T[] slice(Object[] array,
                    Class<T> clazz,
                    int start,
                    int end) {
        int length = array.length;
        if(start > end || end > length) {
            throw  new ArrayIndexOutOfBoundsException("The end is major that the start or the end is major that the size the array");
        }

        int resultLength = length - (Math.abs(end - start) + 1);
        T[] result = (T[]) Array.newInstance(clazz, resultLength);
        for(int i = 0; i < result.length; i ++, start++){
            result[i] = (T) array[start];
        }
        return result;
    }

    /**
     * Slice the array obtained only a part begin to the zero
     * For example, if we have the next integer array:
     * A = [20,10,15, 80, 90]
     * We can obtain the next array making a slice between
     * the index 1 to 3
     * A = [10,15,80]
     *
     * @param array The array for slice
     * @param clazz The clazz type
     * @param end the index where the array finish to slice
     * @return a new array with just the values specified
     * @param <T> the type array
     */


    public <T> T[] slice(Object[] array,
                        Class<T> clazz,
                        int end) {
        return slice(array, clazz, 0, end);
    }

}
