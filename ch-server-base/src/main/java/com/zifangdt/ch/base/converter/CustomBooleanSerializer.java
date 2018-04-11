package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by 袁兵 on 2017/12/7.
 */
public class CustomBooleanSerializer extends JsonSerializer<Boolean> {

    @Override
    public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeBoolean(value);

        String currentName = gen.getOutputContext().getCurrentName();
        if (currentName.equals("disabled")) {
            gen.writeBooleanField("enabled", !value);
        } else if (currentName.endsWith("Disabled")) {
            gen.writeBooleanField(currentName.replace("Disabled", "Enabled"), !value);
        }
    }
}
