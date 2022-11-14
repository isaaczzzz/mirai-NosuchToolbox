package cc.nozuch.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DownloadImage {
    /**
     * 使用okhttp，通过url获取byte文件
     * @param url
     * @return
     * @throws IOException
     */
    public static final String HTTPS_HEADER = "https://";
    public static byte[] getUrlByByte(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        String finalUrl;
        //检查url是否有http头
        if (!url.startsWith(HTTPS_HEADER)) {
            finalUrl = HTTPS_HEADER + url;
        } else {
            finalUrl = url;
        }

        Request request = new Request.Builder()
                .url(finalUrl)
                .addHeader("Connection", "keep-alive")
                .build();

        return Objects.requireNonNull(client.newCall(request).execute().body()).bytes();
    }
}
