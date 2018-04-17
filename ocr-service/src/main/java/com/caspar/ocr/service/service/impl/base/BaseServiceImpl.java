package com.caspar.ocr.service.service.impl.base;

import com.caspar.ocr.persistent.entity.base.BaseEntity;
import com.caspar.ocr.service.service.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018/3/13
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T>, InitializingBean {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getActualTypeClass() {
        Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            entityClass = (Class<T>) p[0];
        }
        return entityClass;
    }

    @Override
    public T selectByKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T save(T entity) {
        //entity.setCreateTime(new Date());
        //entity.setUpdateTime(new Date());
        Integer result = mapper.insert(entity);
        if (result > 0) {
            this.logger.info("save:{}--result:{}", entity.getClass(), result);
        }
        return entity;
    }

    @Override
    public T saveNotNull(T entity) {
        Integer result = mapper.insertSelective(entity);
        if (result > 0) {
            this.logger.info("save:{}--result:{}", entity.getClass(), result);
        }
        return entity;
    }

    @Override
    public List<T> select(T entity) {
        return mapper.select(entity);
    }

    @Override
    public int delete(Integer... id) {
        if (id != null) {
            int c = 0;
            for (int i = 0; i < id.length; i++) {
                c = c + mapper.deleteByPrimaryKey(id[i]);
            }
            return c;
        } else {
            throw new RuntimeException("id is not null");
        }

    }

    @Override
    public int updateAll(T entity) {
        //entity.setUpdateTime(new Date());
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        //entity.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int selectCount(T entity) {
        return mapper.selectCount(entity);
    }

    @Override
    public List<T> selectAll() {
        return this.mapper.selectAll();
    }


    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int delete(Integer id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delete(T entity) {
        return this.mapper.delete(entity);
    }

    @Override
    public T oneSelect(T entity) {
        return this.mapper.selectOne(entity);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.logger.info("{} initialized...", this.getClass());
    }

}
