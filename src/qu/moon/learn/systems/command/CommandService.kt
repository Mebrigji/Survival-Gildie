package qu.moon.learn.systems.command

import org.bukkit.Bukkit
import org.bukkit.command.SimpleCommandMap
import org.bukkit.plugin.SimplePluginManager
import java.lang.reflect.Field

class CommandService {

    private var commands: MutableList<KotlinCommand> = mutableListOf()

    fun getCommands(): List<KotlinCommand>{
        return commands;
    }

    fun getCommand(name: String): KotlinCommand {
        return commands.stream()
            .filter { cmd:KotlinCommand -> cmd.commandInfo!!.name.equals(name, true) }
            .findFirst()
            .orElse(null)
    }

    fun register(command: KotlinCommand) {
        if (commands.contains(command)) return

        commands.add(command)

        command.commandInfoFinder()
        if (command.commandInfo == null) return

        val simplePluginManager: SimplePluginManager = Bukkit.getPluginManager() as SimplePluginManager
        val field: Field = simplePluginManager.javaClass.getDeclaredField("commandMap")
        field.isAccessible = true

        val simpleCommandMap: SimpleCommandMap = field.get(simplePluginManager) as SimpleCommandMap

        simpleCommandMap.register(command.commandInfo!!.prefix, BukkitCommand(command))

    }

}