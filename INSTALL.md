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

### Build 

From the root directory of the project:

    ./mvnw package -DskipTests

If this fails, install Maven and run the following command:

    mvn package -DskipTests

### Run 

#### On Command line:

From the root directory of the project:

    java -jar target/web-scraping-console-0.0.1-SNAPSHOT.jar

#### In IntelliJ IDEA

Open the project in IDEA and run WebScrapingConsoleApplication class.

### Use
Follow the instructions in the console.
