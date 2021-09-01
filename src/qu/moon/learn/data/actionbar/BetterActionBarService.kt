package qu.moon.learn.data.actionbar

class BetterActionBarService {

    private val actionBarList: MutableList<BetterActionBar> = mutableListOf()

    fun getActionBarList(): List<BetterActionBar>{
        return actionBarList
    }

    fun findPlayerActionBar(name:String): BetterActionBar{
        return actionBarList.stream()
            .filter { better -> better.name.equals(name, true) }
            .findFirst()
            .orElse(register(name))
    }

    fun register(name:String): BetterActionBar{
        val action: BetterActionBar = BetterActionBar(name)
        actionBarList.add(action)
        return action
    }

}