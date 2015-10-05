package jamesnguyen.newzyv2.Model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import java.util.ArrayList;

import jamesnguyen.newzyv2.Activity.Main;
import jamesnguyen.newzyv2.RSS_Service.RssService;

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
        /* Request ID = 0 */
        addLink("http://www.wired.com/category/gear/feed/");
        /* Request ID = 1 */
        addLink("http://www.wired.com/category/science/feed/");
        /* Request ID = 2 */
        addLink("http://www.wired.com/category/design/feed/");
        /* Request ID = 3 */
        addLink("http://blog.dota2.com/feed/");
        /* Request ID = 4 */
        addLink("http://blog.counter-strike.net/index.php/feed/");
        /* Request ID = 5 */
        addLink("http://www.ongamers.com/feeds/mashup/");

        TITLE.add("All Newzy");
        TITLE.add("Tech");
        TITLE.add("Science");
        TITLE.add("Design");
        TITLE.add("Dota 2");
        TITLE.add("CS:GO");
        TITLE.add("OnGamers");
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
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
                Main.mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadDialog.dismiss();
                        // DRAWER PREVIEW
                        Main.getDrawerLayout().openDrawer(Main.getDrawerList());
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Main.getDrawerLayout().closeDrawer(Main.getDrawerList());
                            }
                        }, 3000);
                    }
                });
            }
        }).start();
    }
}
