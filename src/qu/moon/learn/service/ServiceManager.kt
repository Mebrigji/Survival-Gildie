package qu.moon.learn.service

import qu.moon.learn.data.actionbar.BetterActionBarService
import qu.moon.learn.data.drop.DropService
import qu.moon.learn.data.user.UserService
import qu.moon.learn.systems.command.CommandService

class ServiceManager {

    val dropService: DropService = DropService()
    val commandService: CommandService = CommandService()
    val userService: UserService = UserService()
    val betterActionBarService: BetterActionBarService = BetterActionBarService()

}