package com.sidhant.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sidhant.common.SecureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DecoderDeserializer extends JsonDeserializer<String> {

    private Logger logger = LoggerFactory.getLogger(DecoderDeserializer.class);


    @Autowired
    public DecoderDeserializer(SecureUtils secureUtils) {
        this.secureUtils = secureUtils;
    }

    public DecoderDeserializer() {
    }

    private static SecureUtils secureUtils;

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String encodedValue = "";
        encodedValue = jsonParser.getText();
        String decrpytedValueString = "";
        if (!StringUtils.isEmpty(encodedValue)) {
            try {
                decrpytedValueString = secureUtils.decode(encodedValue, secureUtils.getSecretSerialization());

            } catch (Exception e) {
                logger.error("Exception ", e);
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid encoded value :" + encodedValue);
            }
        }

        return decrpytedValueString;
    }

}