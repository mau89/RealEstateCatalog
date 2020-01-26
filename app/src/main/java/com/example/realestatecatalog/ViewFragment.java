package com.example.realestatecatalog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.realestatecatalog.data.PropertyDbHolder;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ViewFragment extends Fragment {
    private SQLiteDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_property, container, false);

        PropertyDbHolder propertyDbHolder = ((App) Objects.requireNonNull(getActivity()).getApplication()).getPropertyDbHolder();
        database = propertyDbHolder.getReadableDatabase();
        String propertyId = ((App) getActivity().getApplication()).getPreferences().getString(Preferences.Key.ID_KEY);
        if (!propertyId.equals("")) {
            loadProperty(propertyId, view);
        }
        return view;
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
            ImageView image_view_property = view.findViewById(R.id.image_view_property);
            TextView text_view_price_property = view.findViewById(R.id.text_view_price_property);
            TextView text_view_area_property = view.findViewById(R.id.text_view_area_property);
            TextView text_view_address_property = view.findViewById(R.id.text_view_address_property);
            TextView text_view_quantity_property = view.findViewById(R.id.text_view_quantity_room_property);
            TextView text_view_price_sqm_property = view.findViewById(R.id.text_view_price_sqm_property);
            TextView text_view_floor_property = view.findViewById(R.id.text_view_floor_property);

            Uri selectedImage = Uri.parse(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_photo)));
            Picasso.get().load(selectedImage).into(image_view_property);

            text_view_price_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_price)));
            text_view_area_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_area)));
            text_view_address_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_address)));
            text_view_quantity_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_quantity_room)));
            text_view_price_sqm_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_price_sqm)));
            text_view_floor_property.setText(cursor.getString(cursor.getColumnIndex(PropertyDbHolder.APP_PREFERENCES_floor)));
        }
        cursor.close();
    }


}
