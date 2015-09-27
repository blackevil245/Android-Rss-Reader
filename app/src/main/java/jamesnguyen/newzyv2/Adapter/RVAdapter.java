package jamesnguyen.newzyv2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.R;


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

    // CREATE NEW CARDVIEW
    @Override
    public RVAdapter.FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new FeedViewHolder(v);
    }

    // REPLACE DATA ON NEW CARDVIEW
    @Override
    public void onBindViewHolder(RVAdapter.FeedViewHolder holder, final int position) {
        holder.getTitle().setText(items.get(position).getTitle()); // TITLE
        holder.getPubDate().setText(items.get(position).getPubDate()); //PUBDATE


//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(items.get(position).getLink()));
//        context.startActivity(browserIntent);

        try { //IMAGE LOADING
            Picasso.with(context).load(items.get(position).getImageURL()).fit().centerCrop().into(holder.getImageHolder());
        } catch (Exception e) {
            Log.w(e.toString(), "Cant get to image URL");
        }
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
        private ImageView imageHolder;

        FeedViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            pubDate = (TextView) itemView.findViewById(R.id.item_pubDate);
            imageHolder = (ImageView) itemView.findViewById(R.id.img_thumbnail);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getPubDate() {
            return pubDate;
        }

        public ImageView getImageHolder() {
            return imageHolder;
        }
    }
}
