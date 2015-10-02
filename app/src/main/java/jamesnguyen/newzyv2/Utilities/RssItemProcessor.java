package jamesnguyen.newzyv2.Utilities;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;

public class RssItemProcessor {

    private static RssItemProcessor instance = new RssItemProcessor();

    private RssItemProcessor() {
    }

    public static RssItemProcessor getInstance() {
        return instance;
    }

    public String parseImageURL(Document description) {
        try {
            Elements img = description.getElementsByTag("img");
            String link = img.attr("src");
            URL _link;
            _link = new URL(link);
            return _link.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
