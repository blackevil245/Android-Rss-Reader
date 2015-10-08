package jamesnguyen.newzyv2.Utilities;

import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import jamesnguyen.newzyv2.Application.MyApplication;

public class SettingsManager {

    private static SettingsManager instance = new SettingsManager();

    private static boolean imageLoadAllowed = true;

    private SettingsManager() {
    }

    public static SettingsManager getInstance() {
        return instance;
    }

    public void changeImageLoadPermission() {
        imageLoadAllowed = !imageLoadAllowed;
    }

    public void writeSettingsFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = "Newzy_config";

                try {
                    FileOutputStream fos = MyApplication.getAppContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    DataOutputStream os = new DataOutputStream(fos);
                    os.writeBoolean(imageLoadAllowed);
                    os.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean isImageLoadAllowed() {
        return imageLoadAllowed;
    }

    public void readSettingsFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filename = "Newzy_config";

                try {
                    FileInputStream fis = MyApplication.getAppContext().openFileInput(filename);
                    DataInputStream is = new DataInputStream(fis);
                    imageLoadAllowed = is.readBoolean();
                    is.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
