package com.app.keycloak.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomKeycloakAuthenticationProvider  extends KeycloakAuthenticationProvider {
   private Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;

    // you can  check here user in database or in external resource
    Collection<? extends GrantedAuthority> grantedAuthorities = addUserSpecificAuthorities(authentication);
    return new KeycloakAuthenticationToken(token.getAccount(), token.isInteractive(), grantedAuthorities);

  }


  private Collection<? extends GrantedAuthority> addUserSpecificAuthorities( //
      Authentication authentication
  ) {
    logger.info("user connected is {} ",authentication.getName());
    // you can load GrantedAuthority from database or any external resource , here just in demonstration

    Set<GrantedAuthority> result = new HashSet<>();
    result.add(new SimpleGrantedAuthority("ROLE_USER"));
   result.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

    return result;
  }




}
