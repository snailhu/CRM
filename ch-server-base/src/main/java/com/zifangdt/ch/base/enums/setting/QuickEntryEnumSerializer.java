package com.zifangdt.ch.base.enums.setting;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class QuickEntryEnumSerializer extends StdSerializer<QuickEntryEnum> {

    public QuickEntryEnumSerializer(Class<QuickEntryEnum> t){
        super(t);
    }

    public QuickEntryEnumSerializer(){
        super(QuickEntryEnum.class);
    }

    @Override
    public void serialize(QuickEntryEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("display", value.getDisplay());
        gen.writeStringField("verboseDisplay", value.getVerboseDisplay());
        gen.writeEndObject();
    }
}
