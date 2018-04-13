package com.newth.scorehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newth.scorehelper.R;



/**
 * Created by Mr.chen on 2018/3/18.
 */

public class MeFragment extends Fragment {
    public static MeFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("id", "MeFragment");
        MeFragment fragment = new MeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

}
