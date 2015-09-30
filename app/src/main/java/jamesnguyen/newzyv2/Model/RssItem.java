package jamesnguyen.newzyv2.Model;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;


public class RssItem {

    Document description;
    private String channel_title, title, link, pubDate;

    public RssItem(String channel_title, String title, String link, String pubDate, Document description) {
        this.channel_title = channel_title;
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
    }

    public String getChannelTitle() {
        return channel_title;
    }

    public void setChannelTitle(String channel_title) {
        this.channel_title = channel_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Document getDescription() {
        return description;
    }

    public void setDescription(Document description) {
        this.description = description;
    }

    public String getImageURL() {
        Elements img = description.getElementsByTag("img");
        String link = img.attr("src");
        URL _link = null;
        try {
            _link = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return _link.toString();
    }
}
