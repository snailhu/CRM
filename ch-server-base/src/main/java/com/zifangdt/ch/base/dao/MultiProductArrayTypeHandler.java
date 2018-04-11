package com.zifangdt.ch.base.dao;

import com.zifangdt.ch.base.dto.contract.ProductDetail;
import com.zifangdt.ch.base.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * Created by 袁兵 on 2018/1/22.
 */
public class MultiProductArrayTypeHandler extends BaseTypeHandler<ProductDetail.MultiProduct[]> {

    @Override
    public ProductDetail.MultiProduct[] getResult(ResultSet rs, String columnName) throws SQLException {
        return getNullableResult(rs, columnName);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ProductDetail.MultiProduct[] parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null || parameter.length == 0) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            ps.setString(i, JsonUtil.stringify(parameter));
        }
    }

    @Override
    public ProductDetail.MultiProduct[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        return JsonUtil.parse(string, ProductDetail.MultiProduct[].class);
    }

    @Override
    public ProductDetail.MultiProduct[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProductDetail.MultiProduct[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
