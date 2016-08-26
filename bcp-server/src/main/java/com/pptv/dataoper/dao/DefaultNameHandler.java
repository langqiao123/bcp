package com.pptv.dataoper.dao;

import com.pptv.util.DBFieldFmtUtil;

/**
 * 
 * @author mingyangyang
 * 默认名称处理handler
 */
public class DefaultNameHandler implements NameHandler{

	//主键后缀
	private static final String PRI_SUFFIX = "_id";
	
	/**
     * 根据实体名获取表名
     *
     * @param entityName
     * @return
     */
    @Override
    public String getTableName(String entityName) {
        //Java属性的骆驼命名法转换回数据库下划线“_”分隔的格式
        return DBFieldFmtUtil.getUnderlineName(entityName);
    }
    /**
     * 根据表名获取主键名
     *
     * @param entityName
     * @return
     */
    @Override
    public String getPrimaryName(String entityName) {
        String underlineName = DBFieldFmtUtil.getUnderlineName(entityName);
        //正如前面说到的，主键以表名加上“_id” 如user表主键即“_user_id”
        return underlineName + PRI_SUFFIX;
    }
    /**
     * 根据属性名获取列名
     *
     * @param fieldName
     * @return
     */
    @Override
    public String getColumnName(String fieldName) {
    	return DBFieldFmtUtil.getUnderlineName(fieldName);
    }
    
    public static void main(String[] args) {
		System.out.println(new DefaultNameHandler().getColumnName("pid"));
	}
}
