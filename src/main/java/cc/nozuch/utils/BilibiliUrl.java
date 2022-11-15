package cc.nozuch.utils;

import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.IOException;

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
        JSONObject json = JSONObject.parseObject(GetBilibiliApi.getApi(GetBilibiliApi.getBVid(msg)));
        JSONObject data = json.getJSONObject("data");
        // 视频标题
        String title = data.getString("title");
        // 视频简介
        String desc = data.getString("desc");
        // 视频av号
        String aid = data.getString("aid");
        // 视频图片url
        String pic = data.getString("pic");

        //获取封面图片
        ExternalResource er = ExternalResource.Companion.create(DownloadImage.getByteByUrl(pic));
        Image coverImg = ExternalResource.uploadAsImage(er, g);

        return new MessageChainBuilder()
                .append(new PlainText("[转发来自] " + sender))
                .append(coverImg)
                .append(new PlainText("\n[标题]" + title))
                .append(new PlainText("\n[简介]" + desc))
                .append(new PlainText("\n[哔哩哔哩] https://www.bilibili.com/video/" + aid + "/"))
                .build();
    }
}
