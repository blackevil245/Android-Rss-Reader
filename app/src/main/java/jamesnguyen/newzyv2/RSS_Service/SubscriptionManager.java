package jamesnguyen.newzyv2.RSS_Service;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import java.util.ArrayList;

import jamesnguyen.newzyv2.Activity.Main;
import jamesnguyen.newzyv2.Model.ItemCache;
import jamesnguyen.newzyv2.Model.RssItem;

public class SubscriptionManager {

    private static SubscriptionManager instance = new SubscriptionManager();
    private static ArrayList<String> RSS_LIST = new ArrayList<>();
    private static ArrayList<String> TITLE = new ArrayList<>();
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            ArrayList<ArrayList<RssItem>> receivedPackage = (ArrayList<ArrayList<RssItem>>) resultData.getSerializable(RssService.ITEMS);
            ItemCache.getInstance().setTempCache(receivedPackage);
        }
    };

    private SubscriptionManager() {

    }

    public static SubscriptionManager getInstance() {
        return instance;
    }

    public ArrayList<String> getAll() {
        return RSS_LIST;
    }

    public ArrayList<String> getLink(int position) {
        ArrayList<String> link = new ArrayList<>();
        link.add(RSS_LIST.get(position));
        return link;
    }

    public String getTitle(int position) {
        return TITLE.get(position);
    }

    public void initList() {

        /* Business and Finance */
        addLink("http://www.economist.com/sections/business-finance/rss.xml");

        /* Startup and Funding */
        addLink("http://feeds.feedburner.com/techcrunch/startups?format=xml");
        addLink("http://feeds.feedburner.com/techcrunch/fundings-exits?format=xml");

        /* Tech */
        addLink("http://www.wired.com/category/gear/feed/");
        addLink("http://feeds.feedburner.com/crunchgear");
        addLink("http://www.wired.com/category/reviews/feed/");
        addLink("http://feeds.feedburner.com/TechCrunch/Google");
        /* Science */
        addLink("http://www.wired.com/category/science/feed/");
        addLink("http://www.wired.com/category/science/science-blogs/feed/");
        addLink("http://www.sciencemag.org/rss/twis.xml");
        /* Entertainment */
        addLink("http://www.wired.com/category/design/feed/");
        addLink("http://www.wired.com/category/underwire/feed/");
        addLink("http://feeds.feedburner.com/thr/news");
        addLink("http://www.fandango.com/rss/movie-news.rss");
        /* Game */
        addLink("http://www.ongamers.com/feeds/mashup/");
        addLink("http://feeds.feedburner.com/TechCrunch/gaming");


        TITLE.add("Home");
        TITLE.add("Business and Finance");
        TITLE.add("Startup and Funding");
        TITLE.add("Tech");
        TITLE.add("Science");
        TITLE.add("Entertainment");
        TITLE.add("Gaming");
    }

    public void addLink(String link) {
        RSS_LIST.add(link);
    }

    public void startService() {
        final ProgressDialog loadDialog = ProgressDialog.show(Main.mainActivity, "Please wait ...",
                "Loading contents", true);
        loadDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(Main.mainActivity, RssService.class);
                    intent.putExtra(RssService.RECEIVER, resultReceiver);
                    Main.mainActivity.startService(intent);
                    Thread.sleep(10000);
                } catch (Exception ignored) {
                }
                Main.mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadDialog.dismiss();
                    }
                });
            }
        }).start();
    }
}
