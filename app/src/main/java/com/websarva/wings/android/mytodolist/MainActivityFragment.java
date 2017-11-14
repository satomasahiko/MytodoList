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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivityFragment extends Fragment {

    private Activity _parentActivity;

    private boolean _isLayoutland = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _parentActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        ListView lvMenu = (ListView) view.findViewById(R.id.lvMenu);

        List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();

        Map<String, String> menu = new HashMap<String, String>();
        menu.put("content", "レポート");
        menu.put("day", "11/22");
        menuList.add(menu);

        menu = new HashMap<String, String>();
        menu.put("content", "発表スライド");
        menu.put("day", "11/13");
        menuList.add(menu);

        menu = new HashMap<String, String>();
        menu.put("content", "調べもの");
        menu.put("day", "11/18");
        menuList.add(menu);


        String[] from = {"content", "day"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(_parentActivity, menuList, android.R.layout.simple_list_item_2, from, to);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new ListItemClickListener());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View menuThanksFrame = _parentActivity.findViewById(R.id.menuThanksFrame);
        if(menuThanksFrame == null) {
            _isLayoutland = false;
        }
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
            String menuContent = item.get("content");
            String menuDay = item.get("day");
            Bundle bundle = new Bundle();
            bundle.putString("menuContent", menuContent);
            bundle.putString("menuDay", menuDay);
            if(_isLayoutland) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                ContentActivityFragment menuThanksFragment = new ContentActivityFragment();
                menuThanksFragment.setArguments(bundle);
                transaction.replace(R.id.menuThanksFrame, menuThanksFragment);
                transaction.commit();
            }
            else {
                Intent intent = new Intent(_parentActivity, ContentActivityFragment.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}

