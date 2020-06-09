package com.app.keycloak.config;

import com.app.keycloak.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * if you need to Override your  AuthenticationFailureHandler
 * and replace {@Link AuthenticationFailureHandler KeycloakAuthenticationFailureHandler }
 * to return custom object en json
 */

public class CustomKeycloakAuthenticationFailureHandler implements AuthenticationFailureHandler {

   private ObjectMapper objectMapper = new ObjectMapper();
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    ErrorDto errorDto = ErrorDto
        .of("UNAUTHORIZED", "Unable to authenticate using the Authorization header", exception.getMessage());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    String jsonError = objectMapper.writeValueAsString(errorDto);
    response.getWriter().write(jsonError);
  }


}
