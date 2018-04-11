package com.zifangdt.ch.base.dao;

import com.zifangdt.ch.base.enums.pair.IntVerifierEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 袁兵 on 2017/9/11.
 */
public class IntVerifierEnumTypeHandler extends BaseTypeHandler<IntVerifierEnum> {

    private Class<IntVerifierEnum> clazz;

    public IntVerifierEnumTypeHandler(Class<IntVerifierEnum> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IntVerifierEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIntVerifier());
    }

    @Override
    public IntVerifierEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        return rs.wasNull() ? null : convert(i);
    }

    @Override
    public IntVerifierEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        return rs.wasNull() ? null : convert(i);
    }

    @Override
    public IntVerifierEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        return cs.wasNull() ? null : convert(i);
    }

    private IntVerifierEnum convert(int value) {
        for (IntVerifierEnum intVerifierEnum : clazz.getEnumConstants()) {
            if (intVerifierEnum.getIntVerifier() == value) {
                return intVerifierEnum;
            }
        }
        return null;
    }
}
