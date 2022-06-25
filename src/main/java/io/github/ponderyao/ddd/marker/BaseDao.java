package io.github.ponderyao.ddd.marker;

import java.io.Serializable;
import java.util.List;

/**
 * BaseDao：基础数据访问对象
 * 
 * 数据访问对象的 Marker Interface 标记接口。<p>
 * 
 * BaseDao 与 PO（持久化对象 Persistence Object）绑定，一个 
 * PO 应有一个对应的 Dao 提供增删改查功能。<p>
 * 
 * 由于 PO 主键的多样性，BaseDao 应声明主键类型 ID，以正确生成
 * 对应的 selectById/delete 方法的参数类型。<p>
 * 
 * @author PonderYao
 * @since 1.0.0
 */
public interface BaseDao<ID extends Serializable, PO> {

    /**
     * 增加数据
     * @param po 持久化对象
     * @return 增加数据的行数：1/0
     */
    int insert(PO po);

    /**
     * 修改数据（覆盖）
     * @param po 持久化对象
     * @return 修改数据的行数：1/0
     */
    int update(PO po);

    /**
     * 根据 ID 查询数据
     * @param id 主键
     * @return 持久化对象
     */
    PO selectById(ID id);

    /**
     * 查询所有数据
     * @return 持久化对象列表
     */
    List<PO> selectAll();

    /**
     * 删除数据
     * @param id 主键
     * @return 持久化对象
     */
    int delete(ID id);
    
}
