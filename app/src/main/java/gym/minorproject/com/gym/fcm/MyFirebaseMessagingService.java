/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gym.minorproject.com.gym.fcm;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import gym.minorproject.com.gym.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    Intent intent;

    public void onMessageReceived(RemoteMessage remoteMessage)
    {
//        Toast.makeText(MyFirebaseMessagingService.this, "Message Received", Toast.LENGTH_SHORT).show();

        if (remoteMessage == null)
        {

            return;
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null)

        {
            handleNotification(remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0)
        {
            Log.i( "Data Payload: " ,remoteMessage.getData().toString());
            Log.i("size", String.valueOf(remoteMessage.getData().size()));

            try
            {
                JSONObject json = new JSONObject(remoteMessage.getData());
                Log.i("TRY","inTry");
                handleDataMessage(json);
            }
            catch (Exception e)
            {
                Log.i( "Exception: " , e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json)
    {
        try
        {
            String message = json.getString("message");
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("From Apna Server")
                    .setContentText(message);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(101, notificationBuilder.build());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void handleNotification(String message)
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("From Firebase")
                .setContentText(message);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(101, notificationBuilder.build());

    }
}