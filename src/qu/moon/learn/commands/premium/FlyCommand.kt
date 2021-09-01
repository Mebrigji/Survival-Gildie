package qu.moon.learn.commands.premium

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import qu.moon.learn.systems.command.CommandInfo
import qu.moon.learn.systems.command.KotlinCommand

@CommandInfo(name = "fly",
    usage = "/fly [optional(on, off, check)] [whenCheck(name)]",
    description = "Changing fly status",
    permission = "core.fly",
    aliases = ["latanie"])

class FlyCommand : KotlinCommand() {

    override fun tabComplete(sender: CommandSender, args: Array<out String>): MutableList<String> {
        if(args.size == 1){
            return mutableListOf("on", "off", "check").startWith(args[0])
        }
        return super.tabComplete(sender, args)
    }

    override fun execute(commandSender: CommandSender, args: Array<out String>) {
        if(args.isEmpty()) {
            if (commandSender.isPlayer()) {
                commandSender.sendColoredMessage(commandInfo!!.usage)
                return
            }
            val player: Player = commandSender.castToPlayer()
            player.allowFlight = !player.allowFlight
            player.sendColoredMessage("&7Latanie zostalo "
                        + when (player.allowFlight) {
                    true -> {
                        "&aWlaczone"
                    }
                    else -> {
                        "&cWylaczone"
                    }
                })
            return
        }
        if(args[0].equals("on", true) && commandSender.isPlayer()){
            val player:Player = commandSender.castToPlayer()
            if(player.allowFlight){
                player.sendColoredMessage("&7Latanie jest juz wlaczone")
                return
            }
            player.allowFlight = true
            player.sendColoredMessage("&7Latanie zostalo wlaczone")
        } else if(args[0].equals("off", true) && commandSender.isPlayer()){
            val player:Player = commandSender.castToPlayer()
            if(!player.allowFlight){
                player.sendColoredMessage("&7Latanie nie jest wlaczone")
                return
            }
            player.allowFlight = false
            player.sendColoredMessage("&7Latanie zostalo wylaczone")
        } else if(args[0].equals("check", true) && args.size == 2){
            val target: Player = Bukkit.getPlayer(args[1]) ?: return commandSender.sendColoredMessage("&7Musisz podac nick gracza online.")
            commandSender.sendColoredMessage(
                when (target.allowFlight) {
                    true -> {
                        "&7Gracz &f" + target.name + " &7ma wlaczone latanie"
                    }
                    else -> {
                        "&7Gracz &f" + target.name + " &7ma wylaczone latanie"
                    }
                }
            )
        } else commandSender.sendColoredMessage(commandInfo!!.usage)
    }
}