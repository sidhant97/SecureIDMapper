package com.sidhant.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sidhant.common.serializer.EncoderDeserializer;
import lombok.Data;

@Data
public class EncodedResponse {
    @JsonDeserialize(using = EncoderDeserializer.class)
    private String data;
}
