package qu.moon.learn.helpers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import qu.moon.learn.Main
import qu.moon.learn.data.actionbar.BetterActionBar
import qu.moon.learn.data.user.User
import java.text.DecimalFormat

open class Overwritten {

    fun CommandSender.castToPlayer(): Player {
        return this as Player
    }

    fun CommandSender.isPlayer(): Boolean{
        return this !is Player
    }

    fun List<String>.startWith(text:String): MutableList<String>{
        return MainHelper().startWith(this.toMutableList(), text)
    }

    fun Player.getUser(): User {
        return Main.getInstance().serviceManager.userService.findUser(this.name)
    }

    fun User.getPlayer(): Player? {
        return Bukkit.getPlayer(name)
    }

    fun Double.toString(format:String): String{
        return DecimalFormat(format).format(this)
    }

    fun Player.sendColoredMessage(text:String){
        sendMessage(ChatColor.translateAlternateColorCodes('&', text))
    }

    fun Player.sendColoredMessage(textList:List<String>){
        textList.forEach { m -> sendColoredMessage(m) }
    }

    fun CommandSender.sendColoredMessage(text:String){
        sendMessage(ChatColor.translateAlternateColorCodes('&', text))
    }

    fun CommandSender.sendColoredMessage(textList:List<String>){
        textList.forEach { m -> sendColoredMessage(m) }
    }

    fun Player.getActionBar(): BetterActionBar{
        return Main.getInstance()
            .serviceManager
            .betterActionBarService
            .findPlayerActionBar(name)
    }

    fun CommandSender.getMain(): Main{
        return Main.getInstance()
    }

    fun Player.getMain(): Main{
        return Main.getInstance()
    }

    fun Player.sendActionBar(id:String, text:String){
        getActionBar().addLine(id, text)
    }

}