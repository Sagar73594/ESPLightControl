package com.errorerrorerror.esplightcontrol;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.errorerrorerror.esplightcontrol.di.AppModule;
import com.errorerrorerror.esplightcontrol.di.RoomModule;
import com.errorerrorerror.esplightcontrol.di.component.AppComponent;
import com.errorerrorerror.esplightcontrol.di.component.DaggerAppComponent;
import com.errorerrorerror.esplightcontrol.model.DeviceRepo;

import javax.inject.Inject;

public class EspApp extends Application {

    public static final String CHANNEL_ID = "networkServiceChannel";
    @Inject
    public DeviceRepo deviceRepo;
    private AppComponent appComponent;
    private static EspApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        dagger();
        instance = this;
    }

    void dagger() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Testing service Esp",
                    NotificationManager.IMPORTANCE_LOW
            );
            serviceChannel.setSound(null, null);
            serviceChannel.enableVibration(false);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public static EspApp getInstance() {
        return EspApp.instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
