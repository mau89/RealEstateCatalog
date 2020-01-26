package com.example.realestatecatalog;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.realestatecatalog.data.PropertyDbHolder;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AddFragment extends Fragment {
    private View view;
    private PropertyDbHolder propertyDbHolder;
    private static final int GALLERY_REQUEST = 1;
    private Uri selectedImage;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_property, container, false);
        setHasOptionsMenu(true);
        propertyDbHolder = ((App) getActivity().getApplication()).getPropertyDbHolder();
        addProperty();
        return view;
    }

    private void addProperty() {
        view.findViewById(R.id.button_image_add_property).setOnClickListener(v -> {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

        });

        view.findViewById(R.id.button_add_property).setOnClickListener(v -> {
            EditText text_add_address_property = view.findViewById(R.id.text_add_address_property);
            EditText text_add_area_property = view.findViewById(R.id.text_add_area_property);
            EditText text_add_price_property = view.findViewById(R.id.text_add_price_property);
            EditText text_add_quantity_room_property = view.findViewById(R.id.text_add_quantity_room_property);
            EditText text_add_floor_property = view.findViewById(R.id.text_add_floor_property);

            if (text_add_address_property.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Не заполнен адрес", Toast.LENGTH_LONG).show();

                return;
            }
            if (text_add_price_property.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Не заполнена цена", Toast.LENGTH_LONG).show();

                return;
            }
            if (text_add_quantity_room_property.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Не заполнено количество комнат", Toast.LENGTH_LONG).show();

                return;
            }
            if (text_add_floor_property.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Не указан этаж", Toast.LENGTH_LONG).show();

                return;
            }
            if (text_add_area_property.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Не указана площадь", Toast.LENGTH_LONG).show();

                return;
            }

            SQLiteDatabase database = propertyDbHolder.getWritableDatabase();
            ContentValues contentValues = new ContentValues();


            String price_sqm = String.valueOf(Float.parseFloat(text_add_price_property.getText().toString()) / Float.parseFloat(text_add_area_property.getText().toString()));
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_photo, selectedImage.toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_address, text_add_address_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_area, text_add_area_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_price, text_add_price_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_quantity_room, text_add_quantity_room_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_floor, text_add_floor_property.getText().toString());
            contentValues.put(PropertyDbHolder.APP_PREFERENCES_price_sqm, price_sqm);

            database.insert(PropertyDbHolder.TABLE_CONTACTS, null, contentValues);
            Objects.requireNonNull(getActivity()).onBackPressed();

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        ImageView imageView = view.findViewById(R.id.image_add_property);
        selectedImage = imageReturnedIntent.getData();
        Picasso.get().load(selectedImage).into(imageView);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_edit_property).setVisible(false);
        menu.findItem(R.id.action_add_property).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
