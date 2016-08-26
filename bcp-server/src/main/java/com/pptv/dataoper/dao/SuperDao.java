package com.pptv.dataoper.dao;

import java.util.List;

/**
 * 
 * @author mingyangyang
 * 
 */
public interface SuperDao {
    /**
     * 添加白名单
     *
     * @param field
     * @return
     */
    public SuperDao include(String... field);
    /**
     * 添加黑名单
     *
     * @param field
     * @return
     */
    public SuperDao exclude(String... field);
    /**
     * asc 排序属性
     *
     * @param field the field
     * @return the super dao
     */
    public SuperDao asc(String... field);
    /**
     * desc 排序属性
     *
     * @param field the field
     * @return the super dao
     */
    public SuperDao desc(String... field);
    /**
     * 设置操作属性
     *
     * @param fieldName the field name
     * @param value the value
     * @return super dao
     */
    public SuperDao set(String fieldName, Object value);
    /**
     * 设置where条件属性
     *
     * @param fieldName
     * @param values
     * @return
     */
    public SuperDao where(String fieldName, Object... values);
    /**
     * 设置where条件属性
     *
     * @param fieldName the field name
     * @param operator the operator
     * @param values the values
     * @return super dao
     */
    public SuperDao whereAssign(String fieldName, String operator, Object... values);
    /**
     * 按设置的参数及条件更新
     *
     * @param clazz
     */
    public void update(Class<?> clazz);
    /**
     * 按设置的条件删除
     * 
     * @param clazz
     */
    public void delete(Class<?> clazz);
    /**
     * 按设置的条件查询
     * 
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> query(Class<T> clazz);
    /**
     * 插入一条记录
     *
     * @param entity
     * @return
     */
    public Long insert(Object entity);
    /**
     * 插入一条记录，不生成主键，主要用于oracle这类非自增长主键类型
     *
     * @param entity
     */
    public void add(Object entity);
    /**
     * 更新一条记录
     *
     * @param entity
     */
    public void update(Object entity);
    /**
     * 删除记录
     *
     * @param clazz the clazz
     * @param id the id
     */
    public void delete(Class<?> clazz, Long id);
    /**
     * 删除记录 此方法会以实体中不为空的字段为条件
     *
     * @param entity
     */
    public void delete(Object entity);
    /**
     * 删除所有记录
     * @param clazz the clazz
     */
    public void deleteAll(Class<?> clazz);
    /**
     * 得到记录
     *
     * @param clazz
     * @param id
     * @return
     */
    public <T> T getById(Class<T> clazz, Long id);
    /**
     * 查询单个记录
     *
     * @param <T>   the type parameter
     * @param entity the entity
     * @return t t
     */
    public <T> T querySingleResult(T entity);
    /**
     * 查询记录数
     *
     * @param entity
     * @return
     */
    public int queryCount(Object entity);
    /**
     * 查询列表
     *
     * @param entity the entity
     * @return the list
     */
    public <T> List<T> queryList(T entity);
    /**
     * 查询所有列表
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> findAll(Class<T> clazz);
}
