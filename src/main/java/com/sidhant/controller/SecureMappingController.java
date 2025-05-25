package com.sidhant.controller;

import com.sidhant.dto.request.DecodedRequest;
import com.sidhant.dto.request.EncodedRequest;
import com.sidhant.dto.response.DecodedResponse;
import com.sidhant.dto.response.EncodedResponse;
import com.sidhant.service.SecureMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureMappingController {
    private Logger logger = LoggerFactory.getLogger(SecureMappingController.class);

    @Autowired
    private SecureMappingService secureMappingService;


    @PostMapping("/api/secure/encode")
    public ResponseEntity<Object> encoding(@RequestBody EncodedRequest encodedRequest) {
        EncodedResponse encodedResponse = secureMappingService.encoding(encodedRequest);
        return new ResponseEntity<>(encodedResponse, HttpStatus.OK);
    }

    @PostMapping("/api/secure/decode")
    public ResponseEntity<Object> decoding(@RequestBody DecodedRequest decodedRequest) {
        DecodedResponse decodedResponse = secureMappingService.decoding(decodedRequest);
        return new ResponseEntity<>(decodedResponse, HttpStatus.OK);
    }
}
