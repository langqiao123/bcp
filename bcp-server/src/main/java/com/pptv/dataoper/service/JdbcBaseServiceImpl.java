package com.pptv.dataoper.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pptv.dataoper.dao.JdbcBaseDao;

public class JdbcBaseServiceImpl<T> implements JdbcBaseService<T> {
	@Autowired
	private JdbcBaseDao<T> jdbcDao;

	@Override
    public int save(T entity) {
        return jdbcDao.save(entity);
    }

    @Override
    public void update(T entity) {
        jdbcDao.update(entity);
    }

    @Override
    public void delete(T entity) {
        jdbcDao.delete(entity);
    }

    @Override
    public void delete(Serializable id) {
        jdbcDao.delete(id);
    }

    @Override
    public void deleteAll() {
        jdbcDao.deleteAll();
    }

    @Override
    public T findById(Serializable id) {
        return jdbcDao.findById(id);
    }

    @Override
    public List<T> findAll() {
        return jdbcDao.findAll();
    }

    @Override
    public void batchDelete(Serializable[] ids) {
        jdbcDao.batchDelete(ids);
    }

    @Override
    public void batchUpdate(List<T> list) {
        jdbcDao.batchUpdate(list);
    }

    @Override
    public void batchSave(List<T> list) {
        jdbcDao.batchSave(list);
    }
}
