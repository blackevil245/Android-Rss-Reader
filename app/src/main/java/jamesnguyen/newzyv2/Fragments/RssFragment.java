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
    private ArrayList<Integer> requestID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestID = this.getArguments().getIntegerArrayList(ID);
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.mView = inflater.inflate(R.layout.rss_fragment, container, false);

        // SETUP RECYCLER VIEW
        RecyclerView rv = (RecyclerView) mView.findViewById(R.id.feed);
        LinearLayoutManager rvManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(rvManager);

        ArrayList<RssItem> data = loadData();
        RVAdapter adapter = new RVAdapter(getActivity(), data);
        rv.setAdapter(adapter);

        //RETURN
        return mView;
    }

    private ArrayList<RssItem> loadData() {
        ArrayList<RssItem> appendinglist = ItemCache.getInstance().getItems(this.requestID);
        Collections.sort(appendinglist);
        Collections.reverse(appendinglist);
        return appendinglist;
    }
}
