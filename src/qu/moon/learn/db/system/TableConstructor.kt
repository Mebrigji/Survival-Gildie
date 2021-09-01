package qu.moon.learn.db.system

class TableConstructor(val name: String, val elementType: ElementType, val length_1:Int, val length_2: Int = -1, val key:Boolean = false) {

    constructor(name: String, elementType: ElementType, length_1: Int, key: Boolean)
            : this(name, elementType, length_1, -1, key)

    constructor(name: String, elementType: ElementType, length_1: Int)
            : this(name, elementType, length_1, -1, false)

}