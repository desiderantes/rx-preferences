package com.desiderantes.rx.preferences3.sample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.desiderantes.rx.preferences3.Preference;
import com.desiderantes.rx.preferences3.RxSharedPreferences;
import com.desiderantes.rx.preferences3.sample.databinding.SampleActivityBinding;
import com.jakewharton.rxbinding4.widget.RxCompoundButton;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SampleActivity extends Activity {

  Preference<Boolean> fooBoolPreference;
  Preference<String> fooTextPreference;
  CompositeDisposable disposables;

  private SampleActivityBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = SampleActivityBinding.inflate(getLayoutInflater());
    // Views
    setContentView(binding.getRoot());

    // Preferences
    SharedPreferences preferences = getDefaultSharedPreferences(this);
    RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

    // foo
    fooBoolPreference = rxPreferences.getBoolean("fooBool");
    fooTextPreference = rxPreferences.getString("fooText");
  }

  @Override protected void onResume() {
    super.onResume();

    disposables = new CompositeDisposable();
    bindPreference(binding.foo1, fooBoolPreference);
    bindPreference(binding.foo2, fooBoolPreference);
    bindPreference(binding.text1, fooTextPreference);
    bindPreference(binding.text2, fooTextPreference);
  }

  @Override protected void onPause() {
    super.onPause();
    disposables.dispose();
  }

  void bindPreference(final CheckBox checkBox, Preference<Boolean> preference) {
    // Bind the preference to the checkbox.
    disposables.add(preference.asObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(checkBox::setChecked));
    // Bind the checkbox to the preference.
    disposables.add(RxCompoundButton.checkedChanges(checkBox)
        .skip(1) // First emission is the original state.
        .subscribe(preference.asConsumer()));
  }

  void bindPreference(final EditText editText, Preference<String> preference) {
    disposables.add(preference.asObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .filter(s -> !editText.isFocused())
            .subscribe(editText::setText));
    disposables.add(RxTextView.textChangeEvents(editText)
            .skip(1) // First emission is the original state.
            .debounce(500, TimeUnit.MILLISECONDS) // Filter out UI events that are emitted in quick succession.
            .map(e -> e.getText().toString())
            .subscribe(preference.asConsumer()));
  }
}
