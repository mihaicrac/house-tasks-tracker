package com.house.taskstracker.authentication.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("code", value.getHttpErrorCode());
        jsonGenerator.writeStringField("message", value.getLocalizedMessage());
        jsonGenerator.writeNumberField("status", value.getHttpErrorCode());
        jsonGenerator.writeEndObject();
    }
}
