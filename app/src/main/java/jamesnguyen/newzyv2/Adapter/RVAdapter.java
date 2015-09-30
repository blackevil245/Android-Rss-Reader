package jamesnguyen.newzyv2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.R;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FeedViewHolder> {

    private static List<RssItem> items = new ArrayList<>();
    private static Context context;

    public RVAdapter(Context context, List<RssItem> items) {
        RVAdapter.items = items;
        RVAdapter.context = context;
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
        holder.getChannel_title().setText(items.get(position).getChannelTitle()); // SET CHANNEL ITEM
        holder.getTitle().setText(items.get(position).getTitle()); // TITLE
        holder.getPubDate().setText(items.get(position).getPubDate()); //PUBDATE
        holder.setOnClickLink(items.get(position).getLink()); // ONCICK LINK

        try { //IMAGE LOADING
            Picasso.with(context).load(items.get(position).getImageURL()).error(context.getResources().getColor(android.R.color.white)).fit().centerCrop().into(holder.getImageHolder());
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
        private TextView channel_title;
        private TextView title;
        private TextView pubDate;
        private ImageView imageHolder;
        private View v;

        FeedViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            channel_title = (TextView) itemView.findViewById(R.id.channel_title);
            title = (TextView) itemView.findViewById(R.id.item_title);
            pubDate = (TextView) itemView.findViewById(R.id.item_pubDate);
            imageHolder = (ImageView) itemView.findViewById(R.id.img_thumbnail);
        }

        public void setOnClickLink(final String link) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(browserIntent);
                }
            });
        }

        public void setOnLongClick() {
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }

        public View getView() {
            return v;
        }

        public TextView getChannel_title() {
            return channel_title;
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
