package app.webscrapingconsole.util;

import app.webscrapingconsole.exception.DataProcessingException;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupDocumentUntil {
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) "
            + "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.5.2 Safari/605.1.15";

    private JsoupDocumentUntil() {
    }

    public static Document getDocument(String url) {
        Document document;
        try {
            document = Jsoup
                    .connect(url)
                    .ignoreHttpErrors(true)
                    .timeout(0)
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
            throw new DataProcessingException("Can't scrape url: " + url, e);
        }
        return document;
    }
}
