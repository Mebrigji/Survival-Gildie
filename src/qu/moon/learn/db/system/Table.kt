package qu.moon.learn.db.system

import qu.moon.learn.db.DataService
import java.sql.PreparedStatement

class Table(val name:String, val db:DataService) {

    var key: String = ""

    val values:MutableList<Any> = mutableListOf()

    fun update(map:Map<String, Any>){
        if(!db.availableConnection()) return

        var s:String = "insert into $name (|values|) values (|?|) on duplace key update |update|"

        var a:String = ""
        var b:String = ""
        var c:String = ""

        val objects:MutableList<Any> = mutableListOf()

        for (key in map.keys) {
            a += ",`$key`"
            b += ", ?"
            c += ", $key=values($key)"
            map[key]?.let { objects.add(it) }
        }

        a = a.replaceFirst(",", "")
        b = b.replaceFirst(",?", "")
        c = c.replaceFirst(", ", "")

        s = s.replace("|values|", a)
            .replace("|?|", b)
            .replace("|update|", c)

        val prepareStatement: PreparedStatement = db.prepareStatement(s) ?: return

        var id:Int = 0
        objects.forEach { any ->
           id++
           prepareStatement.setObject(id, any)
        }

        prepareStatement.execute()
    }

    fun create(tableConstructors: Array<TableConstructor>): Table{

        if(!db.availableConnection()) return this

        var ps:String = "create table if not exists $name (|values|primary key(|pkey|));"

        val builder:StringBuilder = StringBuilder()
        tableConstructors.forEach { tableConstructor ->
            run {
                builder.append(tableConstructor.name)
                    .append(" ")
                    .append(tableConstructor.elementType.name.toLowerCase())
                    .append(" (")
                    .append(tableConstructor.length_1)
                if (tableConstructor.length_2 != -1) builder.append(",").append(tableConstructor.length_2)
                builder.append(")").append(", ")
                if(tableConstructor.key) {
                    this.key = tableConstructor.name
                    ps = ps.replace("|pkey|", tableConstructor.name)
                }
            }
        }

        ps = ps.replace("|values|", builder.toString())
        db.execute(ps)

        return this
    }

}