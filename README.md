# Core API Backend

### Description:

* Core API Backend - Backend which utilizes the twitch API to provide functionality

### Documentation:

There is no application documentation. However, there is a REST documentation for developers below.

### Requirements:

Maven is required.

A MariaDB server is required.

For the default local profile there is a development database configured in application-dev.properties.

You cannot run the application without a profile (--spring-profiles.active)

In order to modify code in your IDE, please make sure that "annotation processing" is enabled.

### Environment Variables (Only production):

* DATABASE_PASSWORD: Password for the sql database
* JWT_SECRET: JWT secret

### Execution Variables:

* --spring.profiles.active=production
* --spring.profiles.active=dev

### Used Profiles:

1. Default - Never use on a production system!
2. Production - Changes authentication, variables in properties and possible some other classes. Don't use on locally!

### Configuration:

See *application-dev.properties* and *application-production.properties*

### REST Documentation:

You can find the REST documentation at <http://localhost/swagger-ui.html>.

The REST documentation is not available with the production profile.

The Error codes may vary due to internal spring error checks.

### HTML-Endpoints:

* <https://core.kantanbot.com/>

### Dependencies:

* See *build.gradle*
* Lombok
