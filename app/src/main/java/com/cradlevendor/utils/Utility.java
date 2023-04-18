package com.cradlevendor.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static final int MY_PERMISSIONS_GPS = 222;

    public static String UID = "";

    // GoogleApiClient googleApiClient;

    LocationManager locationManager;
    /* LocationRequest locationRequest;
     LocationSettingsRequest.Builder locationSettingsRequest;
   */ Context context;
    //PendingResult<LocationSettingsResult> pendingResult;

    public static Boolean isValidEmail(String email) {
        Boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public static Boolean isValidPass(String pass) {
        Boolean isValid = false;

        //Minimum eight characters, at least one letter, one number and one special character:
        String expression = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

        String expression4 = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$";

        //Minimum eight characters, at least one letter and one number:
        String expression1 = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

        //Minimum eight characters, at least one uppercase letter, one lowercase letter and one number:
        String expression2 = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

        Pattern pattern = Pattern.compile(expression4);
        Matcher matcher = pattern.matcher(pass);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }



    /*created by : Rahul Tamrakar
    created date : 20 July 2022
     @toastMessage method is used for to display toast message by pass context and message only*/
    public static void toastMessage(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }
    
   


    /* public static boolean isLocationEnabled(Activity mActivity) {
         LocationManager lm = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
         boolean gps_enabled = false;
         boolean network_enabled = false;
         try {
             gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
         } catch (Exception ex) {
         }
         try {
             network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
         } catch (Exception ex) {
         }

         if (!gps_enabled && !network_enabled) {
             enableLocationSettingDialog(mActivity, "Please turn on Your GPS");
         } else {
             return true;
         }
         return false;
     }
 */
    public static void enableLocationSettingDialog(final Activity mActivity, String message) {
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mActivity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), MY_PERMISSIONS_GPS);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();*/


        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        mActivity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



}
