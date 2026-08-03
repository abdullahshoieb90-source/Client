
package com.bedrock.client.fragment
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bedrock.client.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
