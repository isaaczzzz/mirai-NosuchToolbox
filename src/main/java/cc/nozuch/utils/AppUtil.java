package cc.nozuch.utils;

import cc.nozuch.config.Info;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Objects;

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

        if (isInWhitelist(title)) {
            return new MessageChainBuilder().build();
        }

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

    /*
     * 判断app是否在白名单中
     */
    public boolean isInWhitelist(String name){
        File fileDir = new File("config"+File.separator+Info.PLUGIN_ID);
        File file = new File("config"+File.separator+Info.PLUGIN_ID+File.separator+"app_whitelist.json");

        //初始化
        if (!fileDir.exists()) {
            fileDir.mkdirs();
            String JSONString = "{\n" +
                    "  \"app\": [\"腾讯投票\"]\n" +
                    "}";
            try {
                file.createNewFile();
                Writer writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8);
                writer.write(JSONString);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String jsonString = null;
        try {
            jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject json = JSONObject.parseObject(jsonString);
        JSONArray appArray = json.getJSONArray("app");
        return appArray.contains(name);
    }
}
