package jamesnguyen.newzyv2.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class RssItem implements Serializable, Comparable<RssItem> {

    Date formattedDate;
    private String channel_title, title, link, pubDate, description, imageURL;

    public RssItem(String channel_title, String title, String link,
                   String pubDate, String description, String imageURL) throws ParseException {
        this.channel_title = channel_title;
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        formattedDate = changePubdateFormat(this.pubDate);
        this.description = description;
        this.imageURL = imageURL;
    }

    public Date changePubdateFormat(String pubDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        return formatter.parse(this.pubDate);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    @Override
    public int compareTo(@NonNull RssItem another) {
        return getDateTime().compareTo(another.getDateTime());
    }

    public Date getDateTime() {
        return formattedDate;
    }
}
