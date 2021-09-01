package qu.moon.learn.commands

import org.bukkit.command.CommandSender
import qu.moon.learn.systems.command.CommandInfo
import qu.moon.learn.systems.command.KotlinCommand

@CommandInfo(name = "pawel", usage = "/pawel", description = "rediex ma małego" , permission = "xd", aliases = ["t"])
class TestCommand : KotlinCommand() {

    override fun execute(commandSender: CommandSender, args: Array<out String>) {
        commandSender.sendColoredMessage("&7Piechu robi gałe")
    }
}