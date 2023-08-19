# Installation

<!-- TOC -->
* [Installation](#installation)
  * [Clone](#clone)
  * [Run with Docker](#run-with-docker)
  * [Run locally](#run-locally)
    * [Create database](#create-database)
    * [Set up connection to database](#set-up-connection-to-database)
    * [Build and run on the command line](#build-and-run-on-the-command-line)
    * [Run in IntelliJ IDEA](#run-in-intellij-idea)
  * [Use](#use)
<!-- TOC -->

## Clone

    git clone https://github.com/maxlutovinov/web-scraping-console.git

> **Note:**  
> All the following terminal/cmd commands are executed from the root directory of the project.

## Run with Docker

Install Docker first. After that, pull the application image and then run it:

    docker pull maxlutovinov/web-scraping-console

    docker compose run --rm app

## Run locally

### Create database

* Install PostgreSQL
* Create a database with the name you specify below in `spring.datasource.url` property

### Set up connection to database

Change the database properties to yours in [application.properties](src/main/resources/application.properties) file.<br>
Example for PostgreSQL database:

    spring.datasource.url=jdbc:postgresql://localhost:5432/jobs_web_scraping
    spring.datasource.username=postgres
    spring.datasource.password=12345678
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

### Build and run on the command line

    ./mvnw package -DskipTests

If this fails, install Maven and execute the following build command:

    mvn package -DskipTests

Then run the jar:

    java -jar target/web-scraping-console-0.0.1-SNAPSHOT.jar

### Run in IntelliJ IDEA

Open the project in IDEA and run WebScrapingConsoleApplication class.

## Use
After launching the application, follow the instructions in the console.
