package com.sidhant.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidhant.dto.request.DecodedRequest;
import com.sidhant.dto.request.EncodedRequest;
import com.sidhant.dto.response.DecodedResponse;
import com.sidhant.dto.response.EncodedResponse;
import com.sidhant.service.SecureMappingService;
import org.springframework.stereotype.Service;

@Service
public class SecureMappingServiceImpl implements SecureMappingService {
    @Override
    public DecodedResponse decoding(DecodedRequest decodedRequest) {
        DecodedResponse decodedResponse = new DecodedResponse();
        decodedResponse.setData(decodedRequest.getData());
        return decodedResponse;
    }

    @Override
    public EncodedResponse encoding(EncodedRequest encodedRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        EncodedResponse encodedResponse = objectMapper.convertValue(encodedRequest, new TypeReference<EncodedResponse>() {
        });
        return encodedResponse;
    }
}
