# Phase 4: Testing and Deployment

## 1. Purpose and scope

This document describes how to verify and deploy the Book Exchange Portal after implementation. It is specific to the current Java Servlet/JSP application and its MySQL schema. It covers:

- Build and packaging checks
- Database readiness and safe test-data preparation
- Functional, validation, session, authorization, and JSON checks
- Acceptance criteria and recording test evidence
- Local run and Apache Tomcat deployment procedures
- Deployment verification, troubleshooting, backup, and rollback

This is a test and deployment procedure, not a claim that every manual test below has already been executed. The repository currently has no `src/test` test suite; functional testing must be performed against a running application and a configured MySQL database.

## 2. Application profile

| Item | Current project configuration |
|---|---|
| Build | Maven WAR project |
| Java source/target | Java 25 |
| Servlet API | Servlet 4.0 (`javax.servlet`) |
| JSP API | JSP 2.3 (`javax.servlet.jsp`) |
| Database | MySQL; database name `book` |
| JDBC driver | MySQL Connector/J 8.2.0 |
| Local development server | Jetty 12 EE8 Maven plugin |
| Local port and context | `8080`, `/book-exchange-portal` |
| WAR artifact | `target/book-exchange-portal.war` |
| Tomcat compatibility | Tomcat 9.x (Servlet 4 / `javax.servlet`) |

**Important compatibility note:** this application uses `javax.servlet`, so use Tomcat 9.x for the WAR deployment described here. Tomcat 10 and later use `jakarta.servlet`; they are not drop-in compatible with this application without a migration.

## 3. Test prerequisites

Before testing, confirm:

1. JDK 25 is installed and `java -version` reports version 25.
2. Maven 3.9 or later is installed and `mvn -version` uses the intended JDK. On the project owner's Windows setup, Maven may be installed at `%USERPROFILE%\.maven\maven-3.9.15\bin\mvn.cmd`.
3. MySQL is running and reachable on the configured JDBC host and port.
4. The MySQL account configured in `src/main/java/com/bookexchange/config/DatabaseConfig.java` can connect to the `book` database and has the required table privileges. The checked-in default is local development configuration; confirm it matches the actual local MySQL account.
5. The schema and test data have been initialized, or an existing database has been reviewed and backed up before any schema changes.
6. Port 8080 is available for the local Jetty run. For Tomcat, confirm the configured HTTP port is available.
7. Testing uses disposable accounts and books. Do not test destructive flows against real user records.

### 3.1 Safe database preparation

The setup script is [`../database.sql`](../database.sql). It creates the `book` database and tables if absent and inserts sample user and book data. It is a setup/seed script, **not a general-purpose database migration**. In particular:

- Do not execute it against a valuable existing database without first making and verifying a backup.
- `CREATE TABLE IF NOT EXISTS` does not add newly required columns to tables created by an older schema version.
- Seed statements may add or update the sample records. Review the script before running it on a database containing records you need to preserve.
- For an existing installation, compare the table definitions with the script and apply a reviewed migration or restore into a separate test database rather than assuming the script upgraded the schema.

For functional testing, prefer a separate test database or disposable local database. Start with a verified backup if the project is already connected to a database containing important data.

## 4. Build and packaging checks

Run these commands from the project directory:

```powershell
$env:JAVA_HOME = "$env:USERPROFILE\.jdk\jdk-25.0.4.1+1"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn clean test
mvn package
```

If Maven is not on `PATH` in the Windows environment, use its full path:

```powershell
& "$env:USERPROFILE\.maven\maven-3.9.15\bin\mvn.cmd" clean test
& "$env:USERPROFILE\.maven\maven-3.9.15\bin\mvn.cmd" package
```

Expected checks:

