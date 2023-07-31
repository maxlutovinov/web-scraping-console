# Installation

### Clone

    git clone https://github.com/maxlutovinov/web-scraping-console.git

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

### Run
* Open and run in IDEA
* Follow the instructions in the console
