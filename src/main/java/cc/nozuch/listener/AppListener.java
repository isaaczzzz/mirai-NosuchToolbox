package cc.nozuch.listener;

import cc.nozuch.config.Info;
import cc.nozuch.utils.AppUtil;
import cc.nozuch.utils.BilibiliUrl;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageSource;

import java.io.IOException;

public class AppListener extends SimpleListenerHost {

    /**
     * @Description 小程序转为链接并提取信息
     * @param event
     */
    @EventHandler
    private void onAppMessage(GroupMessageEvent event) {
        Group group = event.getGroup();
        MessageChain messageChain = event.getMessage();
        String senderName = event.getSenderName();
        String miraiCode = messageChain.serializeToMiraiCode();
        String plainText = messageChain.contentToString();

        if (miraiCode.contains(Info.APP_HEADER)) {
            try {
                MessageChain message = new AppUtil().AppParseToUtil(event.getSubject(), plainText, senderName);
                if (!message.isEmpty()) {
                    group.sendMessage(message);
                    //撤回原消息
                    MessageSource.recall(messageChain);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @Description 解析含BV号链接
     * @param event
     */
    @EventHandler
    private void onBVMessage(GroupMessageEvent event) {
        Group group = event.getGroup();
        MessageChain messageChain = event.getMessage();
        String senderName = event.getSenderName();
        String miraiCode = messageChain.serializeToMiraiCode();
        String plainText = messageChain.contentToString();

        if (plainText.contains("bilibili.com/video/BV")) {
            try {
                MessageChain message = new BilibiliUrl().BilibiliUrlToUtil(event.getSubject(), plainText, senderName);
                group.sendMessage(message);
                //撤回原消息
                MessageSource.recall(messageChain);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