- Maven exits successfully for both commands.
- Java compilation completes without errors.
- `target/book-exchange-portal.war` exists after packaging.
- The WAR includes the JSP pages, CSS/JavaScript assets, `WEB-INF/web.xml`, compiled application classes, and runtime dependencies. Servlet/JSP APIs are container-provided and should not be bundled as application runtime libraries.
- Review the Maven test summary. A successful build with “No tests to run” is only a compile/package check; it is not evidence that the functional scenarios in this document passed.

The application does not currently include automated tests under `src/test`. Record manual scenario results separately using the evidence template in Section 8.

## 5. Functional test plan

### 5.1 Test data and roles

Create two ordinary test accounts through the registration page:

- **Student A**: lists the book and receives an exchange request.
- **Student B**: searches for Student A's book and requests an exchange.

For administration checks, use a dedicated disposable test account and grant it the administrator role only in the test database. Do not promote a real account or use the production database for this scenario. Keep the administrator and ordinary-user sessions separate.

Use a test book with a unique title and ISBN so it can be found reliably. Keep its initial status `AVAILABLE`.

### 5.2 User registration and login

| ID | Procedure | Expected result |
|---|---|---|
| U-01 | Open `/register`; submit a new valid name, email, password, and optional profile information. | Registration succeeds and returns the user to the login flow. The new user is present once in `users`. |
| U-02 | Register the same email again. | Registration is rejected with a clear duplicate-email message; no duplicate user row is created. |
| U-03 | Submit registration with a missing required name, email, or password. | Registration is rejected and the form shows an error. |
| U-04 | Submit an invalid email, a password shorter than the form's stated minimum, or an invalid optional phone value. | The request is rejected. Verify server-side checks by submitting directly as well as using the browser form. |
| U-05 | Log in with a valid account. | Login succeeds and opens the authenticated dashboard. |
| U-06 | Log in with an incorrect password or an unknown email. | Login is rejected without creating an authenticated session; an error is shown. |
| U-07 | Log out, then request an authenticated page such as `/dashboard`. | The session is ended and access redirects to login. |

The database schema has a unique constraint on user email; the application also performs a duplicate check. Database uniqueness remains the final protection against concurrent duplicate registrations.

### 5.3 Profile management

| ID | Procedure | Expected result |
|---|---|---|
| P-01 | Update the signed-in user's name, phone, department, and course. | Valid changes persist and are reflected in the profile and navigation. |
| P-02 | Clear the required name or submit values that exceed the corresponding database column limits. | The update is rejected with a validation message and does not truncate or corrupt stored data. |
| P-03 | Change the password using the correct current password and matching valid new-password fields. | The change succeeds; the old password no longer works and the new password does. |
| P-04 | Submit a wrong current password, mismatched confirmation, or too-short new password. | The change is rejected and the existing password remains usable. |

### 5.4 Book listing, search, and ownership

| ID | Procedure | Expected result |
|---|---|---|
| B-01 | As Student A, add a book with the required title, author, ISBN, category, and condition. | The listing is saved with Student A as its owner and `AVAILABLE` status. |
| B-02 | Leave required fields blank or submit malformed price/year values. | The listing is rejected and an actionable validation error appears. No incomplete book row is inserted. |
| B-03 | Search by a unique title, author, ISBN, and category separately. | Matching books are returned. A category filter and a keyword search can be used together. |
| B-04 | Open a book's details from the dashboard. | Details and owner/listing information correspond to the selected book. |
| B-05 | As Student A, edit the book and verify the changed details. | The owner can update the listing; the changes are persisted. |
| B-06 | As Student B, attempt to edit or delete Student A's book by changing the submitted book ID. | The operation is refused; the other user's book is unchanged. |
| B-07 | Delete a disposable book owned by the current user. | The listing is removed and is no longer returned in searches. Related request behavior follows the database's configured foreign-key/cascade behavior and DAO deletion logic. |
| B-08 | Mark a disposable listing exchanged, then search for it. | Confirm the actual dashboard/search behavior. The current search DAO should be checked for status filtering; if exchanged books appear in the “Available Books” results, record this as a defect rather than marking the scenario passed. |

