package jamesnguyen.newzyv2.RSS_Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.Model.SubscriptionManager;

public class RssService extends IntentService {

    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<ArrayList<RssItem>> rssItems = new ArrayList<>();
        RssParser parser = new RssParser();
        for (String link : SubscriptionManager.getInstance().getAll()) {
            ArrayList<RssItem> single_link = null;
            try {
                single_link = parser.parse(getInputStream(link));
            } catch (XmlPullParserException | IOException | ParseException e) {
                e.printStackTrace();
            }
            rssItems.add(single_link);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, rssItems);
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
