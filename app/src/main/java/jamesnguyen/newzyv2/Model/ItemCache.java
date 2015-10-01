package jamesnguyen.newzyv2.Model;

import java.util.ArrayList;

public class ItemCache {

    public static int _ALL_LINK = 99;
    public static int _1_LINK = 0;
    public static int _2_LINK = 1;
    public static int _3_LINK = 2;
    public static int _4_LINK = 3;
    public static int _5_LINK = 4;
    public static int _6_LINK = 5;
    public static int _7_LINK = 6;
    private static ItemCache instance = new ItemCache();
    private ArrayList<ArrayList<RssItem>> tempCache = new ArrayList<>();

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

    public ArrayList<RssItem> getItems(int requestID) {
        if (requestID == _ALL_LINK) {
            ArrayList<RssItem> list = new ArrayList<>();
            for (ArrayList<RssItem> a : tempCache) {
                for (RssItem b : a) {
                    list.add(b);
                }
            }
            return list;
        } else {
            return instance.tempCache.get(requestID);
        }
    }
}