### 5.5 Exchange lifecycle

| ID | Procedure | Expected result |
|---|---|---|
| E-01 | As Student B, submit an exchange request for Student A's available book, optionally including a note. | One request is stored with Student B as requester, Student A as owner, and `PENDING` status. |
| E-02 | As Student B, try to request the same book again while the first request is pending. | A duplicate pending request is prevented or reported; it does not create a second pending row. |
| E-03 | As Student A, open incoming requests. | The request appears with the correct book, requester, note, and pending status. |
| E-04 | As Student A, reject a pending request. | The request changes to `REJECTED`; the requester can see the updated status. |
| E-05 | Create a new request and accept it as Student A. | The request changes to `ACCEPTED`, the book changes to `EXCHANGED`, and the result is reflected in the listing/request views. |
| E-06 | Attempt to accept or reject an already processed request, or process it as a user who is not the owner. | The operation is refused or has no effect; the request's completed status is not overwritten. |
| E-07 | As Student B, cancel a pending outgoing request. | The request becomes `CANCELLED`; unrelated requests are unchanged. |
| E-08 | Attempt to request one's own book or a book that is not available. | The request is rejected and no pending request row is created. |
| E-09 | With two pending requesters for one book, accept one request. | Exactly one exchange completes. Verify the other pending request is no longer actionable and no second acceptance can exchange the same book again. |

Run the multiple-requester scenario only with disposable test records. It verifies that the request and book state stay consistent when requests compete for the same listing.

### 5.6 Administration and authorization

| ID | Procedure | Expected result |
|---|---|---|
| A-01 | Open `/admin` as an ordinary signed-in user. | Access is denied or redirected to the dashboard; administrative data and actions are not available. |
| A-02 | Open `/admin` as the dedicated test administrator. | User, book, and exchange activity summaries and lists load. |
| A-03 | Promote/demote a disposable user using the administrator interface. | The role change persists and the resulting access behavior matches the role. |
| A-04 | Delete a disposable user. | The selected account and its dependent data are handled according to DAO/schema behavior; other users' data remains intact. |
| A-05 | Delete a disposable book from the admin panel. | The selected book is deleted; other books and unrelated requests remain intact. |
| A-06 | Modify a submitted user/book/request ID to target a different account's data as an ordinary user. | The operation is denied or has no effect. |

Keep at least one administrator account available in the test database. Do not test deleting or demoting the only administrator on a shared database.

### 5.7 JSON endpoint and user interface

| ID | Procedure | Expected result |
|---|---|---|
| J-01 | Request `/api/books` in a browser or API client. | The response has `Content-Type: application/json` and is valid JSON. |
| J-02 | Request `/api/books?search=<unique-title>` and a category-filtered query. | Returned entries match the supplied filters and have the expected JSON fields. |
| J-03 | Use a title or owner name containing quotes, a backslash, or line breaks in a disposable record, then retrieve JSON. | The response remains valid JSON; text is escaped rather than breaking the JSON document. |
| UI-01 | Open the home, login, register, dashboard, details, profile, book-management, exchange, and admin pages at desktop and mobile viewport widths. | No page is blank or materially clipped; navigation and the principal form actions remain usable. |
| UI-02 | Submit forms with the mouse and keyboard; check required fields and error/success messages. | Controls are understandable and validation feedback is visible. |

## 6. Database verification queries

Use a read-only database client for verification where possible. Substitute the test email, unique ISBN, and unique title values created for the current run.

```sql
USE book;

-- Verify the test account exists once.
SELECT id, full_name, email, role, created_at
FROM users
WHERE email = 'student-a-test@example.edu';

-- Verify the test listing and its owner/status.
SELECT b.id, b.title, b.isbn, b.status, u.email AS owner_email
FROM books b
JOIN users u ON u.id = b.user_id
WHERE b.isbn = 'TEST-ISBN-001';

-- Verify the request lifecycle and both participants.
SELECT er.id, b.title, requester.email AS requester_email,
       owner.email AS owner_email, er.status, er.requested_at, er.notes
FROM exchange_requests er
JOIN books b ON b.id = er.book_id
JOIN users requester ON requester.id = er.requester_id
JOIN users owner ON owner.id = er.owner_id
WHERE b.isbn = 'TEST-ISBN-001'
ORDER BY er.requested_at DESC;
```

