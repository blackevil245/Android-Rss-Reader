package jamesnguyen.newzyv2.RSS_Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jamesnguyen.newzyv2.Model.RssItem;

public class RssService extends IntentService {

    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    public static final String LINK = "link";
    public static final String SHOW_ALL = "all";

    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<List<RssItem>> rssItems = new ArrayList<>();
        try {
            RssParser parser = new RssParser();
            if (intent.getBooleanExtra(SHOW_ALL, false)) {
                for (String link : intent.getStringArrayListExtra(LINK)) {
                    List<RssItem> single_link = parser.parse(getInputStream(link));
                    rssItems.add(single_link);
                }
            } else {
                List<RssItem> single_link = parser.parse(getInputStream(intent.getStringArrayListExtra(LINK).get(0)));
                rssItems.add(single_link);
            }


        } catch (IOException | XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w("RssApp", "Exception while retrieving the input stream", e);
            return null;
        }
    }
}
