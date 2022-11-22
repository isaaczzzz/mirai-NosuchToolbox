package cc.nozuch.utils;

import com.alibaba.fastjson.JSONObject;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BilibiliUrl {
    /**
     * 解析哔哩哔哩的链接
     * @param g 群组
     * @param msg 含链接的消息
     * @param sender 发送者
     * @return 解析后的消息
     * @throws IOException 异常
     */
    public MessageChain BilibiliUrlToUtil(Group g, String msg, String sender) throws IOException {
        String foundUrl = parser(msg);
        if (foundUrl.contains("b23.tv/"))
            foundUrl = GetBilibiliApi.getFullUrl(foundUrl);

        JSONObject json = JSONObject.parseObject(GetBilibiliApi.getInfoApi(foundUrl));
        JSONObject data = json.getJSONObject("data");
        JSONObject stat = data.getJSONObject("stat");
        // 视频标题
        String title = data.getString("title");
        // 视频简介
        String desc = data.getString("desc");
        // 获取投稿人
        String ownerName = data.getJSONObject("owner").getString("name");
        // 投稿时间
        String ctime = timestampToDate(data.getLong("ctime"));
        // 视频分区名
        String tname = data.getString("tname");
        // 视频av号
        String aid = data.getString("aid");
        // 视频BV号
        String bvid = data.getString("bvid");
        // 视频图片url
        String pic = data.getString("pic");
        // 视频获赞数
        String like = stat.getString("like");
        // 视频投币数
        String coin = stat.getString("coin");
        // 视频收藏数
        String favorite = stat.getString("favorite");
        // 视频分享数
        String share = stat.getString("share");

        // 获取封面图片
        ExternalResource er = ExternalResource.Companion.create(DownloadImage.getByteByUrl(pic));
        Image coverImg = ExternalResource.uploadAsImage(er, g);

        // 获取b23短链接
        JSONObject b23json = JSONObject.parseObject(GetBilibiliApi.getShortUrl(bvid));
        String b23url = b23json.getJSONObject("data").getString("content");

        return new MessageChainBuilder()
                .append(new PlainText("[转发来自] " + sender))
                .append(new PlainText("\n" + bvid + "    av" + aid))
                .append(coverImg)
                .append(new PlainText("\n" + title))
                .append(new PlainText("\n" + b23url))
                .append(new PlainText("\n\n[投稿人] " + ownerName))
                .append(new PlainText("\n[分区] " + tname))
                .append(new PlainText("\n[投稿时间] " + ctime))
                .append(new PlainText("\n[简介]\n" + desc))
                .append(new PlainText("\n\n[互动数据]"))
                .append(new PlainText("\n获赞：" + like + "  投币：" + coin + "  收藏：" + favorite + "  分享：" + share))
                .build();
    }

    private static String parser(String text) {
        UrlDetector parser = new UrlDetector(text, UrlDetectorOptions.Default);
        return parser.detect().get(0).toString();
    }

    private static String timestampToDate(long timestamp) {
        long times = timestamp * 1000L;
        Date date = new Date(times);
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return df.format(date);
    }
}
