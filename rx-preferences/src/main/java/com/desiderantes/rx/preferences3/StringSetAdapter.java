package com.desiderantes.rx.preferences3;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Set;

import static java.util.Collections.unmodifiableSet;


final class StringSetAdapter implements RealPreference.Adapter<Set<String>> {
    static final StringSetAdapter INSTANCE = new StringSetAdapter();

    @NonNull
    @Override
    public Set<String> get(@NonNull String key,
                           @NonNull SharedPreferences preferences, @NonNull Set<String> defaultValue) {
        return unmodifiableSet(preferences.getStringSet(key, defaultValue));
    }

    @Override
    public void set(@NonNull String key, @NonNull Set<String> value,
                    @NonNull SharedPreferences.Editor editor) {
        editor.putStringSet(key, value);
    }
}
