package com.example.realestatecatalog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatecatalog.data.PropertyAdd;
import com.example.realestatecatalog.data.PropertyDbHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {
    private View view;
    private SQLiteDatabase database;
    private List<Object> propertyAdds = new ArrayList<>();
    private String propertyName;

    private List<String> propertyList = new ArrayList<>();
    private PropertyAddAdapter propertyAddAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        PropertyDbHolder propertyDbHolder = ((App) Objects.requireNonNull(getActivity()).getApplication()).getPropertyDbHolder();
        database = propertyDbHolder.getReadableDatabase();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (database == null) {
            return;
        }
        String[] projection = {
                PropertyDbHolder.KEY_ID,
                PropertyDbHolder.APP_PREFERENCES_photo,
                PropertyDbHolder.APP_PREFERENCES_address,
                PropertyDbHolder.APP_PREFERENCES_area,
                PropertyDbHolder.APP_PREFERENCES_price,
                PropertyDbHolder.APP_PREFERENCES_quantity_room,
                PropertyDbHolder.APP_PREFERENCES_floor
        };
        Cursor cursor = database.query(
                PropertyDbHolder.TABLE_CONTACTS,
                projection,
                null,
                null,
                null,
                null,
                null);
        propertyAdds.clear();

        while (cursor.moveToNext()) {
            int currentID = cursor.getInt(cursor.getColumnIndex(PropertyDbHolder.KEY_ID));
            propertyList.add(propertyName);
            propertyAdds.add(new PropertyAdd(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_address)),
                    cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_area)),
                    cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_photo)),
                    cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_price)),
                    currentID
            ));

        }
        setUpRecyclerView(view.findViewById(R.id.linear_city));
        cursor.close();
    }

    private void setUpRecyclerView(LinearLayout historyLayout) {
        View historyView = LayoutInflater.from(getActivity())
                .inflate(R.layout.layout_property_list, historyLayout, false);
        RecyclerView recyclerView = historyView.findViewById(R.id.recycler_property);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        setPropertyAdapter();
        recyclerView.setAdapter(propertyAddAdapter);
        historyLayout.addView(historyView);
        propertyAddAdapter.notifyDataSetChanged();
    }


    private void setPropertyAdapter() {
        propertyAddAdapter = new PropertyAddAdapter(propertyAdds);
        propertyAddAdapter.notifyDataSetChanged();
        propertyAddAdapter.setOnClickProperty(objects -> {
            Objects.requireNonNull(getFragmentManager()).beginTransaction()
                    .replace(R.id.container, new ViewFragment())
                    .addToBackStack(MainFragment.class.getName())
                    .commit();
            ((App) Objects.requireNonNull(getActivity()).getApplication()).getPreferences().
                    putString(String.valueOf(((PropertyAdd) objects).getCurrentID()), Preferences.Key.ID_KEY);
        });

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_edit_property).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
