package com.soomapps.universalremotecontrol.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;
import java.util.Timer;

public class VibrationFunction {
    public static void vibrateDevice(Context context,Boolean bvalue) {
        if(bvalue) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
        }
        }

    public static void copyTextToClipboard(Context context, String text,Boolean bvalue) {
        if(bvalue) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", text);
            clipboard.setPrimaryClip(clip);
            showToast(context, "Copied to clipboard!");
        }
    }
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void beep(Context context, Boolean bvalue) {
        if(bvalue) {
           /* Uri beepSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File.separator + context.getPackageName() + "/raw/beep6.wav");
            Ringtone r = RingtoneManager.getRingtone(context, beepSound);
            r.play();*/
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_DTMF_B,200);
          /*  Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();*/
        }
    }
    public static void openLink(Context context,String link, Boolean bvalue) {
        if(bvalue) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(link));
            context.startActivity(i);
        }
    }
    public static void searchProduct(Context context,String product,String result) {
        //  if(bvalue) {
        //searchProduct(ScanResultActivity.this, productSelect, scanResult);
        Intent i = new Intent(Intent.ACTION_VIEW);
       // i.setData(Uri.parse("https://www.google"+product+"/search?output=search&tbm=shop&q="+result));
        i.setData(Uri.parse("https://www.google"+product+"/search?q="+result));
        //https://www.google"+product+"/search?output=search&tbm=shop&q="+result
        context.startActivity(i);
        // }
    }
}
