package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by 袁兵 on 2017/12/7.
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = p.getText();
        if (org.springframework.util.StringUtils.isEmpty(text)) {
            return null;
        }
        int count= StringUtils.countMatches(text,":");
        try {
            if (count==2) {
                return DateUtils.parseDate(text,"yyyy-MM-dd hh:mm:ss");
            } else if(count==1){
                return DateUtils.parseDate(text,"yyyy-MM-dd hh:mm");
            }else {
                return DateUtils.parseDate(text,"yyyy-MM-dd");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式有误.", e);
        }
    }

    @Override
    public Class<Date> handledType() {
        return Date.class;
    }
}
