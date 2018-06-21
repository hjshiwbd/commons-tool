package commons.tool.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;

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


    /**
     * clone list
     *
     * @param src
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> clone(List<T> src) {
        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }

}
