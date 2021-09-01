package qu.moon.learn.data.user

import qu.moon.learn.data.drop.Drop

class User(val name:String) {

    var money:Double = 0.0
    var timePlayed:Long = 0

    val lastClaim: Long = System.currentTimeMillis()

    val cdrops:MutableList<Drop> = mutableListOf()

    fun canClaim(): Boolean{
        return lastClaim < (System.currentTimeMillis() + (1000 * 60 * 60 * 24))
    }

    fun whenMayClaim: Long{
        return lastClaim - (System.currentTimeMillis() + (1000 * 60 * 60 * 24))
    }

}