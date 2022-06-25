package io.github.ponderyao.ddd.common.util;

/**
 * ObjectUtil：基础对象工具类
 *
 * @author Ponder Yao
 * @version 1.0.0
 */
public class ObjectUtils {

    /**
     * 判断对象是否为空
     * @param object 对象
     * @return true：空  false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断对象是否非空
     * @param object 对象
     * @return true：非空  false：空
     */
    public static boolean isNotNull(Object object) {
        return !ObjectUtils.isNull(object);
    }

}
