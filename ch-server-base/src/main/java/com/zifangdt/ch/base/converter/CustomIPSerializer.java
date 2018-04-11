package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zifangdt.ch.base.dto.ticket.IP;

import java.io.IOException;

public class CustomIPSerializer extends JsonSerializer<IP>{
    @Override
    public void serialize(IP value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(value.getIpAddress());
    }
}
