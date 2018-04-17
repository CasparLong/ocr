package com.caspar.ocr.persistent.entity.base;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018/3/12
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8085972019022784632L;

    public <T> T convertExt(Class<T> clazz) {
        String json = JSON.toJSONString(this);
        T t = JSON.parseObject(json, clazz);
        return t;
    }

}
