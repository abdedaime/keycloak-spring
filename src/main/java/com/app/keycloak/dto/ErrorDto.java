package com.app.keycloak.dto;

import java.util.Objects;

public class ErrorDto {
  private  String    codeError;
  private String messageError;
  private String detailsException;

  private ErrorDto(String codeError, String messageError, String detailsException) {
    this.codeError = codeError;
    this.messageError = messageError;
    this.detailsException = detailsException;
  }

  public static ErrorDto of(String codeError, String messageError, String detailsException){
     return  new ErrorDto(codeError, messageError,detailsException);
  }

  public String getCodeError() {
    return codeError;
  }

  public String getMessageError() {
    return messageError;
  }

  public String getDetailsException() {
    return detailsException;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ErrorDto)) {
      return false;
    }
    ErrorDto errorDto = (ErrorDto) o;
    return Objects.equals(codeError, errorDto.codeError) &&
        Objects.equals(messageError, errorDto.messageError) &&
        Objects.equals(detailsException, errorDto.detailsException);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codeError, messageError, detailsException);
  }
}
