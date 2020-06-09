package com.app.keycloak;

import org.keycloak.adapters.springsecurity.KeycloakAuthenticationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {


  @ExceptionHandler(KeycloakAuthenticationException.class)
  @ResponseBody
  public ResponseEntity<String> handleException(KeycloakAuthenticationException ex) {
     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(ex.getMessage());
  }
}