package io.github.ponderyao.ddd.util;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * SnapshotUtils：对象快照工具类
 * 
 * 调用 Apache-Commons-Lang 的 SerializationUtils 工具类实
 * 现，前提是被执行快照的对象的类必须实现 Serializable 接口。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class SnapshotUtils {
    
    public static <T extends Serializable> T snapshot(T obj) {
        return SerializationUtils.clone(obj);
    }
    
}
