package receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;

import com.order.app.order.R;
import com.order.app.order.UserProfile;

import functions.AppConstant;

public class CheckSubscriptionReceiver extends BroadcastReceiver{
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;


    @Override
    public void onReceive(Context context, Intent intent) {

            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    PowerManager.ON_AFTER_RELEASE, AppConstant.TEMP_WAKELOCK);
            wl.acquire();
            builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(context.getString(R.string.subscription_notif_title));
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle(builder);
            style.bigText(context.getString(R.string.subscription_notif_message));
            builder.setStyle(style);
            builder.setAutoCancel(true);
            builder.setTicker(AppConstant.ORDERING_SYSTEM_DEFAULT + context.getString(R.string.ticker_subscription_msg));
            builder.setSmallIcon(R.mipmap.launcher_icon_web);
            builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS | NotificationCompat.DEFAULT_VIBRATE);
            builder.setSound((Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                            + "://" + context.getPackageName() + "/raw/not")));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, UserProfile.class), 0);
            builder.setContentIntent(pendingIntent);
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
            wl.release();

    }
}
