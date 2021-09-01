package qu.moon.learn.commands.admin

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import qu.moon.learn.systems.command.CommandInfo
import qu.moon.learn.systems.command.KotlinCommand

@CommandInfo(name = "gamemode",
    usage = "/gamemode [type] [optional(player)]",
    description = "changing player game mode",
    permission = "core.gamemode",
    aliases = ["gm"])
class GameModeCommand : KotlinCommand() {

    override fun execute(commandSender: CommandSender, args: Array<out String>) {
        if(args.isEmpty()) {
            commandSender.sendColoredMessage("&7Musisz podac poprawne &fID &7gamemoda.")
            return
        }
        val gameMode: GameMode = getGameMode(args[0])
        var target: Player? = commandSender as Player

        if(args.size == 2)
            target = Bukkit.getPlayer(args[1])

        if(target == null) {
            commandSender.sendColoredMessage("&7Musisz podac nick gracza online")
            return
        }

        target.gameMode = gameMode

        if(commandSender.name == commandSender.name){
            commandSender.sendColoredMessage("&7Twoj tryb gry zostal zmieniony na &f" + gameMode.name)
            return
        }

        commandSender.sendColoredMessage("&7Tryb gry gracza &f${target.name} &7zostal zmieniony na &f${gameMode.name}")
        target.sendColoredMessage("&7Twoj tryb gry zostal zmieniony na &f${gameMode.name}")

    }

    private fun getGameMode(value:String): GameMode{
        return GameMode.values()
            .toMutableList()
            .stream()
            .filter { mode:GameMode -> mode.name.equals(value, true)
                    || mode.value == value.toInt() }
            .findFirst().orElse(null)
    }
}