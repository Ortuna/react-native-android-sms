package my.qash.react;


import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;

import android.os.Bundle;
import android.widget.Toast;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.app.Activity;
import android.text.TextUtils;

import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Mohammed Makhlouf on 11/6/15.
 * http://qash.my
 */
public class SmsModule extends ReactContextBaseJavaModule {
    public SmsModule(ReactApplicationContext reactContext)
    {
        super(reactContext);
    }

    @Override
    public String getName()
    {
        return "Sms";
    }

    @ReactMethod
    public synchronized void list(String location, final Callback callback) {
      // public static final String INBOX = "content://sms/inbox";
      // public static final String SENT = "content://sms/sent";
      // public static final String DRAFT = "content://sms/draft";
      Cursor cursor = getReactApplicationContext()
          .getContentResolver()
          .query(Uri.parse(location), null, null, null, null);

      List<String> json = new ArrayList<String>();

      if (cursor.moveToFirst()) {
        do {
          List<String> row = new ArrayList<String>();
          for(int idx=0; idx < cursor.getColumnCount(); idx++) {
            String record = "\"" + cursor.getColumnName(idx) + "\":";
            record += "\"" + cursor.getString(idx) + "\"";
            row.add(record);
          }

          json.add("{" + TextUtils.join(",", row) + "}");
        } while (cursor.moveToNext());
      }

      callback.invoke("[" + TextUtils.join(",", json) + "]");
    }
  }
