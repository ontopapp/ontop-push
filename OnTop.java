package com.poya.notifications;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Poya on 2016-04-12.
 */
public class OnTop
{
    public static final String TAG = "OnTop";
    public static final int VERSION_CODE = 1;
    private final String SEND_END_POINT = "http://ontop.tech/api/send";

    private String appId = "";
    private String appSecret = "";
    private String message = "";
    private String view = "";
    private String category = "";
    private String action = "";
    private boolean notificationSound = false;
    private boolean notificationVibrate = true;
    private JSONObject custom = new JSONObject();

    public OnTop(String appId, String appSecret)
    {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    /**
     * Message that will primarily be shown on the notification
     * Max 250 characters
     *
     * @param message   the text to show on the notification
     * @return self
     */
    public OnTop setMessage(String message)
    {
        this.message = message;
        return this;
    }

    /**
     * (Optional)
     * Add a view tag to represent the view/page that the event has occurred. (i.e. "login", "new_item")
     * Can contain only alphabet, underscore, and numbers with max 64 characters.
     *
     * @param view          Name of the view
     * @return self
     */
    public OnTop setView(String view)
    {
        this.view = view;
        return this;
    }

    /**
     * (Optional)
     * Add a category to represent the entities/categories. (i.e. "user", "song")
     * Can contain only alphabet, underscore, and numbers with max 64 characters.
     *
     * @param category      Name of the category
     * @return self
     */
    public OnTop setCategory(String category)
    {
        this.category = category;
        return this;
    }

    /**
     * Add a action tag to represent the action performed. (i.e. "like", "add", "share")
     * Can contain only alphabet, underscore, and numbers with max 64 characters.
     *
     * @param action        Name of the action
     * @return self
     */
    public OnTop setAction(String action)
    {
        this.action = action;
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "user_name")
     * @param value     the value being sent (i.e. "John Doe")
     * @return self
     */
    public OnTop setCustom(String key, String value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "is_new")
     * @param value     the value being sent (i.e. true)
     * @return self
     */
    public OnTop setCustom(String key, boolean value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "user_id")
     * @param value     the value being sent (i.e. 343)
     * @return self
     */
    public OnTop setCustom(String key, int value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "date")
     * @param value     the value being sent (i.e. 1459836752)
     * @return self
     */
    public OnTop setCustom(String key, long value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "latitude")
     * @param value     the value being sent (i.e. 79.3456)
     * @return self
     */
    public OnTop setCustom(String key, double value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * (Optional)
     * Whether or not to vibrate on notification received for this notification
     * Default to true
     *
     * @param notificationVibrate   should the notification vibrate the phone?
     * @return self
     */
    public OnTop setNotificationVibrate(boolean notificationVibrate)
    {
        this.notificationVibrate = notificationVibrate;
        return this;
    }

    /**
     * (Optional)
     * Whether or not play a ringtone on notification received for this notification
     * Default to false
     *
     * @param notificationSound   should the notification play a ringtone?
     * @return self
     */
    public OnTop setNotificationSound(boolean notificationSound)
    {
        this.notificationSound = notificationSound;
        return this;
    }

    /**
     * Returns the very base API call url with authentication info to be used for either GET/POST
     * You don't need this if you're using either {@link OnTop#getCompiledUrl()}
     * or {@link OnTop#send()}
     *
     * @return Base url with required auth info
     */
    public String getBaseUrl()
    {
        String url = SEND_END_POINT
                + "?id="        + this.appId
                + "&key="       + this.appSecret
                + "&api_ver="   + this.VERSION_CODE;
        return url;
    }

    /**
     * Returns a compiled GET call url based on current values. Note it'll issue a warning
     * to use POST if the URL is too long for a GET call
     * You dont need to use this if you're using {@link OnTop#send()}
     *
     * @return Compiled URL
     */
    public String getCompiledUrl()
    {
        String url = getBaseUrl();
        url += "&noti_sound="       + (this.notificationSound ? 1 : 0);
        url += "&noti_vibrate="     + (this.notificationVibrate ? 1 : 0);
        if (!view.equals(""))       url += "&view=" + this.view;
        if (!category.equals(""))   url += "&category=" + this.category;
        if (!action.equals(""))     url += "&action=" + this.action;
        if (!message.equals(""))    url += "&message=" + URLEncoder.encode(this.message);
        url += "&custom=" + URLEncoder.encode(custom.toString());

        if(url.length() > 990)
            Log.e(TAG, "The url is too long for a GET call. Must use POST.");

        return url;
    }

    /**
     * Sends the notification through a POST call.
     * If you need to make a GET call use {@link OnTop#getCompiledUrl()}
     * If you want to build you're own POST call use {@link OnTop#getBaseUrl()}
     * to get the base url required for either GET/POST
     */
    public void send()
    {
        final String url = getBaseUrl() + "&is_post=1";

        RequestParams params = new RequestParams();
        params.put("noti_sound", (this.notificationSound ? 1 : 0));
        params.put("noti_vibrate", (this.notificationVibrate ? 1 : 0));
        if (!view.equals(""))       params.put("view", this.view);
        if (!category.equals(""))   params.put("category", this.category);
        if (!action.equals(""))     params.put("action", this.action);
        if (!message.equals(""))    params.put("message", this.message);
        params.put("custom", custom.toString());

        Log.i(TAG, "POST Url: " + url);
        Log.i(TAG, "POST params: " + params.toString());

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.post(url, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                Log.i(TAG, "Sending: " + url);
            }

            @Override
            public void onFinish()
            {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response)
            {
                String str_response="";
                try {
                    str_response = new String(response, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try{
                    JSONObject data = new JSONObject(str_response);
                    if (data.getString("status").equals("ok"))
                        Log.i(TAG, "Received: status " + data.getString("status") + " - " + data.getString("msg"));
                    else
                        Log.e(TAG, "Received: status " + data.getString("status") + " - " + data.getString("msg"));

                } catch (Exception t) {
                    Log.e(TAG, "Malformed json response. Received: " + str_response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    Log.e(TAG, "Call failed. Possibly due to no connection or timeout. Received: " + new String(responseBody, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
