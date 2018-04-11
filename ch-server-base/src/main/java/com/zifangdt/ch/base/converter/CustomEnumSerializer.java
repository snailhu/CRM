package com.zifangdt.ch.base.converter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zifangdt.ch.base.enums.pair.IntVerifierEnum;
import com.zifangdt.ch.base.enums.ticket.DisplayableEnum;

import java.io.IOException;

/**
 * Created by 袁兵 on 2017/12/7.
 */
public class CustomEnumSerializer extends JsonSerializer<Enum> {

    @Override
    public void serialize(Enum value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (value instanceof IntVerifierEnum) {
            gen.writeNumber(((IntVerifierEnum) value).getIntVerifier());
        } else if (value instanceof DisplayableEnum) {
            gen.writeString(value.name());
            try {
                gen.writeStringField(gen.getOutputContext().getCurrentName() + "Name", ((DisplayableEnum) value).getName());
            } catch (JsonGenerationException e) {
                // TODO: 列表中有问题
            }
        } else {
            gen.writeString(value.name());
        }
    }
}
