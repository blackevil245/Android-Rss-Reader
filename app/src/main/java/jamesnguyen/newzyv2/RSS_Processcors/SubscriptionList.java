package jamesnguyen.newzyv2.RSS_Processcors;

import java.util.ArrayList;

public class SubscriptionList {  ///////  UNDER BUILDING
    private static ArrayList<String> RSS_LINK = new ArrayList<>();

    private SubscriptionList list = new SubscriptionList();

    private SubscriptionList() {
    }

    private SubscriptionList getInstance() {
        return list;
    }

    private void addSource(String source) {
        RSS_LINK.add(source);
    }

    private String getSource(int position) {
        return RSS_LINK.get(position);
    }
}
