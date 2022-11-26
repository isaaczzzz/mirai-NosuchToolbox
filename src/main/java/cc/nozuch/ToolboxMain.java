package cc.nozuch;

import cc.nozuch.command.BasicCommands;
import cc.nozuch.listener.AppListener;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

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
        //注册事件监听
        channel.registerListenerHost(new AppListener());
        //注册指令
        //还没有找到更好的遍历方法555
        CommandManager.INSTANCE.registerCommand(BasicCommands.Mute.INSTANCE, false);
        //CommandManager.INSTANCE.registerCommand(BasicCommands.Unmute.INSTANCE, false);
    }
}