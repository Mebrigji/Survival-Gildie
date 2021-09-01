package qu.moon.learn.helpers

import net.minecraft.server.v1_16_R3.Items.re
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.awt.SystemColor.text
import java.util.stream.Collectors

open class MainHelper {

    fun startWith(commands:MutableList<String>, text:String): MutableList<String>{
        return when(text.isEmpty() || commands.isEmpty()) {
            true -> mutableListOf()
            false -> {
                commands.filter { sb -> sb.regionMatches(0, text, 0, text.length, true) }.toMutableList()
            }
        }
    }

    fun translateColors(message: String): String {
        return ChatColor.translateAlternateColorCodes('&', message)
    }

    fun translateColors(messageList: List<String>): MutableList<String> {
        return messageList.stream()
            .map { message:String -> ChatColor.translateAlternateColorCodes('&', message)}
            .collect(Collectors.toList())
    }

    fun sendColorfulMessage(commandSender: CommandSender, message:String){
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
    }

    fun sendColorfulMessage(commandSender: CommandSender, messageList: List<String>){
        messageList.forEach { message:String ->
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message)) }
    }

    fun sendColorfulMessage(player: Player, message:String){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
    }

    fun sendColorfulMessage(player: Player, messageList:List<String>){
        messageList.forEach { message:String ->
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message))}
    }

}