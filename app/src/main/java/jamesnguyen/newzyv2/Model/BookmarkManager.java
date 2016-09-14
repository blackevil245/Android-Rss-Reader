package jamesnguyen.newzyv2.Model;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import jamesnguyen.newzyv2.Application.MyApplication;

public class BookmarkManager {

    private static ArrayList<RssItem> list = new ArrayList<>();

    private static BookmarkManager instance = new BookmarkManager();

    private BookmarkManager() {
    }

    public static BookmarkManager getInstance() {
        return instance;
    }

    public ArrayList<RssItem> getList() {
        return list;
    }

    public void addBookmark(RssItem item) {
        list.add(item);
        Collections.sort(list);
        Collections.reverse(list);
    }

    public void removeBookmark(RssItem item) {
        list.remove(item);
        Collections.sort(list);
        Collections.reverse(list);
    }

    public boolean containsLink(String link) {
        for (RssItem a : list) {
            if (a.getLink().contains(link)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void loadBookmarks() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = "Newzy_bookmarks";

                try {
                    FileInputStream fis = MyApplication.getAppContext().openFileInput(filename);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    list = (ArrayList<RssItem>) is.readObject();
                    Collections.sort(list);
                    Collections.reverse(list);
                    is.close();
                    fis.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void writeBookmarks() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = "Newzy_bookmarks";

                try {
                    FileOutputStream fos = MyApplication.getAppContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(list);
                    os.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
