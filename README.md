# keycloak-spring
project spring boot with keycloak (keycloak adapter spring boot) , is application in mode staless .
this application  validate the token jwt , and it loads role from externale source like database or an external service .
it use customAuthenticationProvider to load these roles.
for running this app in local you need to do this steps :
1)- pull project in local 
2)- install keyclaok
3)-create realm in keycloak .
4)- create public client  or confidential with garnt type  password credentials or authorization code  .
5)- create client with mode bear only, export installation for this client ,  .
6)-  update application properties (realm , name fo client , url keycloak )
7)- launch this command gradle bootRun and test your api using keycloak .

