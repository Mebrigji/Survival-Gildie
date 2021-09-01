package qu.moon.learn.systems.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import qu.moon.learn.helpers.MainHelper

class BukkitCommand(private val kotlinCommand: KotlinCommand) : Command(kotlinCommand.commandInfo!!.name) {


    init {
        kotlinCommand.commandInfoFinder()
        val info: CommandInfo = kotlinCommand.commandInfo!!
        permission = info.permission
        usage = info.usage
        description = info.description
        aliases = info.aliases.toMutableList()
    }

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {

        val info: CommandInfo = kotlinCommand.commandInfo!!

        val helper:MainHelper = MainHelper()

        if(info.equals(ExecutorType.CONSOLE) && sender is Player){
            helper.sendColorfulMessage(sender, "&cPodana komenda jest przeznaczona tylko dla konsoli")
            return false
        }

        if(info.equals(ExecutorType.PLAYER) && sender is ConsoleCommandSender){
            helper.sendColorfulMessage(sender, "&cPodana komenda jest przeznaczona tylko dla gracza")
            return false
        }

        if(permission == null || permission!!.isEmpty() || sender.hasPermission(permission!!))
            kotlinCommand.execute(sender, args)
        else helper.sendColorfulMessage(sender, "&cNie posiadasz uprawnien &4$permission &cdo użycia tej komendy")
        return true;
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        return kotlinCommand.tabComplete(sender, args)
    }

}