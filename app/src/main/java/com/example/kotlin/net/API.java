package com.example.kotlin.net;



import androidx.lifecycle.LiveData;

import com.andrew.library.model.BaseResponse;
import com.andrew.library.net.RetrofitHelper;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    //    String URL_ROOT_LOCAL = "http://192.168.124.117";//测试环境
    String URL_ROOT_LOCAL = "https://192.168.124.108";//乔
    String URL_ROOT_TEST = "https://lwtest.yibaobt.com";//测试环境
    String URL_ROOT_PRD = "https://lw.eyongtech.com";//正式环境

    String URL_ROOT = URL_ROOT_TEST;

    String API_URL = URL_ROOT + "/";


    class Builder {
        public static API getDefaultService() {
            return RetrofitHelper.getInstance().getBuilder(API_URL).build().create(API.class);
        }
    }

    @POST("api/user/json")
    LiveData<BaseResponse> login(@Body RequestBody requestBody);//账号密码登录

}
