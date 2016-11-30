package com.uoc.miquel.uocpac1app.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

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
        sendNotification(remoteMessage.getNotification().getBody(),0);    //S'ha d'obtenir el valor de la clau valor.
    }

    private void sendNotification(String messageBody, int position) {

        BookContent.BookItem bookItem;

        //obtenim el book a partir de la posicio
        bookItem = BookContent.getBooks().get(position);

        // Creem un intent per quan vulguem veure el detall del llibre
        Intent detailIntent = new Intent(this, BookDetailActivity.class);
        //TODO: Que fan els FLAGS????
        detailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Creem un intent per quan vulguem borrar el llibre de la based dades local.
        Intent listIntent = new Intent(this, BookDetailActivity.class);
        listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //TODO: Que es el PendingIntent????
        PendingIntent piDetail = PendingIntent.getActivity(this,0,detailIntent,PendingIntent.FLAG_ONE_SHOT);
        PendingIntent piErase = PendingIntent.getActivity(this,0,listIntent,PendingIntent.FLAG_ONE_SHOT);

        //Es crea un so per la notificacio
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Es crea el builder de la notificació.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                //Configurem la icona de la barra d'estat
                .setSmallIcon(R.mipmap.ic_launcher)
                //Titol de la notificacio visible quan esta extesa.
                .setContentTitle(bookItem.getTitle())
                //Text de la notificacio visible quan esta extesa.
                //.setContentText(messageBody)
                //TODO: Que fa setAutoCancel????
                .setAutoCancel(true)
                //Asigna el so que hem creat anteriorment.
                .setSound(defaultSoundUri)

                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(messageBody))
                .addAction(R.mipmap.ic_launcher,"Eliminar",piErase)
                .addAction(R.mipmap.ic_launcher,"Veure",piDetail);
                //TODO: Que fa el setContentIntent????
                //.setContentIntent(pendingIntent);

        //Es crea una instancia del gestor de notificacions.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //S'executa la notificació.
        notificationManager.notify(0,notificationBuilder.build());
    }

}
