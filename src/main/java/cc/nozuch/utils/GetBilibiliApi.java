package cc.nozuch.utils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GetBilibiliApi {
    public static final String BV_INFO_API_HEADER = "https://api.bilibili.com/x/web-interface/view?bvid=";
    public static final String AV_INFO_API_HEADER = "https://api.bilibili.com/x/web-interface/view?aid=";
    public static final String SHARE_API = "https://api.bilibili.com/x/share/click";
    public static final String VIDEO_HEADER = "https://www.bilibili.com/video/";

    /**
     * 获取链接中的BV号
     * @param msg 含链接的消息
     * @return 含前缀的BV号
     */
    public static String getBVid(String msg) {
        return msg.substring(msg.indexOf("/BV") + 1, msg.indexOf("/BV") + 13);
    }

    /**
     * 获取链接中的av号
     * @param msg 含链接的消息
     * @return 含前缀的av号
     */
    public static String getAVid(String msg) {
        String[] matches = msg.split("/+");
        String aid = "";
        for (String s : matches)
            if (s.startsWith("av"))
                aid = s.substring(2);
        return aid;
    }

    private static String getRandomBuvid() { return RandomStringUtils.randomAlphanumeric(32) + "infoc"; }

    /**
     * 通过哔哩哔哩api获取视频信息
     * @param url 视频链接
     * @return String类型视频json信息
     * @throws IOException 异常
     */
    public static String getInfoApi(String url) throws IOException {
        String finalUrl;
        if (url.contains("/av"))
            finalUrl = AV_INFO_API_HEADER + getAVid(url);
        else
            finalUrl = BV_INFO_API_HEADER + getBVid(url);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(finalUrl)
                .addHeader("Connection", "keep-alive")
                .build();

        return Objects.requireNonNull(client.newCall(request).execute().body()).string();
    }

    /**
     * 获取b23短连接的完整url
     * @param url 短链接
     * @return 完整url
     * @throws IOException 异常
     */
    public static String getFullUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive")
                .build();

        return Objects.requireNonNull(client.newCall(request).execute().request().url()).toString();
    }

    /**
     * 通过bilibili分享api获取b23短链接
     * @param bvid 含前缀的BV号
     * @return String类型视频分享json信息
     * @throws IOException 异常
     */
    public static String getShortUrl(String bvid) throws IOException{
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody requestbody = new FormBody.Builder()
                .add("build","6500300")
                .add("buvid", getRandomBuvid())
                .add("oid", VIDEO_HEADER + bvid)
                .add("platform", "android")
                .add("share_channel", "COPY")
                .add("share_id", "public.webview.0.0.pv")
                .add("share_mode", "3")
                .build();

        Request request = new Request.Builder()
                .url(SHARE_API)
                .post(requestbody)
                .addHeader("Connection", "keep-alive")
                .build();

        return Objects.requireNonNull(client.newCall(request).execute().body()).string();
    }
}
