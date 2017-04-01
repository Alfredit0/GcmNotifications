package com.alfredoreyes.gcmnotifications;

/**
 * Created by Meltsan on 26/03/2017.
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Lo único que hace este BroadcastReceiver es iniciar el servicio
 * GcmIntentService al capturar el intent, el servicio a su vez visualizara la
 * notificación en la barra de notificaciones ya que si se manipula directamente
 * la interfaz de usuario desde el propio BroadcastReceiver se corre el riesgo
 * que si el proceso dura mas de 5 segundos, el sistema operativo lance una
 * excepción, mientras que si se manipula la interfaz desde un servicio no
 * existe tal inconveniente
 *
 *
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Especificar explicitamente que GcmIntentService debe manejar el
        // intent
        ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
        // Se inicia el servicio, manteniento el dispositovo despierto mientras
        // se esta lanzando
        startWakefulService(context, (intent.setComponent(comp)));
        //
        setResultCode(MainActivity.RESULT_OK);
    }
}