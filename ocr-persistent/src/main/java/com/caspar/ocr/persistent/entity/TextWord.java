package com.caspar.ocr.persistent.entity;

import com.caspar.ocr.persistent.entity.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "text_word")
public class TextWord extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String word;

    /**
     * 类型,0-商城,1-金额相关
     */
    @Column(name = "word_type")
    private Integer wordType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return word
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * 获取类型,0-商城,1-金额相关
     *
     * @return word_type - 类型,0-商城,1-金额相关
     */
    public Integer getWordType() {
        return wordType;
    }

    /**
     * 设置类型,0-商城,1-金额相关
     *
     * @param wordType 类型,0-商城,1-金额相关
     */
    public void setWordType(Integer wordType) {
        this.wordType = wordType;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}