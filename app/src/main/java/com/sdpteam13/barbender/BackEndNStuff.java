package com.sdpteam13.barbender;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BackEndNStuff extends AppCompatActivity {

    public static final String TAG = BackEndNStuff.class.getSimpleName();

    public static ExecutorService executor = Executors.newSingleThreadExecutor();

   static String post(String url, String seat,String spirit,String mixer,String token) throws IOException {
        final String[] responseResult = {""};
        OkHttpClient client = new OkHttpClient();

        // new methode to post with okhttp
        RequestBody requestBody = new MultipartBody.Builder()
               .setType(MultipartBody.FORM)
               .addFormDataPart("seat", seat)
                .addFormDataPart("spirit", spirit)
                .addFormDataPart("mixer",mixer)
                .addFormDataPart("token",token)
               .build();

        // Internet said it is outdated
        //RequestBody body = new FormBody.Builder()
        //        .add("seat",command)
        //        .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"Could not connect to url");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"Success");
                responseResult[0] = response.body().string();
            }
        });
        return responseResult[0];
    }

    static String getSalt(String url, String username) throws IOException {
        //final String asd = "";
        final OkHttpClient client = new OkHttpClient();

        // new methode to post with okhttp
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {

                    Response response = client.newCall(request).execute();
                    return response.body().string();

                }catch (IOException e)
                {
                    Log.e(TAG,"Failed to get the salt");
                    e.printStackTrace();
                    return "None";
                }
            }
        };

        try {

            Future<String> future = executor.submit(callable);
            Log.d(TAG, "The salt from the server is: "+future.get());
            return future.get();

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "Server did not respond");
            return "None";
        }
    }

    static String logIn(String url, String username, String password) throws IOException {
        final OkHttpClient client = new OkHttpClient();

        // new methode to post with okhttp
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {

                    Response response = client.newCall(request).execute();
                    return response.body().string();

                }catch (IOException e)
                {
                    Log.e(TAG,"Login failed");
                    e.printStackTrace();
                    return "None";
                }
            }
        };

        try {

            Future<String> future = executor.submit(callable);
            Log.d(TAG, "The login result from the server is: "+future.get());
            return future.get();

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.d(TAG, "Server did not respond");
            return "None";
        }
    }

    static String register(String url,String username, String password,String salt) throws IOException {
        final String[] responseResult = new String[1];
        //final String asd = "";
        final OkHttpClient client = new OkHttpClient();

        // new methode to post with okhttp
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .addFormDataPart("salt", salt)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {

                    Response response = client.newCall(request).execute();
                    return response.body().string();

                }catch (IOException e)
                {
                    Log.e(TAG,"register failed");
                    e.printStackTrace();
                    return "None";
                }
            }
        };

        try {

            Future<String> future = executor.submit(callable);
            Log.d(TAG, "The register result from the server is: "+future.get());
            return future.get();

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.d(TAG, "Server did not respond");
            return "None";
        }
    }
}
