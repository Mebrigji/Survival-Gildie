package qu.moon.learn.data.actionbar

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.stream.Collectors

class BetterActionBar(val name: String) {

    var lines:MutableList<Line> = mutableListOf()
    var player: Player? = null

    fun isOnline(): Boolean{
        return player != null && player!!.isOnline
    }

    fun checkLines(){

        lines = lines.toMutableList()
            .stream()
            .filter { line -> !line.isSend }
            .collect(Collectors.toList())

    }

    fun addLine(id:String, text:String){
        val line:Line? = lines.stream()
            .filter { l -> l.id == id }
            .findFirst()
            .orElse(null)
        if(line != null){
            lines.remove(line)
        }
        lines.add(Line(id, text))
    }

    fun checkList(){
        this.lines = lines.stream().filter { line -> !line.isSend }.collect(Collectors.toList())
    }

    fun sendActionBar(){

        if(player == null) return

        val builder:StringBuilder = StringBuilder();

        lines.toMutableList().filter { line -> !line.isSend }.forEach { line ->
            run {
                line.isSend = true
                builder.append(line.text).append(" &8| &f")
            }
        }

        player!!.sendActionBar(ChatColor.translateAlternateColorCodes('&', builder.substring(0,
            0.coerceAtLeast(builder.length - 7)
        )))

    }

    data class Line(val id:String, val text:String, var isSend:Boolean = false)

}