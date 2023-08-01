package app.webscrapingconsole.service.impl;

import app.webscrapingconsole.exception.DataProcessingException;
import app.webscrapingconsole.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

@Service
public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeFile(String report, String filePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(report);
        } catch (IOException e) {
            throw new DataProcessingException("Can't write data to file: " + filePath, e);
        }
    }
}
