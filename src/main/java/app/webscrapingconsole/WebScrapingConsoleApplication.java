package app.webscrapingconsole;

import app.webscrapingconsole.service.ConsoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebScrapingConsoleApplication implements CommandLineRunner {
    private final ConsoleService consoleService;

    public WebScrapingConsoleApplication(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebScrapingConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        consoleService.run();
    }
}
