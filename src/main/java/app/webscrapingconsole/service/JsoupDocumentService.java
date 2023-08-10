package app.webscrapingconsole.service;

import app.webscrapingconsole.exception.DataProcessingException;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JsoupDocumentService {
    @Value("${app.user-agent}")
    private String userAgent;

    public Document getDocument(String url) {
        Document document;
        try {
            document = Jsoup
                    .connect(url)
                    .ignoreHttpErrors(true)
                    .timeout(0)
                    .userAgent(userAgent)
                    .get();
        } catch (IOException e) {
            throw new DataProcessingException("Can't scrape url: " + url, e);
        }
        return document;
    }
}
