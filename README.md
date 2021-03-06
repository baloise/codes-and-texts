
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
