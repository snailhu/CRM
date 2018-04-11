package com.zifangdt.ch.base.dao;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.util.ClassUtil;
import com.zifangdt.ch.base.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 袁兵 on 2017/9/11.
 */
public class BaseConfigDetailTypeHandler extends BaseTypeHandler<BaseConfigDetail> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseConfigDetail parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JsonUtil.stringify(parameter));
    }

    public static void main(String[] args){
        DetailOfOption o=new DetailOfOption();
        o.setDisabled(false);
        System.out.println(JsonUtil.stringify(o));
    }

    @Override
    public BaseConfigDetail getNullableResult(ResultSet rs, String columnName) throws SQLException {
        ConfigType configType = ConfigType.valueOf(rs.getString("type"));
        Class<? extends BaseConfigDetail> boClass = configType.getBoClass();
        String string = rs.getString(columnName);
        if (StringUtils.isEmpty(string)) {
            return ClassUtil.newInstance(boClass);
        }
        return JsonUtil.parse(string, boClass);
    }

    @Override
    public BaseConfigDetail getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseConfigDetail getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
