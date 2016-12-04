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

import java.util.Map;

/**
 * Created by mucl on 29/11/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String ACTION_DETAIL = "detail";
    public static final String ACTION_ERASE = "erase";
    public static final String POSITION_KEY = "position";




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String nTitle;
        String nMessage;
        String mId;
        int nBookPosition;

        ///////////// DATA RECEPTION /////////////////
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String,String> data = remoteMessage.getData();

        ///////////// DATA PROCESSING /////////////////
        if (notification.getTitle() != null) {
            nTitle = notification.getTitle();
        } else {
            nTitle = "BookApp Firebase Notification";
        }
        if (notification.getBody() != null) {
            nMessage = notification.getBody();
        } else {
            nMessage = "";
        }
        if (!data.isEmpty()) {
            nBookPosition = Integer.parseInt(data.get(POSITION_KEY));
        } else {
            nBookPosition = -1;
        }
        /////////////// SENDING THE NOTIFICATION ////////////
        sendNotification(0,nTitle,nMessage,nBookPosition);
    }

    private void sendNotification(int id, String title, String messageBody, int position) {

        BookContent.BookItem bookItem;


        //obtenim el book a partir de la posicio
        bookItem = BookContent.getBooks().get(position);
        if (messageBody.length() > 0) messageBody.concat("\n");
        String message = messageBody.concat(bookItem.getTitle());


        ///////////////// INTENTS  /////////////////////
        // Creem un intent per quan vulguem veure el detall del llibre
        Intent detailIntent = new Intent(this, BookListActivity.class);
        detailIntent.setAction(ACTION_DETAIL);
        detailIntent.putExtra(POSITION_KEY, position);
        PendingIntent piDetail = PendingIntent.getActivity(this,(int) System.currentTimeMillis(),detailIntent,0);


        //Creem un intent per quan vulguem borrar el llibre de la based dades local.
        Intent eraseIntent = new Intent(this, BookListActivity.class);
        eraseIntent.setAction(ACTION_ERASE);
        PendingIntent piErase = PendingIntent.getActivity(this,(int) System.currentTimeMillis(),eraseIntent,0);
        ///////////////////////////////////////////////

        //Es crea un so per la notificacio
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        /////////////////  NOTIFICATION BUILDER //////////
        //Es crea el builder de la notificació.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                //Configurem la icona de la barra d'estat
                .setSmallIcon(R.drawable.ic_new_releases_black_24dp)
                //Titol de la notificacio visible quan esta extesa.
                .setContentTitle(title)
                //Text de la notificacio visible quan esta extesa.
                //.setContentText(messageBody)
                //Indiquem a la notificació que es pot tancar despres de presionar-la
                .setAutoCancel(true)
                //Asigna el so que hem creat anteriorment.
                .setSound(defaultSoundUri)

                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(messageBody))
                .addAction(new NotificationCompat.Action(R.drawable.ic_delete_black_24dp,"Eliminar",piErase))
                .addAction(new NotificationCompat.Action(R.drawable.ic_search_black_24dp,"Veure",piDetail));
        ////////////////////////////////////////////////////

        //Es crea una instancia del gestor de notificacions.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //S'executa la notificació.
        notificationManager.notify(id, notificationBuilder.build());
    }



}
