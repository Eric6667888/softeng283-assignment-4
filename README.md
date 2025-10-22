# SOFTENG 283 - Assignment 4

See Canvas for instructions

Some useful commands:

Make sure you are in the project root when running the commands.

For Windows OS replace `./mvnw ` with `mvnw.cmd`

### Run code style

```bash
./mvnw clean compile exec:java@style
```


### Run unit tests:
```bash
./mvnw clean test
```


### Generate coverage report (JaCoCo):

```bash
./mvnw clean test jacoco:report
open target/site/jacoco/index.html
```

### Run mutation testing (PIT):

```bash
./mvnw clean test pitest:mutationCoverage@run-pitest
open target/pit-reports/index.html
```

### Submission:

- PDF report max 4 pages
- your GitHub repo (automatically cloned after the deadline)