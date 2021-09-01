package qu.moon.learn.data.drop

class DropService {

    private val dropList:MutableList<Drop> = mutableListOf()

    fun getDropList(): MutableList<Drop>{
        return dropList
    }

    fun unregister(drop:Drop){
        dropList.remove(drop)
    }

    fun getDrop(id:Int): Drop{
        return dropList.stream()
            .filter { drop -> drop.id == id }
            .findFirst()
            .orElse(null)
    }

    fun register(drop:Drop){
        if(dropList.contains(drop)) return
        dropList.add(drop)
    }

}