package com.zifangdt.ch.base.dao;

import com.zifangdt.ch.base.dto.finance.InventoryItem;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.dto.product.entity.PurchaseItem;
import com.zifangdt.ch.base.dto.product.entity.StockChangeItem;
import com.zifangdt.ch.base.dto.ticket.*;
import com.zifangdt.ch.base.util.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 参考https://github.com/abel533/Mapper/issues/198
 */
@MappedTypes(value = {ReceiveInfo.class, String[].class, ReturnVisit.class, Clearing.class, ReceiptDto.class,
        Remark[].class, InventoryItem[].class, PurchaseItem[].class, StockChangeItem[].class, Long[].class, Journal.JournalDetail[].class})
public class JsonObjectTypeHandler<T extends Object> extends BaseTypeHandler<T>{

    private Class<T> type;

    public JsonObjectTypeHandler(Class<T> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        String s = JsonUtil.stringify(parameter);
        ps.setString(i, s);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String column = rs.getString(columnName);
        if (StringUtils.isEmpty(column)) return null;
        return JsonUtil.parse(rs.getString(columnName), type);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
