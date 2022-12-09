package com.example.chattick.fragments;

import com.example.chattick.Notifications.MyResponse;
import com.example.chattick.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAMUxdaKI:APA91bHdvMX3Kb9uaFlccYL2n6ZxUvlBjNHGYYgTtJ1rybup06Bdm4Dl-1fkGjJN16MSMtP93bNYasPY9XEiUDu8iGbFDtuUr2tsZP4FvxotPG0UBXwjl6XJncfAFkisRB5ZOZ0OiXqc"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
