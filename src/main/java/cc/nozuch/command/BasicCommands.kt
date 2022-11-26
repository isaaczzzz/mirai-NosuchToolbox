package cc.nozuch.command

import cc.nozuch.ToolboxMain
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.events.MessageEvent

object BasicCommands {
    object Mute : SimpleCommand(
        ToolboxMain.INSTANCE,
        "mute",
        "禁言",
        description = "禁言某位群友"
    ) {
        @Handler
        suspend fun CommandSender.handle(target: Member, duration: Int) {
            val secDuration = duration * 60
            target.mute(secDuration)
        }
    }

//    object Unmute : SimpleCommand(
//        ToolboxMain.INSTANCE,
//        "unmute",
//        "解除禁言",
//        description = "解除某位群友的禁言"
//    ) {
//        @Handler
//        suspend fun CommandSenderOnMessage<MessageEvent>.handle(target: Member) {
//
//        }
//    }
}