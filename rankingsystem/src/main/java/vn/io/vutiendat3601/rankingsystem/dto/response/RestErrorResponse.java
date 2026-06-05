package vn.io.vutiendat3601.rankingsystem.dto.response;

import org.springframework.http.HttpStatus;

public record RestErrorResponse(HttpStatus status, String errorCode, String message) {}
