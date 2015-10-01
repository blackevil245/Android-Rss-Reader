package jamesnguyen.newzyv2.Model;

import java.util.ArrayList;

public class SubscriptionManager {

    private static SubscriptionManager instance = new SubscriptionManager();
    private static ArrayList<String> RSS_LIST = new ArrayList<>();

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

    public void initList() {
        addLink("http://www.wired.com/category/gear/feed/");
        addLink("http://www.wired.com/category/science/feed/");
        addLink("http://www.wired.com/category/design/feed/");
        addLink("http://www.pcworld.com/index.rss");
        addLink("http://blog.dota2.com/feed/");
        addLink("http://blog.counter-strike.net/index.php/feed/");
        addLink("http://www.ongamers.com/feeds/mashup/");
    }

    public void addLink(String link) {
        RSS_LIST.add(link);
    }
}
