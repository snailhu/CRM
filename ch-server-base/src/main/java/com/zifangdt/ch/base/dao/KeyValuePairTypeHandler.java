package com.zifangdt.ch.base.dao;

import com.zifangdt.ch.base.bo.KeyValuePair;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 袁兵 on 2017/9/11.
 */
public class KeyValuePairTypeHandler extends BaseTypeHandler<KeyValuePair> {

    private static final String VALUE_COLUMN_SUFFIX="_value";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, KeyValuePair parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i,parameter.getKey());
    }

    @Override
    public KeyValuePair getNullableResult(ResultSet rs, String columnName) throws SQLException {
        KeyValuePair keyValuePair=new KeyValuePair(rs.getLong(columnName));
        try{
            String value = rs.getString(columnName + VALUE_COLUMN_SUFFIX);
            keyValuePair.setValue(value);
        }catch (SQLException e){
            //SQL中不存在该列
        }
        return keyValuePair;
    }

    @Override
    public KeyValuePair getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public KeyValuePair getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
