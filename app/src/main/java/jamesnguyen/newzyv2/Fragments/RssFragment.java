package jamesnguyen.newzyv2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jamesnguyen.newzyv2.Adapter.RVAdapter;
import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.R;
import jamesnguyen.newzyv2.RSS_Service.RssService;

public class RssFragment extends Fragment {
    public final static String LINK = "link";

    protected View mView;
    private RecyclerView rv;
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
            RVAdapter adapter = new RVAdapter(getActivity(), items);
            rv.setAdapter(adapter);
        }
    };

    private String fragment_link;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        fragment_link = this.getArguments().getString(LINK);
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ////// START RSS SERVICE ////////
        startService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.rss_fragment, container, false);

        /////// SETUP RECYCLER VIEW /////
        rv = (RecyclerView) mView.findViewById(R.id.feed);
        LinearLayoutManager rvManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(rvManager);
        return mView;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        intent.putExtra(RssService.LINK, fragment_link);
        getActivity().startService(intent);
    }
}
