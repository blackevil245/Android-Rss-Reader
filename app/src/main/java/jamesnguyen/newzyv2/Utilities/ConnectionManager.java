package jamesnguyen.newzyv2.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import jamesnguyen.newzyv2.Application.MyApplication;

public class ConnectionManager {

    public static ConnectionManager instance = new ConnectionManager();

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        return instance;
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
