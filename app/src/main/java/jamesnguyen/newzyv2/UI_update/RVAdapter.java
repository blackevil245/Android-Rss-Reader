package jamesnguyen.newzyv2.UI_update;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jamesnguyen.newzyv2.R;
import jamesnguyen.newzyv2.RSS_Processcors.RssItem;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FeedViewHolder> {

    private static List<RssItem> items = null;
    private static Context context;

    public RVAdapter(Context context, List<RssItem> items) {
        this.items = items;
        this.context = context;
    }

    public static List<RssItem> getItems() {
        return items;
    }

    @Override
    public RVAdapter.FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new FeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVAdapter.FeedViewHolder holder, int position) {
        holder.getTitle().setText(items.get(position).getTitle());
        holder.getPubDate().setText(items.get(position).getPubDate());

        //FeedViewHolder.getDescription().setText(items.get(position).getDescription());
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView pubDate;
//        public static TextView description;

        FeedViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
//            description = (TextView) itemView.findViewById(R.id.description);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getPubDate() {
            return pubDate;
        }

//        public static TextView getDescription() {
//            return description;
//        }
    }
}
