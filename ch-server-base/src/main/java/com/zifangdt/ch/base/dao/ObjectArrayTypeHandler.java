package com.zifangdt.ch.base.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * Created by 袁兵 on 2018/1/22.
 */
public class ObjectArrayTypeHandler extends BaseTypeHandler<Object[]> {

    @Override
    public Object[] getResult(ResultSet rs, String columnName) throws SQLException {
        return getNullableResult(rs, columnName);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object[] parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null || parameter.length == 0) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            ps.setString(i, StringUtils.join(parameter, ","));
        }
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        if (StringUtils.isEmpty(string)) {
            return new Object[0];
        }
        return StringUtils.splitByWholeSeparator(string, ",");
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
