package qu.moon.learn.commands.admin

import org.bukkit.command.CommandSender
import qu.moon.learn.Main
import qu.moon.learn.systems.command.CommandInfo
import qu.moon.learn.systems.command.KotlinCommand

@CommandInfo(name = "config",
    usage = "/config [save, load]",
    description = "manage config",
    aliases = ["cfg"])
class ConfigCommand : KotlinCommand() {
    override fun execute(commandSender: CommandSender, args: Array<out String>) {
        if(args.isEmpty()){
            commandSender.sendMessage(commandInfo!!.usage)
            return
        }
        if(args[0].equals("save", true)){
            Main.getInstance().kotlinConfig.save()
            commandSender.sendColoredMessage("&7Plik konfiguracyjny &f${Main.getInstance().kotlinConfig.name} &7zostal pomyslnie zapisany")
        } else if(args[0].equals("load", true)){
            Main.getInstance().kotlinConfig.load(false)
            commandSender.sendColoredMessage("&7Plik konfiguracyjny &f${Main.getInstance().kotlinConfig.name} &7zostal pomyslnie zaladowany.")
        } else commandSender.sendColoredMessage(commandInfo!!.usage)
    }
}