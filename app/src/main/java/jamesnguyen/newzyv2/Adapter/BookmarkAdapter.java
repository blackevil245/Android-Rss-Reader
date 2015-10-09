package jamesnguyen.newzyv2.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jamesnguyen.newzyv2.Activity.Main;
import jamesnguyen.newzyv2.Model.BookmarkManager;
import jamesnguyen.newzyv2.Model.RssItem;
import jamesnguyen.newzyv2.Model.SettingsManager;
import jamesnguyen.newzyv2.R;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkHolder> {

    private static ArrayList<RssItem> items = new ArrayList<>();
    private static Context context;
    private final BookmarkAdapter thisAdapter = this;

    public BookmarkAdapter(Context context, ArrayList<RssItem> items) {
        BookmarkAdapter.items = items;
        BookmarkAdapter.context = context;
    }

    @Override
    public BookmarkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item, parent, false);
        return new BookmarkHolder(v);
    }

    @Override
    public void onBindViewHolder(BookmarkHolder holder, int position) {
        holder.getChannel_title().setText(items.get(position).getChannelTitle()); // SET CHANNEL ITEM
        holder.getTitle().setText(items.get(position).getTitle()); // TITLE
        holder.getPubDate().setText(items.get(position).getPubDate()); //PUBDATE
        holder.setOnClickLink(items.get(position).getLink()); // ON CLICK LINK
        holder.setDeleteBookmarkButton(items.get(position)); // ON CLICK DELETE

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
    public int getItemCount() {
        return items.size();
    }

    public class BookmarkHolder extends RecyclerView.ViewHolder {

        private TextView channel_title;
        private TextView title;
        private TextView pubDate;
        private ImageView imageHolder;
        private View v;
        private Button delete_bookmark;

        public BookmarkHolder(View itemView) {
            super(itemView);
            v = itemView;
            channel_title = (TextView) itemView.findViewById(R.id.channel_title_bookmark);
            title = (TextView) itemView.findViewById(R.id.item_title_bookmark);
            pubDate = (TextView) itemView.findViewById(R.id.item_pubDate_bookmark);
            imageHolder = (ImageView) itemView.findViewById(R.id.img_thumbnail_bookmark);
            delete_bookmark = (Button) itemView.findViewById(R.id.delete_bookmark);
        }

        public void setDeleteBookmarkButton(final RssItem item) {
            delete_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(Main.mainActivity)
                            .setMessage("Are you sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BookmarkManager.getInstance().removeBookmark(item);
                                    thisAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
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


        public void setOnClickLink(final String link) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(browserIntent);
                }
            });
        }
    }
}
