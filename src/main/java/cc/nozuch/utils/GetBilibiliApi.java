package cc.nozuch.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GetBilibiliApi {
    public static final String API_HEADER = "https://api.bilibili.com/x/web-interface/view?bvid=BV";

    /**
     * 获取链接中的BV号
     * @param msg 含链接的消息
     * @return 不含前缀的BV号
     */
    public static String getBVid(String msg) {
        return msg.substring(msg.indexOf("/BV") + 3, msg.indexOf("/BV") + 13);
    }

    /**
     * 通过哔哩哔哩api获取视频信息
     * @param bvid 不含前缀的BV号
     * @return String类型视频json信息
     * @throws IOException 异常
     */
    public static String getApi(String bvid) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(API_HEADER + bvid)
                .addHeader("Connection", "keep-alive")
                .build();

        return Objects.requireNonNull(client.newCall(request).execute().body()).string();
    }
}
