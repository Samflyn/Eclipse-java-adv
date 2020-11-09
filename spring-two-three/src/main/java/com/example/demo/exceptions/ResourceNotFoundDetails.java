package com.example.demo.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
// when working with super classes use @SuperBuilder
public class ResourceNotFoundDetails {
    private String title;
    private int status;
    private String details;
    private LocalDateTime localDateTime;
    private String message;
}
