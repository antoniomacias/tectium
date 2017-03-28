package com.ammacias.tectium;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ammacias.tectium.Utils.MyInfiniteListAdapter;
import com.softw4re.views.InfiniteListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfinityFragment extends Fragment {
    private final int ITEM_COUNT_TO_LOAD = 5;
    private final int ITEM_COUNT_LIMIT = 15;
    private final int TIME_TO_LOAD = 1500; //in ms

    private int itemOffset = 0;

    private LinearLayout container;
    private InfiniteListView<String> infiniteListView;

    private ArrayList<String> itemList;
    private MyInfiniteListAdapter<String> adapter;

    public InfinityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_lista_infinita, container, false);

        container = (LinearLayout) v.findViewById(R.id.container);
        infiniteListView = (InfiniteListView) v.findViewById(R.id.infiniteListView);

        itemList = new ArrayList<>();
        //adapter = new MyInfiniteListAdapter(getActivity(), R.layout.fragment_evento_item, itemList);

        infiniteListView.setAdapter(adapter);

        loadNewItems();

        return v;
    }

    //SIMULATES ITEM LOADING
    public void loadNewItems() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                infiniteListView.startLoading();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(TIME_TO_LOAD);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                if(itemOffset >= ITEM_COUNT_LIMIT) {
                    infiniteListView.hasMore(false);
                }
                else {
                    //ADD NEW ITEMS TO LIST
                    for (int i = itemOffset; i < itemOffset + ITEM_COUNT_TO_LOAD; i++) {
                        String item = "Item #" + i;
                        infiniteListView.addNewItem(item);
                    }
                    itemOffset += ITEM_COUNT_TO_LOAD;
                    Log.d("InfiniteListView", "Current item count = " + itemOffset);

                    infiniteListView.hasMore(true);
                }

                infiniteListView.stopLoading();
            }
        }.execute();
    }

    //DO THIS ON SWIPE-REFRESH
    public void refreshList() {
        itemOffset = 0;
        infiniteListView.clearList();
        loadNewItems();
    }

    //DO THIS ON ITEM CLICK
    public void clickItem(int position) {
        Snackbar.make(container, "Item clicked: " + position, Snackbar.LENGTH_SHORT).show();
    }

    //DO THIS ON ITEM LONG-CLICK
    public void longClickItem(int position) {
        Snackbar.make(container, "Item long-clicked: " + position, Snackbar.LENGTH_SHORT).show();
    }

}
