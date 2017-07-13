package http;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/6.
 */

public class Httputil {
    public static void downLoad(String url,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(build).enqueue(callback);
    }
}
