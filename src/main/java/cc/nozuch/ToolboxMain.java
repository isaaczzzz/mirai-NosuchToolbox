package cc.nozuch;

import cc.nozuch.utils.AppUtil;
import cc.nozuch.utils.BilibiliUrl;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class ToolboxMain extends JavaPlugin {
    public static final String APP_HEADER = "[mirai:app";

    public static final ToolboxMain INSTANCE = new ToolboxMain();

    private ToolboxMain() {
        super(new JvmPluginDescriptionBuilder("cc.nozuch.toolbox", "0.1.0")
                .name("NosuchToolbox")
                .author("isaac")
                .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        getLogger().info("Toolbox Plugin loading...");
    }

    @Override
    public void onEnable() {
        MiraiLogger logger = getLogger();
        logger.info("Plugin loaded!");

        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        eventChannel.subscribeAlways(GroupMessageEvent.class, event -> {
            Group group = event.getGroup();
            MessageChain messageChain = event.getMessage();
            String senderName = event.getSenderName();

            //获取mirai code
            String miraiCode = messageChain.serializeToMiraiCode();
            //获取纯文本
            String plainText = messageChain.contentToString();
//            logger.info("MiraiCode: "+miraiCode);
            logger.info("PlainText: "+plainText);

            /*
             * 小程序转为链接
             */
            if (miraiCode.contains(APP_HEADER)) {
                try {
                    MessageChain message = new AppUtil().AppParseToUtil(event.getSubject(), plainText, senderName);
                    group.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //撤回原消息
                MessageSource.recall(messageChain);
            }

            /*
             * 解析BV号
             */
            if (plainText.contains("bilibili.com/video/BV")) {
                try {
                    MessageChain message = new BilibiliUrl().BilibiliUrlToUtil(event.getSubject(), plainText, senderName);
                    group.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //撤回原消息
                MessageSource.recall(messageChain);
            }
        });
    }
}