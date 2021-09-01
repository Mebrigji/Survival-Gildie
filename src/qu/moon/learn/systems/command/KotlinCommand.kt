package qu.moon.learn.systems.command

import org.bukkit.command.CommandSender
import qu.moon.learn.Main
import qu.moon.learn.helpers.Overwritten
import qu.moon.learn.service.ServiceManager

abstract class KotlinCommand : Overwritten(){

    fun getService(): ServiceManager {
        return Main.getInstance().serviceManager
    }

    var commandInfo: CommandInfo? = null;

    fun commandInfoFinder(){
        this.commandInfo = this.javaClass.getDeclaredAnnotation(CommandInfo::class.java)
    }

    abstract fun execute(commandSender: CommandSender, args: Array<out String>)

    open fun tabComplete(sender: CommandSender, args: Array<out String>): MutableList<String> {
        return args.toMutableList()
    }

}
