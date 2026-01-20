## Prerequisites

- Java JDK (tested with JDK 17+; the build will also work on newer JDKs)
- Apache Maven

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
