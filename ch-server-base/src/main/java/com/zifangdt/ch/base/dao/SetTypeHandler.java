package com.zifangdt.ch.base.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2017/9/11.
 */
public class SetTypeHandler extends BaseTypeHandler<Set<?>> {

    @Override
    public Set<?> getResult(ResultSet rs, String columnName) throws SQLException {
        return getNullableResult(rs, columnName);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<?> parameter, JdbcType jdbcType) throws SQLException {
        if (CollectionUtils.isEmpty(parameter)) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            ps.setString(i, StringUtils.join(parameter.stream().filter(Objects::nonNull).collect(Collectors.toSet()), ","));
        }
    }

    @Override
    public Set<?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        if (StringUtils.isEmpty(string)) {
            return new HashSet<>();
        }
        return Arrays.stream(StringUtils.splitByWholeSeparator(string, ",")).filter(StringUtils::isNotEmpty).map(Long::valueOf).collect(Collectors.toSet());
    }

    @Override
    public Set<?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
