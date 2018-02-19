package com.kavi.droid.lowjuicelocation.services.sharePreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by kavi707 on 9/9/17.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class SharedPreferenceManager {

    private static final String LOW_JUICE_LOCATOR_SHARED_PREFERENCES = "LOW_JUICE_LOCATOR_SHARED_PREFERENCES";

    //Shared Preference constants
    private static final String IS_I_NEED_SELECTED = "IS_I_NEED_SELECTED";
    private static final String FCM_PUSH_TOKEN = "FCM_PUSH_TOKEN";

    /**
     * Store boolean shared preference value
     * @param preferenceKey preference key
     * @param preferenceValue preference value
     */
    private static void writeBooleanSharePreference(Context context, String preferenceKey, boolean preferenceValue) {

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = context.getSharedPreferences(LOW_JUICE_LOCATOR_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(preferenceKey, preferenceValue);

        // Commit the edits!
        editor.commit();
    }

    /**
     * Get boolean share preference value
     * @param preferenceKey preference key
     * @return boolean value
     */
    private static boolean readBooleanSharePreference(Context context, String preferenceKey) {
        SharedPreferences settings = context.getSharedPreferences(LOW_JUICE_LOCATOR_SHARED_PREFERENCES, 0);
        return settings.getBoolean(preferenceKey, false);
    }

    /**
     * Store String shared preference value
     * @param preferenceKey preference key
     * @param preferenceValue preference value
     */
    private static void writeStringSharePreference(Context context, String preferenceKey, String preferenceValue) {

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = context.getSharedPreferences(LOW_JUICE_LOCATOR_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(preferenceKey, preferenceValue);

        // Commit the edits!
        editor.commit();
    }

    /**
     * Get String share preference value
     * @param preferenceKey preference key
     * @return String value
     */
    private static String readStringSharePreference(Context context, String preferenceKey) {
        SharedPreferences settings = context.getSharedPreferences(LOW_JUICE_LOCATOR_SHARED_PREFERENCES, 0);
        return settings.getString(preferenceKey, "NULL");
    }

    /**
     * Store String shared preference value
     * @param preferenceKey preference key
     * @param preferenceValue preference value
     */
    private static void writeObjectSharePreference(Context context, String preferenceKey, Object preferenceValue) {

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = context.getSharedPreferences(LOW_JUICE_LOCATOR_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        String jsonString = gson.toJson(preferenceValue);

        editor.putString(preferenceKey, jsonString);

        // Commit the edits!
        editor.commit();
    }

    /**
     * Get String share preference value
     * @param preferenceKey preference key
     * @return String value
     */
    private static Object readObjectSharePreference(Context context, String preferenceKey, Class objectClass) {
        SharedPreferences settings = context.getSharedPreferences(LOW_JUICE_LOCATOR_SHARED_PREFERENCES, 0);

        Gson gson = new Gson();
        String jsonString = settings.getString(preferenceKey, "NULL");

        return gson.fromJson(jsonString, objectClass);
    }

    /**********************************************************************************************/
    /************************ Shared Preference Getters & Setters *********************************/
    /**********************************************************************************************/

    public static void setFCMPushToken(Context context, String value) {
        writeStringSharePreference(context, FCM_PUSH_TOKEN, value);
    }

    public static String getFCMPushToken(Context context){
        return readStringSharePreference(context, FCM_PUSH_TOKEN);
    }

    public static void setIsINeedTypeSelected(Context context, boolean value){
        writeBooleanSharePreference(context, IS_I_NEED_SELECTED, value);
    }

    public static boolean isINeedTypeSelected(Context context){
        return readBooleanSharePreference(context, IS_I_NEED_SELECTED);
    }
}
