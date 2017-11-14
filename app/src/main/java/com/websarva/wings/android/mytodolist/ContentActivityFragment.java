package com.websarva.wings.android.mytodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ContentActivityFragment extends Fragment {

    private Activity _parentActivity;

    private boolean _isLayoutland = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _parentActivity = getActivity();

        FragmentManager manager = getFragmentManager();
        MainActivityFragment menuListFragment = (MainActivityFragment) manager.findFragmentById(R.id.fragmentMenuList);
        if(menuListFragment == null) {
            _isLayoutland = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_activity, container, false);

        String menuContent = "";
        String menuDay = "";
        if(_isLayoutland) {  // （2）
            Bundle extras = getArguments();  // （3）
            menuContent = extras.getString("menuContent");  //（4）
            menuDay = extras.getString("menuDay");  //（4）
        }
        else {  // （5）
            Intent intent = _parentActivity.getIntent();  // （6）
            menuContent = intent.getStringExtra("menuContent");  // （6）
            menuDay = intent.getStringExtra("menuDay");  // （6）
        }


        TextView tvContent = view.findViewById(R.id.tvContent);
        TextView tvDay = view.findViewById(R.id.tvDay);

        tvContent.setText(menuContent);
        tvDay.setText(menuDay);

        Button btBackButton =view.findViewById(R.id.btBackButton);
        btBackButton.setOnClickListener(new ButtonClickListener());

        return view;
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (_isLayoutland) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(ContentActivityFragment.this);
                transaction.commit();
            }
            else {
                _parentActivity.finish();
            }
        }
    }
}

