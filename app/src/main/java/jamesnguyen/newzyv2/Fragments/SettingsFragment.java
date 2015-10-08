package jamesnguyen.newzyv2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import jamesnguyen.newzyv2.R;
import jamesnguyen.newzyv2.Utilities.SettingsManager;

public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ToggleButton load_image_toggle = (ToggleButton) getActivity().findViewById(R.id.load_image_toggle);
        load_image_toggle.setTextOn("On");
        load_image_toggle.setTextOff("Off");
        load_image_toggle.setChecked(SettingsManager.getInstance().isImageLoadAllowed());
        load_image_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsManager.getInstance().changeImageLoadPermission();
                SettingsManager.getInstance().writeSettingsFile();
            }
        });
    }
}
