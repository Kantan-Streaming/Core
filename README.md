# KantanBot Core API Backend

### Description:

* KantanBot Core API Backend - Backend which utilizes the twitch API to provide functionality

### Documentation:

There is no application documentation. However, there is a REST documentation for developers below.

### Requirements:

A MariaDB server is required.

For the default local profile there is a development database configured in application-dev.properties.

You cannot run the application without a profile (--spring-profiles.active)

In order to modify code in your IDE, please make sure that "annotation processing" is enabled.

### Instructions for Live Reload

Verify that the option check-box File->Setting –> Build, Execution, Deployment –> Compiler–>Build project automatically
is selected.

Press SHIFT+CTRL+A for Linux/Windows users or Command+SHIFT+A for Mac users, then type registry in the opened pop-up
window. Scroll down to Registry... using the down arrow key and hit ENTER on Registry.... In the Registry window verify
the following option: compiler.automake.allow.when.app.running = true.

### Environment Variables:

* DATABASE_CONNECTION: Full string for database connection
* DATABASE_USERNAME: Username for the sql database
* DATABASE_PASSWORD: Password for the sql database
* JWT_SECRET: JWT secret
* SERVICE_DOMAIN: devapi/api, version to deploy via CI/CD
* OAUTH_ID: Twitch oauth id
* OAUTH_SECRET: Twitch oauth secret
* OAUTH_CALLBACK: Twitch oauth callback url

### Execution Variables:

* --spring.profiles.active=production
* --spring.profiles.active=dev

### Used Profiles:

1. Default - Never use on a production system!
2. Production - Changes authentication, variables in properties and possible some other classes. Don't use locally!

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