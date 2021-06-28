package com.andrew.library.utils;

import android.os.Environment;
import android.text.Html;
import android.text.Spanned;

public interface ConstantsUtil {
    String TOKEN = "token";
    int REQUEST_CODE_100 = 100;
    int REQUEST_CODE_101 = 101;
    int REQUEST_CODE_102 = 102;
    int REQUEST_CODE_103 = 103;
    int REQUEST_CODE_104 = 104;
    int REQUEST_CODE_105 = 105;
    int REQUEST_CODE_106 = 106;
    int REQUEST_CODE_SEX = 201;
    int REQUEST_CODE_EMAIL = 202;
    int REQUEST_CODE_ADDRESS = 203;
    int REQUEST_CODE_VIDEO = 400;
    int REQUEST_CODE_IMAGE = 401;
    int REQUEST_CODE_FILE = 402;
    int REQUEST_CODE_CAMERA = 500;
    int REQUEST_CODE_ALBUM = 501;
    int REQUEST_CODE_PICTURE_CROP = 502;

    int IMAGE_TYPE_DEFAULT = 600;//普通图片
    int IMAGE_TYPE_IDENTITY = 601;//身份证
    int IMAGE_TYPE_BUSINESS_CERTIFICATE = 602;//营业执照


    int PAGE_SIZE = 20;

    String INTENT_KEY1 = "ik1";
    String INTENT_KEY2 = "ik2";
    String INTENT_KEY3 = "ik3";
    String INTENT_KEY4 = "ik4";


    String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    String SDCARD_TS = SDCARD_ROOT + "/task/";
    String SDCARD_APP_ALBUM = SDCARD_TS + "album/";
    String SDCARD_CROP = SDCARD_TS + "crop/";
    String SDCARD_AUDIO = SDCARD_TS + "Audio";


}
