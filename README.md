java-app-skeleton
=================
	java起手工程, 由jhipster生成的工程进行了微调

# Develop
To start your application in the dev profile, run:
```
mvn
```

# Building for production

## Packaging as jar

To build the final jar and optimize the jwt application for production, run:
```
mvn -Pprod clean verify
```    

## Packaging as war

To package your application as a war in order to deploy it to an application server, run:
```
mvn -Pprod,war clean verify
```


# References
- [jhipster](https://www.jhipster.tech/)