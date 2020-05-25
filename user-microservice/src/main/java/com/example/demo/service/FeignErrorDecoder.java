package com.example.demo.service;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 400 -> {
                return null;
            }
            case 404 -> {
                return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                        String.format("%s is not found", s));
            }
            default -> {
                return new IllegalStateException("Unexpected value: " + response.status());
            }
        }
    }
}
