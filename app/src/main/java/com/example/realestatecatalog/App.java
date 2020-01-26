package com.example.realestatecatalog;

import android.app.Application;
import android.preference.PreferenceManager;

import com.example.realestatecatalog.data.PropertyDbHolder;
import com.example.realestatecatalog.Preferences;


public class App extends Application {
    private PropertyDbHolder propertyDbHolder;
    private Preferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new Preferences(PreferenceManager.getDefaultSharedPreferences(this));
        propertyDbHolder = new PropertyDbHolder(this);
    }
    public PropertyDbHolder getPropertyDbHolder() {
        return propertyDbHolder;
    }

    public Preferences getPreferences() {
        return preferences;
    }
}
