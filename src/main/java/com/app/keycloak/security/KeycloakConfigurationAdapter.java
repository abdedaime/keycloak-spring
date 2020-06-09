package com.app.keycloak.security;

import com.app.keycloak.config.CustomKeycloakAuthenticationFailureHandler;
import com.app.keycloak.config.CustomKeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationFailureHandler;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class KeycloakConfigurationAdapter extends KeycloakWebSecurityConfigurerAdapter {

  // in the case  stateless (bearonly) there is no session
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  /**
   * AuthenticationManagerBuilder use default prodvider KeycloakAuthenticationProvider in my case i
   * override KeycloakAuthenticationProvider to manage authorization from external resourse
   * (database )
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider = new CustomKeycloakAuthenticationProvider();
    // simple Authority Mapper to avoid ROLE_
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  /**
   * keycloakAuthenticationProcessingFilter().setAuthenticationFailureHandler()
   * @param http
   * @throws Exception
   */

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http
        // disable csrf because of API mode
        .csrf().disable()
        .sessionManagement()
        // use previously declared bean
        .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // keycloak filters for securisation
        .and()
        .addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)
        .addFilterBefore(keycloakAuthenticationProcessingFilter(), X509AuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint())
        .and()
        // manage routes securisation here
        .authorizeRequests().antMatchers("/health", "/").permitAll()
        .antMatchers("/authenticated").authenticated();
     //   .antMatchers("/user").hasRole("USER")
    //    .antMatchers("/admin").hasRole("ADMIN")
      //  .anyRequest().denyAll();

  }

  /**
   *  goal for method to override error message if token invalid , override    {@link KeycloakAuthenticationFailureHandler}
   *  to return custome message with  status 401
   * @return
   * @throws Exception
   */

  private    KeycloakAuthenticationProcessingFilter customkeycloakAuthenticationProcessingFilter() throws  Exception{
    KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter =  keycloakAuthenticationProcessingFilter();
    keycloakAuthenticationProcessingFilter.setAuthenticationFailureHandler(new CustomKeycloakAuthenticationFailureHandler());
    return  keycloakAuthenticationProcessingFilter;
   }
}
