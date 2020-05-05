
[![Build State](https://github.com/baloise/codes-and-texts/workflows/CI/badge.svg)](https://github.com/baloise/codes-and-texts/actions)

# Codes and Texts
Project to replace the old Baloise [**CuT**](https://translate.google.com/?op=translate&sl=de&tl=en&text=Codes%20und%20Texte "'Codes und Texte'") application.

## Local Setup for postgres running in docker 
```
docker run --name postgres_cat -e POSTGRES_DB=cat -e POSTGRES_USER=cat -e  POSTGRES_PASSWORD=cat -d -p 5433:5432 postgres:12-alpine
```

# Getting Started
## Start Server
```shell script
./gradlew bootRun -Pdev
```

## Start Client
```shell script
npm ci

cd ./cat-client
ng serve
```

## Start Generator
