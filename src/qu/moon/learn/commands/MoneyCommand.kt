package qu.moon.learn.commands

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import qu.moon.learn.data.user.User
import qu.moon.learn.systems.command.CommandInfo
import qu.moon.learn.systems.command.ExecutorType
import qu.moon.learn.systems.command.KotlinCommand
import java.text.DecimalFormat
import java.util.stream.Collectors

@CommandInfo(name = "money",
    usage = "/money [optional(player)]",
    description = "Checking, or paying money for player",
    permission = "",
    aliases = ["pieniadze"],
    executor = ExecutorType.PLAYER)
class MoneyCommand : KotlinCommand() {

    override fun tabComplete(sender: CommandSender, args: Array<out String>): MutableList<String> {
        if(args.size == 1)
            return Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .collect(Collectors.toList())
                .startWith(args[0])
        return super.tabComplete(sender, args)
    }

    override fun execute(commandSender: CommandSender, args: Array<out String>) {

        var player: Player? = commandSender.castToPlayer();
        if(args.isEmpty()){
            val user: User = player!!.getUser()
            player.sendColoredMessage("&7Posiadasz &f${user.money.toString("##.###,###")}&7$")
            return
        }

        if(args.size == 1){
            player = Bukkit.getPlayer(args[0])
        }

        if(player == null) {
            commandSender.sendColoredMessage("&7Podany gracz ${args[0]} &7jest offline")
            return
        }

        commandSender.sendColoredMessage("&7Gracz &f${player.name} &7posiada &f${player.getUser().money.toString("##.###,###")}")
    }
}