package com.uoc.miquel.uocpac1app.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.activities.BookDetailActivity;
import com.uoc.miquel.uocpac1app.activities.BookListActivity;
import com.uoc.miquel.uocpac1app.model.BookContent;

/**
 * Created by mucl on 29/11/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String ACTION_DETAIL = "detail";
    public static final String ACTION_ERASE = "erase";




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Creem una notificació local a partir de les dades rebudes de la notificació Firebase.
        Log.i("MYFIREBASE","onMessageReceived");
        sendNotification(remoteMessage.getNotification().getBody(),0);
    }

    private void sendNotification(String messageBody, int position) {

        //BookContent.BookItem bookItem;

        //obtenim el book a partir de la posicio
        //bookItem = BookContent.getBooks().get(position);


        ///////////////// INTENTS  /////////////////////
        // Creem un intent per quan vulguem veure el detall del llibre
        Intent detailIntent = new Intent(this, BookDetailActivity.class);
        detailIntent.setAction(ACTION_DETAIL);
        PendingIntent piDetail = PendingIntent.getActivity(this,(int) System.currentTimeMillis(),detailIntent,0);


        //Creem un intent per quan vulguem borrar el llibre de la based dades local.
        Intent eraseIntent = new Intent(this, BookDetailActivity.class);
        eraseIntent.setAction(ACTION_ERASE);
        PendingIntent piErase = PendingIntent.getActivity(this,(int) System.currentTimeMillis(),eraseIntent,0);
        ///////////////////////////////////////////////

        //Es crea un so per la notificacio
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Es crea el builder de la notificació.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                //Configurem la icona de la barra d'estat
                .setSmallIcon(R.mipmap.ic_launcher)
                //Titol de la notificacio visible quan esta extesa.
                .setContentTitle("Firebase")
                //Text de la notificacio visible quan esta extesa.
                .setContentText("Text normal de la notificació")
                //Indiquem a la notificació que es pot tancar despres de presionar-la
                .setAutoCancel(true)
                //Asigna el so que hem creat anteriorment.
                .setSound(defaultSoundUri)

                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Text extra de la notificació expandida."))
                .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher,"Eliminar",piErase))
                .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher,"Veure",piDetail));

        //Es crea una instancia del gestor de notificacions.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //S'executa la notificació.
        notificationManager.notify(0, notificationBuilder.build());
    }



    private void sendNotification2 (String msg, int i) {

        Intent intent = new Intent(this, BookListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(msg)
                .setContentTitle("Titol de la notificacio")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(0, notificationBuilder.build());

    }



}
