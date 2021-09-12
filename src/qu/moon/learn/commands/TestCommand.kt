package qu.moon.learn.commands

import org.bukkit.command.CommandSender
import qu.moon.learn.systems.command.CommandInfo
import qu.moon.learn.systems.command.KotlinCommand

@CommandInfo(name = "test", usage = "/test", description = "rediex to robił" , permission = "", aliases = ["t"])
class TestCommand : KotlinCommand() {

    override fun execute(commandSender: CommandSender, args: Array<out String>) {
        commandSender.sendColoredMessage("&7Nic tu nie ma")
    }
}
