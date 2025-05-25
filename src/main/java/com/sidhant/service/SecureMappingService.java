package com.sidhant.service;

import com.sidhant.dto.request.DecodedRequest;
import com.sidhant.dto.request.EncodedRequest;
import com.sidhant.dto.response.DecodedResponse;
import com.sidhant.dto.response.EncodedResponse;

public interface SecureMappingService {

    DecodedResponse decoding(DecodedRequest decodedRequest);

    EncodedResponse encoding(EncodedRequest encodedRequest);
}
