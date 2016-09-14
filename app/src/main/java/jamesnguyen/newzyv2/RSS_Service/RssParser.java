package jamesnguyen.newzyv2.RSS_Service;

import android.util.Xml;

import junit.framework.Assert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.Utilities.RssItemProcessor;

public class RssParser {
    private final String nameSpace = null; //No name space

    public ArrayList<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException, ParseException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Assert.assertEquals(e.getLocalizedMessage(), "Inputstream closed");
            }
        }
    }

    public ArrayList<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        String channel_title = null;
        String title = null;
        String link = null;
        String pubDate = null;
        String imageURL = null;
        Document description = null;
        Boolean insideItem = false;
        Boolean channelTagAcquired = false;
        int eventType = parser.getEventType();
        ArrayList<RssItem> items = new ArrayList<>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equalsIgnoreCase("item")) {
                    insideItem = true;
                } else if (parser.getName().equalsIgnoreCase("title")) {
                    if (insideItem)
                        title = readTitle(parser); //extract the title of article
                    else {
                        if (!channelTagAcquired) {
                            channel_title = "<< " + readTitle(parser) + " >>"; // extract channel title
                            channelTagAcquired = true;
                        }
                    }
                } else if (parser.getName().equalsIgnoreCase("link")) {
                    if (insideItem)
                        link = readLink(parser); //extract the link of article
                } else if (parser.getName().equalsIgnoreCase("pubDate")) {
                    if (insideItem)
                        pubDate = readPubDate(parser); //extract the link of article
                } else if (parser.getName().equalsIgnoreCase("description")) {
                    if (insideItem)
                        description = readDescription(parser); //extract the description of article
                }
            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                insideItem = false;
            }
            eventType = parser.next();

            if (channel_title != null && title != null && link != null && pubDate != null && description != null) {
                imageURL = RssItemProcessor.getInstance().parseImageURL(description);
                RssItem item = new RssItem(channel_title, title, link, pubDate, description.toString(), imageURL);
                items.add(item);
                title = null;
                link = null;
                pubDate = null;
                description = null;
            }
        }
        channelTagAcquired = false;

        //Limit items received to 5
        for (int i = 6; i < items.size(); i++) {
            items.remove(i);
        }

        return items;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "title");
        return title;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "link");
        return link;
    }

    private String readPubDate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "pubDate");
        String pubDate = readText(parser).substring(0, 25);
        parser.require(XmlPullParser.END_TAG, nameSpace, "pubDate");
        return pubDate;

    }

    private Document readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "description");
        return Jsoup.parseBodyFragment(description);
    }

    private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
