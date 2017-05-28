package cr.ac.ucr.paraiso.tydilabs.configuration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Set;

import cr.ac.ucr.paraiso.tydilabs.models.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Project: Tydilabs
 * Date: 5/27/17
 *
 * @author ricardo
 */

public class ConfigManager implements SharedPreferences.Editor {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public ConfigManager(Context context) {
        this.preferences = context.getSharedPreferences("tydilabs", MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    /**
     * Saves a new String in SharedPreferences for Tydilabs namespace.
     *
     * @param key   The key of the new preference to save.
     * @param value The value to save
     */
    public void putObject(String key, Object value) {
        putString(key, serializeObject(value));
        apply();
    }

    public Object getObject(String key, Class cls) {
        String json = preferences.getString(key, "");
        return new Gson().fromJson(json, cls);
    }

    public User getUser() {
        String json = preferences.getString("loggedInUser", "");
        return new Gson().fromJson(json, User.class);
    }

    private String serializeObject(Object object) {
        return new Gson().toJson(object);
    }

    @Override
    public SharedPreferences.Editor putString(String key, String value) {
        return editor.putString(key, value);
    }

    @Override
    public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
        return editor.putStringSet(key, values);
    }

    @Override
    public SharedPreferences.Editor putInt(String key, int value) {
        return editor.putInt(key, value);
    }

    @Override
    public SharedPreferences.Editor putLong(String key, long value) {
        return editor.putLong(key, value);
    }

    @Override
    public SharedPreferences.Editor putFloat(String key, float value) {
        return editor.putFloat(key, value);
    }

    @Override
    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        return editor.putBoolean(key, value);
    }

    @Override
    public SharedPreferences.Editor remove(String key) {
        return editor.remove(key);
    }

    @Override
    public SharedPreferences.Editor clear() {
        return editor.clear();
    }

    @Override
    public boolean commit() {
        return editor.commit();
    }

    @Override
    public void apply() {
        editor.apply();
    }
}
