package com.android.proiect;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.proiect.Database.AppDatabase;
import com.android.proiect.Database.Entities.Shop;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class LocationWorker extends Worker {

    private Context context;

    private AppDatabase appDatabase;

    private static boolean hasRun = false;

    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
        this.appDatabase = AppDatabase.getInstance(context);
    }

    @Override
    public @NonNull Result doWork() {
        if(hasRun) return Result.success();
        if(checkProximity()) {
            createNotification();
            hasRun = true;
            return Result.success();
        }
        try {
            Thread.sleep(10000);
        } catch(Exception e) {
            return Result.failure();
        }
        doWork();
        return Result.failure();
    }

    private boolean checkProximity() {
        List<Shop> shops = appDatabase.shopDao().getAll();
        for(Shop shop : shops) {
            if(Math.abs(shop.xCoordinate - MainActivity.longitude) < 0.00225 &&
                    Math.abs(shop.yCoordinate - MainActivity.latitude) < 0.0025) return true;
        }
        return false;
    }

    private void createNotification() {
        Intent intent = new Intent(context, NearbyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Shopping Assistant")
                .setContentText("There are shops nearby with products from your cart.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="test";
            String description = "test";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
