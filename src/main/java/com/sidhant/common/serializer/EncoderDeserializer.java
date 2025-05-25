package com.sidhant.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.sidhant.common.SecureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EncoderDeserializer extends JsonDeserializer<String> {

    private Logger logger = LoggerFactory.getLogger(EncoderDeserializer.class);

    @Autowired
    public EncoderDeserializer(SecureUtils secureUtils) {
        this.secureUtils = secureUtils;
    }

    public EncoderDeserializer() {
    }

    private static SecureUtils secureUtils;

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String encrpytedValue = "";
        JsonNode node = null;
        try {
            ObjectCodec objectCodec = jsonParser.getCodec();
            node = objectCodec.readTree(jsonParser);
            encrpytedValue = jsonParser.getText();
            if (node != null) {
                encrpytedValue = secureUtils.encode(node.toString(), secureUtils.getSecretSerialization());
            } else {
                encrpytedValue = node.toString();
            }
        } catch (Exception e) {
            logger.error("Exception ", e);

        }
        return encrpytedValue;

    }
}
