package com.zifangdt.ch.base.dao;

import com.zifangdt.ch.base.dto.ticket.IP;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import javax.management.RuntimeMBeanException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IPTypeHandler extends BaseTypeHandler<IP>{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IP parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getInt());
    }

    @Override
    public IP getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return IP.fromInt(rs.getInt(columnName));
    }

    @Override
    public IP getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new RuntimeException("unsupport");
    }

    @Override
    public IP getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new RuntimeException("unsupport");
    }
}
