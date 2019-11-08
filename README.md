[![Build Status](https://travis-ci.org/baloise/codes-and-texts.svg?branch=master)](https://travis-ci.org/baloise/codes-and-texts)

# Codes and Texts
Project to replace the old [**C**(odes)**u**(nd)**T**(exte)](https://translate.google.com/?op=translate&sl=de&tl=en&text=Codes%20und%20Texte).

## Local Setup for postgres running in docker 
```
docker run --name postgres_cat -e POSTGRES_DB=cat -e POSTGRES_USER=cat -e  POSTGRES_PASSWORD=cat -d -p 5433:5432 postgres:12-alpine
```