Do not run `DELETE`, `TRUNCATE`, or broad `UPDATE` statements as test cleanup on a shared or production database. Remove only explicitly identified disposable test records after verifying their IDs and dependencies, or discard the isolated test database.

## 7. Local deployment with Jetty

1. Start MySQL and verify that the configured database credentials work.
2. From the repository root, run:

   ```powershell
   mvn clean package
   mvn jetty:run
   ```

   If Maven is not on `PATH` in the project owner's Windows setup:

   ```powershell
   & "$env:USERPROFILE\.maven\maven-3.9.15\bin\mvn.cmd" clean package
   & "$env:USERPROFILE\.maven\maven-3.9.15\bin\mvn.cmd" jetty:run
   ```

3. Wait for Jetty to report that the server connector has started on port 8080.
4. Open `http://localhost:8080/book-exchange-portal/`.
5. Check the home, login, and registration pages, then run the database-backed scenarios in Section 5.
6. Stop the development server with `Ctrl+C` in the terminal.

The repository's `run.bat` starts the configured Maven Jetty goal using JDK 25 from `%USERPROFILE%\.jdk\jdk-25.0.4.1+1` and Maven 3.9.15 from `%USERPROFILE%\.maven\maven-3.9.15`. Adjust those paths if the tools are installed elsewhere.

## 8. Apache Tomcat deployment

### 8.1 Compatibility and prerequisites

- Use JDK 25 for the build.
- Use Apache Tomcat 9.x for this Servlet 4 / `javax.servlet` application.
- Ensure the MySQL server is reachable from the Tomcat host.
- Configure the database URL, username, and password in `DatabaseConfig.java` for the target environment before packaging. The current implementation uses source-level configuration; this project does not currently provide a container-managed JNDI `DataSource` configuration.
- Never deploy a development database with real user information as a demonstration/test database.

### 8.2 Package the application

From the project root:

```powershell
mvn clean package
```

Confirm that `target/book-exchange-portal.war` exists and that the build completed successfully. Keep a copy of the currently deployed WAR and a database backup before replacing a deployed version.

### 8.3 Deploy

1. Stop or prepare the Tomcat service according to the installation's normal deployment procedure.
2. Back up the current application WAR/directory and back up the database.
3. Copy `target/book-exchange-portal.war` to Tomcat's `webapps` directory.
4. Start Tomcat and wait for the application context to deploy.
5. Open:

   ```text
   http://localhost:8080/book-exchange-portal/
   ```

   Replace `localhost` and `8080` with the deployed host and configured HTTP port.
6. Check Tomcat logs for deployment exceptions, JSP compilation errors, missing dependency errors, and JDBC connection failures.
7. Run the smoke checks in Section 8.4.

### 8.4 Post-deployment smoke checks

| Check | Pass condition |
|---|---|
| Home page | Portal landing page loads over the intended host/context. |
| Authentication pages | Login and registration JSPs render without server errors. |
| Database connectivity | A disposable test account can register and log in; no JDBC error is logged. |
| Book operations | A disposable listing can be created, viewed, searched, edited, and removed by its owner. |
| Exchange flow | A request can be created and reviewed by its owner; status changes persist. |
| Authorization | An ordinary user cannot access or perform administrator actions. |
| JSON endpoint | `/api/books` returns valid JSON with the JSON content type. |
| Logout/session | Logout returns the user to login and protected pages require authentication. |
| Logs | No unexpected exception, stack trace, JSP error, or repeated database connection failure appears. |

### 8.5 Production-readiness cautions

