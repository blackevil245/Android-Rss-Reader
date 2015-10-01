package jamesnguyen.newzyv2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import jamesnguyen.newzyv2.Adapter.RVAdapter;
import jamesnguyen.newzyv2.Model.ItemCache;
import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.R;

public class RssFragment extends Fragment {
    public final static String ID = "id";

    protected View mView;
    private RecyclerView rv;
    private int requestID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestID = this.getArguments().getInt(ID);
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.rss_fragment, container, false);

        // SETUP RECYCLER VIEW
        rv = (RecyclerView) mView.findViewById(R.id.feed);
        LinearLayoutManager rvManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(rvManager);

        // GET DATA FOR FRAGMENT
        loadData();

        //RETURN
        return mView;
    }

    private void loadData() {
        ArrayList<RssItem> appendinglist = ItemCache.getInstance().getItems(this.requestID);
        Collections.sort(appendinglist);
        Collections.reverse(appendinglist);
        RVAdapter adapter = new RVAdapter(getActivity(), appendinglist);
        rv.setAdapter(adapter);
    }
}
