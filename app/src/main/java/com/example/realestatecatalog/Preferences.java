package com.example.realestatecatalog;

import android.content.SharedPreferences;

public class Preferences {
    private final SharedPreferences preferences;

    public enum Key {
        ID_KEY
    }

    public Preferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void putString(String string, Key key) {
        preferences.edit().putString(key.name(), string).apply();
    }

    public String getString(Key key) {
        return preferences.getString(key.name(), "");
    }
}
