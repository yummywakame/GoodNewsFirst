/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yummywakame.breakroom;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTheme(R.style.SettingsFragmentTheme);

        // Find the menu toolbar for app compat
//        Toolbar mToolbar = findViewById(R.id.main_toolbar);
//        setSupportActionBar(mToolbar);

        // Hide the default title to use the designed one instead
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

//            Preference topic = findPreference(getString(R.string.settings_topic_key));
//            bindPreferenceSummaryToValue(topic);
//
//            Preference search = findPreference(getString(R.string.settings_search_key));
//            bindPreferenceSummaryToValue(search);
//
//            Preference fromDate = findPreference(getString(R.string.settings_from_date_key));
//            bindPreferenceSummaryToValue(fromDate);
//
//            Preference toDate = findPreference(getString(R.string.settings_to_date_key));
//            bindPreferenceSummaryToValue(toDate);
//
//            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
//            bindPreferenceSummaryToValue(orderBy);
//
//            Preference todayDate = findPreference(getString(R.string.settings_switch_key));
//            checkSwitchValue(todayDate);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[]labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }
            else if (preference instanceof SwitchPreference) {
                Log.e("Switch is set to: ", stringValue);
                Preference toDate = findPreference("to-date");
                if(stringValue.equals("true")){
                    toDate.setEnabled(false);
                } else{
                    toDate.setEnabled(true);
                }
            }
            else{
                preference.setSummary(stringValue);
            }

            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

        private void checkSwitchValue(Preference switchPreference) {
            switchPreference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(switchPreference.getContext());
            Boolean preferenceBoolean = preferences.getBoolean(switchPreference.getKey(), false);
            onPreferenceChange(switchPreference, preferenceBoolean);
        }
    }
}