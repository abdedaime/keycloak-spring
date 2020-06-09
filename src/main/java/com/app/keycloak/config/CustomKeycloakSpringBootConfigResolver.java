package com.app.keycloak.config;

import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.context.annotation.Configuration;

/**
 * to  replace keycloak.json and load config keyckloak from properties or yml spring boot ,
 */
@Configuration
public class CustomKeycloakSpringBootConfigResolver extends KeycloakSpringBootConfigResolver {
  private final KeycloakDeployment keycloakDeployment;

  public CustomKeycloakSpringBootConfigResolver(KeycloakSpringBootProperties properties) {
    keycloakDeployment = KeycloakDeploymentBuilder.build(properties);
  }

  @Override
  public KeycloakDeployment resolve(HttpFacade.Request facade) {
    return keycloakDeployment;
  }
}
