package com.example.realestatecatalog;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.realestatecatalog.data.PropertyDbHolder;

import java.util.Objects;

public class EditFragment extends Fragment {
    private View view;
    private SQLiteDatabase database;
    private PropertyDbHolder propertyDbHolder;
    private String propertyId;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_property, container, false);
        propertyDbHolder = ((App) Objects.requireNonNull(getActivity()).getApplication()).getPropertyDbHolder();
        database = propertyDbHolder.getReadableDatabase();
        propertyId = ((App) getActivity().getApplication()).getPreferences().getString(Preferences.Key.ID_KEY);
        setHasOptionsMenu(true);
        String propertyId = ((App) getActivity().getApplication()).getPreferences().getString(Preferences.Key.ID_KEY);
        if (!propertyId.equals("")) {
            loadProperty(propertyId, view);
        }
        editProperty();
        return view;
    }

    private void editProperty() {
        view.findViewById(R.id.button_edit_property).setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            TextView text_view_price_property = view.findViewById(R.id.text_edit_price_property);
            TextView text_view_area_property = view.findViewById(R.id.text_edit_area_property);
            TextView text_view_quantity_room_property = view.findViewById(R.id.text_edit_quantity_room_property);
            TextView text_view_floor_property = view.findViewById(R.id.text_edit_floor_property);
            String priceSqm = String.valueOf(Float.parseFloat(text_view_price_property.getText().toString()) / Float.parseFloat(text_view_area_property.getText().toString()));
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_price, text_view_price_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_area, text_view_area_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_quantity_room, text_view_quantity_room_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_floor, text_view_floor_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_price_sqm, priceSqm);

            propertyDbHolder.getWritableDatabase().update(PropertyDbHolder.TABLE_CONTACTS, contentValues, PropertyDbHolder.KEY_ID + "=?", new String[]{propertyId});
            Objects.requireNonNull(getActivity()).onBackPressed();
        });
    }

    private void loadProperty(String propertyId, View view) {
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
                PropertyDbHolder.APP_PREFERENCES_price_sqm,
                PropertyDbHolder.APP_PREFERENCES_floor
        };
        String selection = "_id = ?";
        String[] selectionArgs = new String[]{propertyId};
        Cursor cursor = database.query(
                PropertyDbHolder.TABLE_CONTACTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            TextView text_view_price_property = view.findViewById(R.id.text_edit_price_property);
            TextView text_view_area_property = view.findViewById(R.id.text_edit_area_property);
            TextView text_view_quantity_room_property = view.findViewById(R.id.text_edit_quantity_room_property);
            TextView text_view_floor_property = view.findViewById(R.id.text_edit_floor_property);

            text_view_price_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_price)));
            text_view_area_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_area)));
            text_view_quantity_room_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_quantity_room)));
            text_view_floor_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_floor)));
        }
        cursor.close();
    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_edit_property).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