Passing the smoke checks establishes basic deployment functionality, not a full production security certification. Before exposing the application beyond a controlled academic/demo environment:

- Replace source-level database credentials with an external secret/configuration mechanism and ensure secrets are not committed.
- Review seeded demo users and sample content; remove or change demonstration credentials before public deployment.
- Serve the site over HTTPS and configure secure session cookies at the deployment layer.
- Review authorization checks, cross-site request forgery protections, output encoding, and error handling across all routes and JSPs.
- Use a least-privilege MySQL account rather than a database administrator account.
- Establish database backup, retention, restoration, and incident procedures.
- Review logging to avoid recording passwords, secrets, or unnecessary personal data.

These are deployment recommendations and require separate implementation/configuration work where the current project does not already provide them.

## 9. Test execution record

Complete one record per test run. Do not mark a test as passed without executing it against the deployed/test application.

| Field | Record |
|---|---|
| Tester / role | |
| Date and time | |
| Application version / Git commit | |
| Java version | |
| Maven version | |
| Server and version (Jetty/Tomcat) | |
| MySQL version and test database | |
| Build result and WAR filename | |
| Test IDs executed | |
| Passed / failed / blocked | |
| Defect summary and evidence location | |
| Deployment URL/context | |
| Final acceptance decision | |

Suggested evidence includes the Maven build output, test case IDs and results, redacted screenshots of successful and failed validation, relevant database query results, and relevant server-log excerpts with credentials and personal data removed.

## 10. Acceptance criteria

Phase 4 is complete when all of the following are true:

1. A clean build and WAR packaging complete successfully.
2. The test database is isolated or backed up, and its schema supports the current application code.
3. All applicable user, book, exchange, admin, validation, and JSON cases in Section 5 have recorded results.
4. No unresolved critical defect prevents registration/login, book management, exchange processing, or access control.
5. The WAR deploys on the intended Tomcat 9.x/Jetty EE8 environment without application startup or JSP compilation errors.
6. Post-deployment smoke checks pass and their results are recorded.
7. A database backup and rollback path have been confirmed before replacing an existing deployment.
8. Known limitations and any failed or blocked tests are documented; a successful build alone is not treated as complete functional testing.

## 11. Troubleshooting

| Symptom | Checks |
|---|---|
| `mvn` is not recognized | Use the configured Maven `mvn.cmd` path or add Maven's `bin` directory to `PATH`. |
| Java version/build failure | Run `java -version` and `mvn -version`; ensure Maven is using JDK 25. |
| Port 8080 is already in use | Stop the other local server or configure an unused port in the chosen server before launching. |
| HTTP 404 after deployment | Verify Tomcat started, the WAR deployed without errors, and the URL includes `/book-exchange-portal`. |
| JDBC connection refused | Check that MySQL is running, host/port are correct, and network/firewall rules allow the connection. |
| Access denied for database user | Verify the configured username/password and grants for the `book` database; do not solve this by using an unrestricted privileged account in production. |
| Missing table or column | Confirm the application is connected to the intended database and compare its schema with `database.sql`; `CREATE TABLE IF NOT EXISTS` does not migrate old tables. |
| JSP or servlet class errors on Tomcat | Confirm Tomcat 9.x is used and inspect the deployment logs for missing dependencies or compilation details. |
| Login fails for a sample account | Verify the actual stored record and password format in the test database; do not assume seed data was loaded into an existing database. |
| Test data remains after a failed scenario | Identify the specific disposable rows and their relationships before cleanup; use an isolated database reset when possible. |

## 12. Rollback

If the new deployment fails:

1. Stop the application using the installation's normal service procedure.
2. Restore the previous WAR/application directory from the deployment backup.
3. Restore the database only if the deployment applied a database change and the backup has been verified. Do not restore or overwrite the database merely to roll back an application-only change.
4. Start the previous deployment and repeat the minimal home-page, login, and database connectivity checks.
5. Preserve the failed deployment logs and the test record for diagnosis.
