
package com.ciscojabberguest;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.cisco.jabber.guest.sdk.JabberGuestCallActivity;

public class RNReactNativeCiscoJabberGuestModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private String callerName;
  public static final String DEFAULT_SERVER = "jabberguestsandbox.cisco.com";
  public static final String DEFAULT_ADDRESS = "5555";
  public static final String TAG = "JabberGuestModule";


  public RNReactNativeCiscoJabberGuestModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNReactNativeCiscoJabberGuest";
  }

  @ReactMethod
  public boolean init(String phoneNumber, String callerName) {
      if ((phoneNumber == null || phoneNumber.isEmpty()) && (callerName == null || callerName.isEmpty()))
          return false;

      if (phoneNumber == null || phoneNumber.isEmpty()) {
          this.callerName = callerName;
      } else if (callerName == null || callerName.isEmpty()) {
          this.callerName = phoneNumber;
      } else {
          this.callerName = phoneNumber + " " + callerName;
      }

      return true;
  }

  @ReactMethod
  public void startChat() {
      Activity activity = getCurrentActivity();
      if (activity != null) {
          Intent intent = new Intent(activity, JabberGuestCallActivity.class);
          intent.putExtra(JabberGuestCallActivity.ARG_ADDRESS_TO_DIAL, DEFAULT_ADDRESS);
          intent.putExtra(JabberGuestCallActivity.ARG_JABBER_GUEST_SERVER, DEFAULT_SERVER);
          intent.putExtra(JabberGuestCallActivity.ARG_DISPLAY_NAME_TO_DIAL, callerName);
          intent.putExtra(JabberGuestCallActivity.ARG_SHOW_PREVIEW, true);
          activity.startActivity(intent);
      } else {
          Log.e(TAG, "Could not load getCurrentActivity -- no UI can be displayed without it.");
      }

  }
}