# Redux Server (Spring Boot Edition)

[![GitHub last commit](https://img.shields.io/github/last-commit/MichaelKaaden/redux-server-spring.svg)](https://github.com/MichaelKaaden/redux-server-spring/commits/master)
[![GitHub issues](https://img.shields.io/github/issues/MichaelKaaden/redux-server-spring.svg)](https://github.com/MichaelKaaden/redux-server-spring/issues)
[![license](https://img.shields.io/github/license/MichaelKaaden/redux-server-spring.svg)](https://github.com/MichaelKaaden/redux-server-spring)

This is a tiny REST service managing counters. The counters
are kept in memory, so they are reset every time you restart
the service.

Each counter has

- a unique index (a number greater or equal 0) and
- a value.

You can either get or set a counter. Of course, you shouldn't
set any counter in a distributed environment. Instead, you
should get it and then use the increment or decrement operations
on it. For presentations, it is a reasonable choice to set
some counters before showing anything to your audience.

The RESTful Web Service runs at [http://localhost:3000](http://localhost:3000).
Its Swagger UI is available
at [http://localhost:3000/swagger-ui/index.html](http://localhost:3000/swagger-ui/index.html) and the OpenAPI
description is available at [http://localhost:3000/v3/api-docs](http://localhost:3000/v3/api-docs).

## Running the service

```bash
$ ./gradlew bootRun
```

## Test

```bash
$ ./gradlew test
```

For the HTML report, see the directory `build/reports/tests/test`.

## Code Coverage (with JaCoCo)

This is generated everytime the unit tests are run. To do this separately:

```bash
$ ./gradlew jacocoTestReport
```

As usual, you'll find the HTML report in the directory `build/reports/jacoco/test`.

## Alternative and Corresponding Implementations

This is only one possible solution to this kind of problem.

There are some implementations of single-page applications using the services which are implemented in different
programming languages and frameworks.

Here's the full picture.

## Client-Side Implementations

- [https://github.com/MichaelKaaden/redux-client-ngrx](https://github.com/MichaelKaaden/redux-client-ngrx) (Angular with
  NgRx)
- [https://github.com/MichaelKaaden/redux-client-ng5](https://github.com/MichaelKaaden/redux-client-ng5) (Angular
  with `angular-redux`)
- [https://github.com/MichaelKaaden/redux-client-ng](https://github.com/MichaelKaaden/redux-client-ng) (AngularJS
  with `ng-redux`)

## Server-Side Implementations

- [https://github.com/MichaelKaaden/redux-server-spring](https://github.com/MichaelKaaden/redux-server-spring) (Java
  with Spring Boot)
- [https://github.com/MichaelKaaden/redux-server-rust](https://github.com/MichaelKaaden/redux-server-rust) (Rust
  with `actix-web`)
- [https://github.com/MichaelKaaden/redux-server-golang](https://github.com/MichaelKaaden/redux-server-golang) (Go
  with `Gin`)
- [https://github.com/MichaelKaaden/redux-server-nest](https://github.com/MichaelKaaden/redux-server-nest) (Node.js
  with `Nest.js`)
- [https://github.com/MichaelKaaden/redux-server](https://github.com/MichaelKaaden/redux-server) (Node.js
  with `Express`)
