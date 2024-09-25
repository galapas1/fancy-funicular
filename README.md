![Alt text](./imgs/fancy-funicular.jpeg?raw=true "Fancy Funicular Project")

## Solution Summary

- [Backend service](src) - Kotlin Spring Boot Application

The fancy-funicular accepts and returns perferences (e.g., pizza toppings).

Note the presence of a `scripts/` directory under the project root. These scripts
will aid in exercising the endpoints exposed by the application.

- `GET /api/v1/health` - see `scripts/check-health.sh`
- `POST /api/v1/preferences/` - see `scripts/post-prefs.sh`
- `GET /api/v1/preferences/{emailAddress}` - see `scripts/get-prefs.sh`
- `GET /api/v1/preferences/` - see `scripts/all-prefs.sh`

## Tech Stack

- Kotlin
- Spring Boot (v3.3.4)
- Spring Data JPA
- Spring Validation
- Spring Security + API Key
- Maven

## Build and Run
- mvn clean # omit to retain the db
- mvn compile package
- java -jar ./target/fancy-funicular-0.0.1-SNAPSHOT.jar --spring.config.location=target/classes/application.properties

Note - these are provided in the `run.sh` located in the project root

