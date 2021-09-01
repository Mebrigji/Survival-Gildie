package qu.moon.learn.listeners

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import qu.moon.learn.data.actionbar.BetterActionBar
import qu.moon.learn.data.user.User
import qu.moon.learn.helpers.Overwritten

class PlayerListener : Listener, Overwritten() {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        event.joinMessage = ""
        val player: Player = event.player
        val action: BetterActionBar = player.getActionBar()
        action.player = player
        action.addLine("welcome", "&7Witaj &l${player.name} &7na &fwolfmc.pl &7na serwerze jest &f${Bukkit.getOnlinePlayers().size} &7graczy.")

        val user: User = event.player.getUser()
        if(user.canClaim()) user.claim(consumer = {
            user.money += 20
        })
    }
}