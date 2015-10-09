package jamesnguyen.newzyv2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jamesnguyen.newzyv2.Adapter.BookmarkAdapter;
import jamesnguyen.newzyv2.Model.BookmarkManager;
import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.R;

public class BookmarkFragment extends Fragment {

    protected View mView;
    private BookmarkAdapter adapter;

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.mView = inflater.inflate(R.layout.bookmark_fragment, container, false);

        // SETUP RECYCLER VIEW
        RecyclerView rv = (RecyclerView) mView.findViewById(R.id.bookmark_recycler_view);
        LinearLayoutManager rvManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(rvManager);

        ArrayList<RssItem> data = BookmarkManager.getInstance().getList();
        adapter = new BookmarkAdapter(getActivity(), data);
        rv.setAdapter(adapter);

        //RETURN
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        BookmarkManager.getInstance().writeBookmarks();
    }

}
