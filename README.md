# A demo application for using Micronaut and GraalVM

This example server application implements a fictional electronic component catalog
for showcasing various technologies provided by the Micronaut framework

See source-level documentation for details - many classes are "trivial" but contain
detailed explanations what's going on

## Features

### Overall application design

* Basic examples of using Micronaut DI and application configuration
* Typical usage of Micronaut's `application.yml` for configuring various system parameters,
  including Jackson configuration, HTTP server setup etc.
* Implementation of a custom bean (for the startup ascii art banner) that integrates with 
  the application context lifecycle  
* Example of replacing an existing bean with a different one

###  HTTP Server / REST API / Server-side views

* Based on Micronaut's default Netty-based HTTP server implementation
* Several annotated controller classes show examples of Micronaut controller annotations
* Exposes a REST API for operating on individual components
* Uses `micronaut-views` Thymeleaf integration for providing a simple browsing interface using
Thymeleaf templates for server-side rendering
* Uses a message source for exposing a message bundle to the view templates
* Uses Micronaut's static resource provider to incorporate `Bootstrap` CSS webjars for page styling
* Also exposes some of Micronaut's management endpoints via configuration in `application.yml`

### Build and containerization

* A typical Gradle build configuration for Micronaut specifics

## GraalVM integration and native image build

* Integrates Micronaut's GraalVM-specific annotation processor for creating configuration
  files for the GraalVM `native-image` toolchain