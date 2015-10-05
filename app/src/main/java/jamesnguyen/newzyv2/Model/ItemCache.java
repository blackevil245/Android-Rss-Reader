package jamesnguyen.newzyv2.Model;

import android.app.Application;

import java.util.ArrayList;
import java.util.Collections;

public class ItemCache extends Application {

    private static ItemCache instance = new ItemCache();
    private static ArrayList<ArrayList<RssItem>> tempCache = new ArrayList<>();

    private ItemCache() {
    }

    public static ItemCache getInstance() {
        return instance;
    }

    public ArrayList<ArrayList<RssItem>> getTempCache() {
        return tempCache;
    }

    public void setTempCache(ArrayList<ArrayList<RssItem>> cache) {
        tempCache = cache;
    }

    public ArrayList<RssItem> getItems(ArrayList<Integer> requestID) {
        ArrayList<RssItem> list = new ArrayList<>();

        for (int a : requestID) {
            ArrayList<RssItem> b = tempCache.get(a);
            for (RssItem c : b) {
                list.add(c);
            }
        }

        Collections.sort(list);
        Collections.reverse(list);

        return list;
    }
}
