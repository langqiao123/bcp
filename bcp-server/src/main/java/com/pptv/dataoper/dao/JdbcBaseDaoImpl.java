package com.pptv.dataoper.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.Assert;

public class JdbcBaseDaoImpl<T> implements JdbcBaseDao<T> {
	
    public static final String SQL_INSERT = "insert";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_DELETE = "delete";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Class<T> entityClass;
    
    /** 名称加工处理器 */  
    private NameHandler nameHandler;  

    @SuppressWarnings("unchecked")
    public JdbcBaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        System.out.println("Dao实现类是：" + entityClass.getName());
    }

    @Override
    public int save(T entity) {
        String sql = this.makeSql(SQL_INSERT);
        Object[] args = this.setArgs(entity, SQL_INSERT);
        int[] argTypes = this.setArgTypes(entity, SQL_INSERT);
        return jdbcTemplate.update(sql, args, argTypes);
    }

    @Override
    public void update(T entity) {
        String sql = this.makeSql(SQL_UPDATE);
        Object[] args = this.setArgs(entity, SQL_UPDATE);
        int[] argTypes = this.setArgTypes(entity, SQL_UPDATE);
        jdbcTemplate.update(sql, args, argTypes);
    }

    @Override
    public void delete(T entity) {
        String sql = this.makeSql(SQL_DELETE);
        Object[] args = this.setArgs(entity, SQL_DELETE);
        int[] argTypes = this.setArgTypes(entity, SQL_DELETE);
        jdbcTemplate.update(sql, args, argTypes);
    }

    @Override
    public void deleteAll() {
        String sql = " TRUNCATE TABLE " + this.getActualNameHandler().getTableName(entityClass.getSimpleName());
        jdbcTemplate.execute(sql);
    }

    @Override
    public void delete(Serializable id) {
        String sql = " DELETE FROM " + this.getActualNameHandler().getTableName(entityClass.getSimpleName()) + " WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchSave(List<T> lt) {
        Assert.notEmpty(lt);
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(this.getActualNameHandler().getTableName(entityClass.getSimpleName()).toLowerCase()).append("( ");
        List<Object[]> params = new ArrayList<Object[]>();
        String val = "";

        for (T t : lt) {
            int index = 0;
            Field[] fields = t.getClass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                Object[] objVal = new Object[fields.length];

                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        Object obj = field.get(t);
                        if (params.size() == 0) {
                            sb.append(this.getActualNameHandler().getColumnName(field.getName())).append(" ,");
                            val += ", ? ";
                        }
                        objVal[index++] = obj;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                params.add(objVal);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        val = val.substring(1);
        sb.append(") value (").append(val).append(")");
        String sql = sb.toString();
        jdbcTemplate.batchUpdate(sql, params);
    }

    @Override
    public void batchUpdate(List<T> lt) {
        Assert.notEmpty(lt);
        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(this.getActualNameHandler().getTableName(entityClass.getSimpleName()).toLowerCase()).append(" set ");
        List<Object[]> params = new ArrayList<Object[]>();
        Object primaryKey = "id";
        for (T t : lt) {
            int index = 0;
            Field[] fields = entityClass.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                Object id = null;
                Object[] objVal = new Object[fields.length];
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        Object obj = field.get(t);
                        if (this.getActualNameHandler().getColumnName(field.getName()).equalsIgnoreCase("id")) {
                            primaryKey = obj;
                            id = obj;
                        } else {
                            if (params.size() == 0) {
                                sb.append(this.getActualNameHandler().getColumnName(field.getName())).append(" = ? ,");
                            }
                            objVal[index++] = obj;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    objVal[index] = id;
                }
                params.add(objVal);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" where ").append(primaryKey).append(" = ? ");
        String sql = sb.toString();
        jdbcTemplate.batchUpdate(sql, params);
    }

    @Override
    public T findById(Serializable id) {
        String sql = "SELECT * FROM " + this.getActualNameHandler().getTableName(entityClass.getSimpleName()) + " WHERE id=?";
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        return jdbcTemplate.query(sql, rowMapper, id).get(0);
    }

    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM " + this.getActualNameHandler().getTableName(entityClass.getSimpleName());
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Map<String, Object> findOne(String sql, Object... args) {
        return jdbcTemplate.queryForMap(sql, args);
    }

    @Override
    public List<Map<String, Object>> findListMap(String sql, Object... args) {
        return jdbcTemplate.queryForList(sql, args);
    }

    @Override
    public void execSQL(String sql) {
        jdbcTemplate.execute(sql);
    }

    @Override
    public SqlRowSet rowSet(String sql, Object... args) {
        return this.jdbcTemplate.queryForRowSet(sql, args);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 组装SQl
     * 
     * @param entityClass
     * @param sqlFlag
     * @return
     */
    public String makeSql(String sqlFlag) {
        StringBuffer sql = new StringBuffer();
        Field[] fields = entityClass.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            sql.append(" INSERT INTO " + this.getActualNameHandler().getTableName(entityClass.getSimpleName()));
            sql.append("(");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                sql.append(this.getActualNameHandler().getColumnName(fields[i].getName())).append(",");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(") VALUES (");
            for (int i = 0; fields != null && i < fields.length; i++) {
                sql.append("?,");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            sql.append(" UPDATE " + this.getActualNameHandler().getTableName(entityClass.getSimpleName()) + " SET ");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                String column = this.getActualNameHandler().getColumnName(fields[i].getName());
                if (column.equals("id")) { // id 代表主键
                    continue;
                }
                sql.append(column).append("=").append("?,");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id=?");
        } else if (sqlFlag.equals(SQL_DELETE)) {
            sql.append(" DELETE FROM " + this.getActualNameHandler().getTableName(entityClass.getSimpleName()) + " WHERE id=?");
        }
        System.out.println("SQL=" + sql);
        return sql.toString();
    }

    /**
     * 设置参数
     * 
     * @param entity
     * @param sqlFlag
     * @param entityClass
     * @return
     */
    public Object[] setArgs(T entity, String sqlFlag) {
        Field[] fields = entityClass.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            Object[] args = new Object[fields.length];
            for (int i = 0; args != null && i < args.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    args[i] = fields[i].get(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return args;
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            Object[] tempArr = new Object[fields.length];
            for (int i = 0; tempArr != null && i < tempArr.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    tempArr[i] = fields[i].get(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Object[] args = new Object[fields.length];
            System.arraycopy(tempArr, 1, args, 0, tempArr.length - 1); // 数组拷贝
            args[args.length - 1] = tempArr[0];
            return args;
        } else if (sqlFlag.equals(SQL_DELETE)) {
            Object[] args = new Object[1]; // 长度是1
            fields[0].setAccessible(true); // 暴力反射
            try {
                args[0] = fields[0].get(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return args;
        }
        return null;
    }

    /**
     * 设置参数类型（写的不全，只是一些常用的）
     * 
     * @param entity
     * @param sqlFlag
     * @param entityClass
     * @return
     */
    public int[] setArgTypes(T entity, String sqlFlag) {
        Field[] fields = entityClass.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            int[] argTypes = new int[fields.length];
            try {
                for (int i = 0; argTypes != null && i < argTypes.length; i++) {
                    fields[i].setAccessible(true); // 暴力反射
                    String filedType = fields[i].getGenericType().toString(); // 获取属性的类型
                    if (filedType.equals("class java.lang.String")) {
                    	argTypes[i] = Types.VARCHAR;
                    } else if (filedType.equals("class java.lang.Double")) {
                    	argTypes[i] = Types.DECIMAL;
                    } else if (filedType.equals("class java.lang.Integer")) {
                    	argTypes[i] = Types.INTEGER;
                    } else if (filedType.equals("class java.lang.Short")) {
                    	argTypes[i] = Types.INTEGER;
                    } else if (filedType.equals("class java.lang.Long")) {
                    	argTypes[i] = Types.INTEGER;
                    } else if (filedType.equals("class java.lang.Float")) {
                    	argTypes[i] = Types.FLOAT;
                    } else if (filedType.equals("class java.util.Date")) {
                    	argTypes[i] = Types.DATE;
                    }else if (filedType.equals("class java.lang.Boolean")) {
                    	argTypes[i] = Types.BOOLEAN;
                    }else if (filedType.equals("class java.sql.Timestamp")) {
                    	argTypes[i] = Types.TIMESTAMP;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            int[] tempArgTypes = new int[fields.length];
            int[] argTypes = new int[fields.length];
            try {
                for (int i = 0; tempArgTypes != null && i < tempArgTypes.length; i++) {
                    fields[i].setAccessible(true); // 暴力反射
                    if (fields[i].equals("class java.lang.String")) {
                    	argTypes[i] = Types.VARCHAR;
                    } else if (fields[i].equals("class java.lang.Double")) {
                    	argTypes[i] = Types.DECIMAL;
                    } else if (fields[i].equals("class java.lang.Integer")) {
                    	argTypes[i] = Types.INTEGER;
                    } else if (fields[i].equals("class java.lang.Short")) {
                    	argTypes[i] = Types.INTEGER;
                    } else if (fields[i].equals("class java.lang.Long")) {
                    	argTypes[i] = Types.INTEGER;
                    } else if (fields[i].equals("class java.lang.Float")) {
                    	argTypes[i] = Types.FLOAT;
                    } else if (fields[i].equals("class java.util.Date")) {
                    	argTypes[i] = Types.DATE;
                    }else if (fields[i].equals("class java.lang.Boolean")) {
                    	argTypes[i] = Types.BOOLEAN;
                    }else if (fields[i].equals("class java.sql.Timestamp")) {
                    	argTypes[i] = Types.TIMESTAMP;
                    }
                }
                System.arraycopy(tempArgTypes, 1, argTypes, 0, tempArgTypes.length - 1); // 数组拷贝
                argTypes[argTypes.length - 1] = tempArgTypes[0];

            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;

        } else if (sqlFlag.equals(SQL_DELETE)) {
            int[] argTypes = new int[1]; // 长度是1
            try {
                fields[0].setAccessible(true); // 暴力反射
                if (fields[0].get(entity).getClass().getName().equals("java.lang.String")) {
                    argTypes[0] = Types.VARCHAR;
                } else if (fields[0].get(entity).getClass().getName().equals("java.lang.Integer")) {
                    argTypes[0] = Types.INTEGER;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;
        }
        return null;
    }

    @Override
    public void batchDelete(Serializable[] ids) {
        String idStr="";
        for (int i = 0; i < ids.length; i++) {
            idStr += ",'" + ids[i] + "'";
        }
        String sql = " DELETE FROM " + this.getActualNameHandler().getTableName(entityClass.getSimpleName()) + " WHERE id in (?)";
        jdbcTemplate.update(sql, idStr.charAt(1));
    }
    
    /** 
     * 获取实际运行时的名称处理器 
     * 
     * @return 
     */  
    private NameHandler getActualNameHandler() {  
        if (nameHandler == null) {  
            synchronized (this) {  
                if (nameHandler == null) {  
                    nameHandler = this.getNameHandler();  
                }  
            }  
        }  
        return nameHandler;  
    }  
  
    /** 
     * 得到名称处理器，子类覆盖此方法实现自己的名称转换处理器 
     * 
     * @return 
     */  
    protected NameHandler getNameHandler() {  
        return new DefaultNameHandler();  
    }  

}