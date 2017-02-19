package com.piotrkostecki.smarttravelpoznan.data.net;

import android.support.annotation.Nullable;
import android.util.Log;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements {@link java.util.concurrent.Callable} so when executed asynchronously can
 * return a value.
 */
public class ApiConnection implements Callable<String> {

    private URL url;
    private String response;
    private String method;
    private String content;

    private ApiConnection(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public static ApiConnection createGET(String url) throws MalformedURLException {
        return new ApiConnection(url);
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response
     */
    @Nullable
    public String requestSyncCall(String method, String content) {
        this.method = method;
        this.content = content;
        connectToApi();
        return response;
    }

    private void connectToApi() {
        OkHttpClient okHttpClient = this.createClient();
        RequestBody formData = new FormBody.Builder()
                .add("method", method)
                .add("p0", content)
                .build();
        String postBodyString = bodyToString(formData);
        final Request request = new Request.Builder()
                .url(this.url)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString))
                .build();

        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OkHttpClient createClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(15000, TimeUnit.MILLISECONDS)
                .build();
    }

    public static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }


    @Override public String call() throws Exception {
        return requestSyncCall(method, content);
    }
}
