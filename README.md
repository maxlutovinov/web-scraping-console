# Web Scraping Console

<!-- TOC -->
* [Web Scraping Console](#web-scraping-console)
  * [Description](#description)
  * [Structure](#structure)
  * [Technology stack](#technology-stack)
  * [Usage](#usage)
<!-- TOC -->

## Description

This is a Java console application that web scrapes public information from a job search site by job function 
(category), stores it in a local database, and writes to [JSON file](scraping_result.json).

## Structure

The application has an N-tier architecture:

- [Service](src/main/java/app/webscrapingconsole/service) layer is responsible for the business logic 
of the application on request from the console.
- [Repository](src/main/java/app/webscrapingconsole/repository) layer works with the database 
on request from service.

The project operates with entities which are represented in the [model](src/main/java/app/webscrapingconsole/model) 
package.

## Technology stack

Java 17, Hibernate, PostgeSQL, Spring Boot, Spring Data JPA, Maven, Jsoup, JSON, Lombok, Liquibase, Docker.

## Usage

[INSTALL.md](INSTALL.md)
