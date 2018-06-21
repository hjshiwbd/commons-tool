package commons.tool.utils;

import java.util.Collection;

public class CollectionUtil {

    /**
     * collection中查找某个元素的值
     *
     * @param collection
     * @param fieldName  属性值的名称
     * @param value      属性值
     * @return Object[], [0]下标, [1]找到返回元素, 找不到返回null
     */
    public static <T> Object[] find(Collection<T> collection, String fieldName, Object value) {
        int i = 0;
        Object[] arr = new Object[2];
        for (T t : collection) {
            Object v = ReflectUtil.invokeGetMethod(t, fieldName);
            if (v != null && v.equals(value)) {
                arr[0] = i;
                arr[1] = t;
                return arr;
            }
            i++;
        }
        return null;
    }
}
