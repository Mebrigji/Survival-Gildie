package qu.moon.learn.systems.command

annotation class CommandInfo(val name: String,
                             val usage: String,
                             val description: String,
                             val permission: String = "",
                             val aliases: Array<String>,
                             val prefix: String = "kco",
                             val executor: ExecutorType = ExecutorType.ALL)

enum class ExecutorType {
    PLAYER,
    CONSOLE,
    ALL
}


