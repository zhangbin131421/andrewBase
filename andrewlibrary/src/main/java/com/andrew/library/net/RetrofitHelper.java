package com.andrew.library.net;


import android.text.TextUtils;

import com.andrew.library.net.interceptor.Level;
import com.andrew.library.utils.SpUtil;
import com.orhanobut.logger.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;

public class RetrofitHelper {
    private static RetrofitHelper instance;
    private static final int DEFAULT_TIME_OUT = 25;
    private static final int DEFAULT_READ_TIME_OUT = 0;

    private RetrofitHelper() { }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }


    private OkHttpClient getOkClient() {
//        LoggingInterceptor logging = new LoggingInterceptor();
//        logging.setDebug(BuildConfig.DEBUG);
//        logging.setLevel(Level.BASIC);
//        logging.setType(Platform.INFO);
//        logging.setRequestTag("Request");
//        logging.setResponseTag("Response");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), new
                    X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    })//配置
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier());//配置
        }

        return builder.retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.MILLISECONDS)
//                .followRedirects(false)
//                .cache(new Cache(new File("sdcard/cache","okhttp"),1024))
//                .addNetworkInterceptor(logging)
                .addInterceptor(new RequestInterceptor())
//                .addInterceptor(new RespInterceptor())
                .build();
    }

    public Retrofit.Builder getBuilder(String apiUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient());
        builder.baseUrl(apiUrl);
        builder.addConverterFactory(MyGsonConverterFactory.create());
        builder.addCallAdapterFactory(new LiveDataCallAdapterFactory());
        return builder;
    }

    /**
     * 根据Response，判断Token是否失效
     */
//    private void isTokenExpired(Response response) {
//        try {
//            String body = response.body().string();
//            JSONObject object = new JSONObject(body);
//            String message = object.getString("message");
//            int code = object.getInt("code");
//            if (code == 401 || message.contains("token")) {
//                BaseApplication.instance.jumpLoginActivity();
//            }
//        } catch (IOException | JSONException e) {
//            Logger.e(e.getMessage());
//        }
//
//    }

    private static class RequestInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request originalRequest = chain.request();//获取原始请求
            Request.Builder requestBuilder = originalRequest.newBuilder() //建立新的请求
//                    .header("Connection", "close")
                    .addHeader("Connection", "close")
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json; charset=utf-8")
//                    .addHeader("filePlatform", "task")
                    .removeHeader("User-Agent")
//                    .addHeader("User-Agent", BaseUtils.getUserAgent())
                    .method(originalRequest.method(), originalRequest.body());

            String token = SpUtil.getInstance().getToken();
            if (TextUtils.isEmpty(token)) {
                return chain.proceed(requestBuilder.build());
            }
//            tokenRequest = requestBuilder.header("Authorization", Credentials.basic("token", "")).build();
            Request tokenRequest = requestBuilder.header("token", token)
//                    .header("companyId", SpUtil.getInstance().getCurrentCompanyId())
                    .build();
            return chain.proceed(tokenRequest);
        }
    }

//    private class RespInterceptor implements Interceptor {
//        @NotNull
//        @Override
//        public Response intercept(@NotNull Chain chain) throws IOException {
//            Request request = chain.request();
//            Response response = chain.proceed(request);
//            if (response.code() == 500) {
////                isTokenExpired(response);
//            }
////            Logger.d("response.code()= " + response.code());
////            Logger.d("response.body()= " + response.body().string());
//            return response;
//        }
//    }

}
