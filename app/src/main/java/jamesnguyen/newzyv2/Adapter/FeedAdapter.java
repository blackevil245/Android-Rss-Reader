package jamesnguyen.newzyv2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jamesnguyen.newzyv2.Activity.Main;
import jamesnguyen.newzyv2.Model.BookmarkManager;
import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.Model.SettingsManager;
import jamesnguyen.newzyv2.R;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private static ArrayList<RssItem> items = new ArrayList<>();
    private static Context context;

    public FeedAdapter(Context context, ArrayList<RssItem> items) {
        FeedAdapter.items = items;
        FeedAdapter.context = context;
    }

    // CREATE NEW CARDVIEW
    @Override
    public FeedAdapter.FeedViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item, parent, false);
        return new FeedViewHolder(v);
    }

    // REPLACE DATA ON NEW CARDVIEW
    @Override
    public void onBindViewHolder(FeedAdapter.FeedViewHolder holder, final int position) {
        holder.getChannel_title().setText(items.get(position).getChannelTitle()); // SET CHANNEL ITEM
        holder.getTitle().setText(items.get(position).getTitle()); // TITLE
        holder.getPubDate().setText(items.get(position).getPubDate()); //PUBDATE
        holder.setOnClickLink(items.get(position).getLink()); // ONCICK LINK
        holder.setOnBookmarkClick(items.get(position)); // SET BOOKMARK BUTTON
        holder.setBookmarkButtonState(BookmarkManager.getInstance().containsLink(items.get(position).getLink()));
        try { //IMAGE LOADING
            if (SettingsManager.getInstance().isImageLoadAllowed()) {
                Picasso.with(context).load(items.get(position).getImageURL())
                        .error(context.getResources().getColor(android.R.color.white))
                        .fit().centerCrop()
                        .into(holder.getImageHolder());
            }
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
        private Button bookmark;

        FeedViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            channel_title = (TextView) itemView.findViewById(R.id.channel_title);
            title = (TextView) itemView.findViewById(R.id.item_title);
            pubDate = (TextView) itemView.findViewById(R.id.item_pubDate);
            imageHolder = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            bookmark = (Button) itemView.findViewById(R.id.bookmark);
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

        public void setOnBookmarkClick(final RssItem item) {
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!BookmarkManager.getInstance().getList().contains(item)) {
                        BookmarkManager.getInstance().addBookmark(item);
                        Main.mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Main.mainActivity, "Bookmark added", Toast.LENGTH_SHORT).show();
                                bookmark.setBackgroundResource(R.drawable.ic_star_yellow_700_24dp);
                            }
                        });
                    } else {
                        BookmarkManager.getInstance().removeBookmark(item);
                        Main.mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Main.mainActivity, "Bookmark removed", Toast.LENGTH_SHORT).show();
                                bookmark.setBackgroundResource(R.drawable.ic_star_white_24dp);
                            }
                        });
                    }
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


        public void setBookmarkButtonState(final boolean bookmarkExists) {

            Main.mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bookmarkExists) {
                        bookmark.setBackgroundResource(R.drawable.ic_star_yellow_700_24dp);
                        Log.w(null, "success");
                    } else {
                        bookmark.setBackgroundResource(R.drawable.ic_star_white_24dp);
                        Log.w(null, "fail");
                    }
                }
            });

        }
    }
}
