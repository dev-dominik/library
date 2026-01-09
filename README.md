# Library — running the project

This repository contains a small Java backend under `packages/backend` and a Docker Compose file that starts a PostgreSQL database and pgAdmin.

## Prerequisites

- Java JDK (tested with JDK 17+; the build will also work on newer JDKs)
- Apache Maven
- Docker & Docker Compose (to run the bundled PostgreSQL service)

## Start the database (Docker Compose)

From the repository root run:

```bash
docker-compose up -d
```

This starts two services declared in `docker-compose.yml`:

- `postgres` — PostgreSQL 16 (database: `library`, user: `library_user`, password: `library_pass`)
- `pgadmin` — pgAdmin available on port `5050`

If you don't want to use Docker, create a PostgreSQL database with the same credentials and point the application to it.

## Build the backend with Maven

Build the backend module (skip tests during a quick iteration):

```bash
mvn -f "packages/backend/pom.xml" -DskipTests package
```

The produced artifact will be:
`packages/backend/target/backend-0.1.0-SNAPSHOT.jar`

## Run the backend

Option A — run the packaged JAR:

```bash
java -jar packages/backend/target/backend-0.1.0-SNAPSHOT.jar
```

Option B — run with Maven exec (useful during development):

```bash
mvn -f "packages/backend/pom.xml" exec:java
```

Notes:

- The `pom.xml` sets the main class to `com.library.app.Main`. If your entry point differs, update the `mainClass` in the pom.
- The application will need to know the database connection details. The Docker Compose file uses `library_user` / `library_pass` and DB `library`; ensure your app reads these values from environment variables or application config.
- If you change Java target version, update the `<release>`/`<maven.compiler.*>` properties in `packages/backend/pom.xml`.

## Troubleshooting

- If `mvn` is not found, install Apache Maven (Homebrew on macOS: `brew install maven`).
- If Docker is not running, start Docker Desktop or the Docker daemon before `docker-compose up`.
