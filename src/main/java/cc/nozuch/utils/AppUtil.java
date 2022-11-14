package cc.nozuch.utils;

import com.alibaba.fastjson.JSONObject;

public class AppUtil {
    public String AppParseToUtil(String msg) {
        JSONObject json = JSONObject.parseObject(msg);
        JSONObject meta = json.getJSONObject("meta");
        JSONObject detail_1 = meta.getJSONObject("detail_1");
        String desc = detail_1.getString("desc");
        String preview = detail_1.getString("preview");
        String url = detail_1.getString("qqdocurl");
        String title = detail_1.getString("title");
        return preview + "\n内容: "+ desc +"\n" + title+": "+ url;
    }
}
