package com.app.keycloak.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainApi {


  @GetMapping(value = {"health", "/"})
  public String health() {
    return "ok";
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(value = {"admin"})
  public String admin() {
    return "admin path";
  }

  @PreAuthorize("hasRole('ROLE_USER')")
  @GetMapping(value = {"user"})
  public String user() {
    return "user path";
  }

  @GetMapping(value = {"authenticated"})
  public String authenticated() {
    return "authenticated path";
  }


}



