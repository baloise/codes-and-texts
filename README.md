
[![Build State](https://github.com/baloise/codes-and-texts/workflows/CI/badge.svg)](https://github.com/baloise/codes-and-texts/actions)

# Codes and Texts
Project to replace the old Baloise [**CuT**](https://translate.google.com/?op=translate&sl=de&tl=en&text=Codes%20und%20Texte "'Codes und Texte'") application.

## Local Setup for postgres running in docker

* Setup Database 
```
docker run --name postgres_cat -e POSTGRES_DB=cat -e POSTGRES_USER=cat -e  POSTGRES_PASSWORD=cat -d -p 5433:5432 postgres:12-alpine
```
* Execute server to create tables & views over flyway
* Import data (data.zip)

# Getting Started
## Start Server
```shell script
cd cat-server
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

running on port 8088


## Start Vaadin Client
```shell script
cd cat-vaadin-client
mvn spring-boot:run
```
running on port 8080

## Start Generator
todo


## OpenShift Environment

[Codes and Texts Deployments](https://github.com/baloise-incubator/codes-and-texts-deployment)


## Sources

### For keycloak integration in vaddin
[1]: https://ramonak.io/posts/vaadin–keycloak–spring-security-integration
[2]: https://github.com/KaterinaLupacheva/spring-boot-vaadin-keycloak-demo/blob/master/vaadin-ui/src/main/java/io/ramonak/SecurityConfig.java
[3]: https://github.com/karkaletsis/vaadin-keycloak/blob/master/src/main/java/com/gnomon/examinations/config/SecurityConfiguration.java
[4]: https://www.keycloak.org/docs/4.8/securing_apps/#redirect-uris
