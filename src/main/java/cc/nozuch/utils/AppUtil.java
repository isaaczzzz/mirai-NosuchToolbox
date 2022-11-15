package cc.nozuch.utils;

import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.IOException;

public class AppUtil {
    public MessageChain AppParseToUtil(Group g, String msg, String sender) throws IOException {

        JSONObject json = JSONObject.parseObject(msg);
        JSONObject meta = json.getJSONObject("meta");
        JSONObject detail_1 = meta.getJSONObject("detail_1");
        //内容：视频标题，微博内容之类的
        String desc = detail_1.getString("desc");
        //图片url
        String preview = detail_1.getString("preview");
        //链接
        String url = detail_1.getString("qqdocurl");
        //App名称
        String title = detail_1.getString("title");

        //获取preview中的图片
        ExternalResource er = ExternalResource.Companion.create(DownloadImage.getByteByUrl(preview));
        Image previewImg = ExternalResource.uploadAsImage(er, g);

        return new MessageChainBuilder()
                .append(new PlainText("[转发来自] "+sender))
                .append(previewImg)
                .append(new PlainText("\n[内容]【"+desc+"】"))
                .append(new PlainText("\n["+title+"] "+url))
                .build();
    }
}
