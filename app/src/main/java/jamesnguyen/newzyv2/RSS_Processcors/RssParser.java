package jamesnguyen.newzyv2.RSS_Processcors;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RssParser {
    private final String nameSpace = null; //No name space

    public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }

    public List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "rss");
        String title = null;
        String link = null;
        String pubDate = null;
        String description = null;
        Boolean insideItem = false;
        List<RssItem> items = new ArrayList<RssItem>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equalsIgnoreCase("title")) {
                title = readTitle(parser);
            } else if (name.equalsIgnoreCase("link")) {
                link = readLink(parser);
            } else if (name.equalsIgnoreCase("pubDate")) {
                pubDate = readPubDate(parser);
            } else if (name.equalsIgnoreCase("description")) {
                description = readDescription(parser);
            }

            if (title != null && link != null && pubDate != null && description != null) {
                RssItem item = new RssItem(title, link, pubDate, description);
                items.add(item);
                title = null;
                link = null;
                pubDate = null;
                description = null;
            }
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
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "pubDate");
        return pubDate;
    }

    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, "description");
        return description;
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
