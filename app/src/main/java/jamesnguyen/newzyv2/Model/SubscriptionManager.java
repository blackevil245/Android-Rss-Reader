package jamesnguyen.newzyv2.Model;

import java.util.ArrayList;

public class SubscriptionManager {

    private static ArrayList<String> RSS_LIST = new ArrayList<>();

    private static SubscriptionManager instance = new SubscriptionManager();

    private SubscriptionManager() {

    }

    public static SubscriptionManager getInstance() {
        return SubscriptionManager.instance;
    }

    public static ArrayList<String> getAll() {
        return RSS_LIST;
    }

    public ArrayList<String> getLink(int position) {
        ArrayList<String> link = new ArrayList<>();
        link.add(RSS_LIST.get(position));
        return link;
    }

    public void initList() {
        this.addLink("http://www.wired.com/category/gear/feed/");
        this.addLink("http://www.wired.com/category/science/feed/");
        this.addLink("http://www.wired.com/category/design/feed/");
        this.addLink("http://www.pcworld.com/index.rss");
        this.addLink("http://blog.dota2.com/feed/");
        this.addLink("http://blog.counter-strike.net/index.php/feed/");
        this.addLink("http://www.ongamers.com/feeds/mashup/");

    }

    public void addLink(String link) {
        SubscriptionManager.RSS_LIST.add(link);
    }
}
