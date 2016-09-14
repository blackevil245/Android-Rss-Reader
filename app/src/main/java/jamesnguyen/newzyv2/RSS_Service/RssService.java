package jamesnguyen.newzyv2.RSS_Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import jamesnguyen.newzyv2.Model.ItemCache;
import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.Utilities.ConnectionManager;

public class RssService extends IntentService {

    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ConnectionManager.getInstance().isNetworkAvailable()) {
            ArrayList<ArrayList<RssItem>> rssItems = new ArrayList<>();
            try {
                RssParser parser = new RssParser();
                for (String link : SubscriptionManager.getInstance().getAll()) {
                    rssItems.add(parser.parse(getInputStream(link)));
                }

                writeStorageFile(rssItems);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ITEMS, rssItems);
                ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
                receiver.send(0, bundle);
            } catch (IOException | XmlPullParserException | ParseException e) {
                Log.w(e.getMessage(), e);
            }
        } else {
            readStorageFile();

        }
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

    public void writeStorageFile(final ArrayList<ArrayList<RssItem>> items) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = "NewzyData.ser";

                try {
                    FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    ObjectOutputStream out = new ObjectOutputStream(fos);
                    out.writeObject(items);
                    out.flush();
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressWarnings("unchecked")
    public void readStorageFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = "NewzyData.ser";
                try {
                    FileInputStream fis = openFileInput(filename);
                    ObjectInputStream in = new ObjectInputStream(fis);
                    ItemCache.getInstance().setTempCache((ArrayList<ArrayList<RssItem>>) in.readObject());
                    in.close();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
