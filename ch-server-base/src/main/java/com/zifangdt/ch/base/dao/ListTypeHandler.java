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
public class ListTypeHandler extends BaseTypeHandler<List<?>> {

    private static final Set<String> LIST_COLUME_SET = new HashSet<>(Arrays.asList(
            "attachments",
            "finance_attachments",
            "img",
            "user_orga",
            "photos",
            "p_img",
            "operators",
            "brands"));

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<?> parameter, JdbcType jdbcType) throws SQLException {
        if (CollectionUtils.isEmpty(parameter)) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            ps.setString(i, StringUtils.join(parameter.stream().filter(Objects::nonNull).collect(Collectors.toList()), ","));
        }
    }

    @Override
    public List<?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        if (LIST_COLUME_SET.contains(columnName)) {
            return Arrays.asList(StringUtils.splitByWholeSeparator(string, ","));
        } else {
            return Arrays.stream(StringUtils.splitByWholeSeparator(string, ",")).filter(StringUtils::isNotEmpty).map(Long::valueOf).collect(Collectors.toList());
        }
    }

    @Override
    public List<?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
