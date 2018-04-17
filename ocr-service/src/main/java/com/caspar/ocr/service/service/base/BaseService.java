package com.caspar.ocr.service.service.base;


import com.caspar.ocr.persistent.entity.base.BaseEntity;

import java.util.List;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018/3/13
 */
public interface BaseService<T extends BaseEntity> {

    public T selectByKey(Integer id);

    public T save(T entity);

    public T saveNotNull(T entity);

    public List<T> select(T entity);

    public T oneSelect(T entity);

    public int delete(Integer id);

    public int delete(Integer... id);

    public int delete(T entity);

    public int updateAll(T entity);

    public int selectCount(T entity);

    public int updateNotNull(T entity);

    public List<T> selectAll();

    public List<T> selectByExample(Object example);

}
