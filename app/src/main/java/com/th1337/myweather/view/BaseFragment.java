package com.th1337.myweather.view;

import android.support.v4.app.Fragment;

/**
 * Created by a607937 on 10/06/2015.
 */
public class BaseFragment extends Fragment {

    public boolean isValid() {
        return !isDetached() && getActivity() != null && getView() != null;
    }

}
