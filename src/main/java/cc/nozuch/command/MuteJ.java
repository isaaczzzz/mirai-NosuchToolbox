package cc.nozuch.command;

import cc.nozuch.ToolboxMain;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.contact.Member;

public class MuteJ extends JSimpleCommand {

    public static final MuteJ INSTANCE = new MuteJ();

    private MuteJ() {
        super(ToolboxMain.INSTANCE, "mute", "禁言");
        this.setDescription("禁言某位群成员");
    }

    @Handler
    public void handle(CommandSender sender, Member target, int duration) {
        int secDuration = duration * 60;
        target.mute(secDuration);
    }
}
