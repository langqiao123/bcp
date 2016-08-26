package com.pptv.bcp.api.service;

import java.io.Serializable;
import java.util.List;

public interface JdbcBaseService<T> {

    int save(T entity);

    void update(T entity);

    void delete(T entity);

    void delete(Serializable id);

    void deleteAll();

    T findById(Serializable id);

    List<T> findAll();

    void batchDelete(Serializable[] ids);

    void batchUpdate(List<T> list);

    void batchSave(List<T> list);
}