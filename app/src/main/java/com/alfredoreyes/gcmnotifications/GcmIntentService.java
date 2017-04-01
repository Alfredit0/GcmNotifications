package com.alfredoreyes.gcmnotifications;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    /**
     * Metodo que recupera el mensaje de la notificación contenida en el intent
     * para luego mostrar dicho mensaje en la barra de notificaciones del dispositivo
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                //Se visualiza el mendaje en la barra de notificaciones
                sendNotification(extras.getString("mensaje"));
            }
        }

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Este metodo lo que hace es visualizar una notificación en la barra de
     * notificaciones con el mensaje pasado por parametro
     *
     * @param msg mensaje que se muestra en la notificación
     */
    private void sendNotification(String msg) {
        // Sonido por defecto de notificaciones, podemos usar otro
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Patrón de vibración: 1 segundo vibra, 0.5 segundos para, 1 segundo vibra
        long[] pattern = new long[]{1000,500,1000};

        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.ic_contact_mail_black_24dp)
                .setContentTitle("Mesaje Calius")
                .setSound(defaultSound)//Se agrega sonido a la notificacion
                .setVibrate(pattern)//Se agrega vibracion a la notificacion
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}