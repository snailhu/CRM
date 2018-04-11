package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zifangdt.ch.base.util.DateUtil;

import java.io.IOException;
import java.util.Date;

/**
 * Created by 袁兵 on 2017/11/10.
 */
public class JsonDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(DateUtil.format(value, "yyyy-MM-dd"));
    }
}
