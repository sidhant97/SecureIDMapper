package com.sidhant.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sidhant.common.serializer.DecoderDeserializer;
import lombok.Data;

@Data
public class DecodedRequest {

    @JsonDeserialize(using = DecoderDeserializer.class)
    private String data;
}
