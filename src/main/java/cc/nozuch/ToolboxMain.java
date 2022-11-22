package cc.nozuch;

import cc.nozuch.listener.AppListener;
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

    public static final ToolboxMain INSTANCE = new ToolboxMain();

    private ToolboxMain() {
        super(new JvmPluginDescriptionBuilder("cc.nozuch.toolbox", "0.1.1")
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

        EventChannel<Event> channel = GlobalEventChannel.INSTANCE.parentScope(this);
        channel.registerListenerHost(new AppListener());

    }
}