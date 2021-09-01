package qu.moon.learn.db

import qu.moon.learn.db.system.Table
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class DataService {

    private var tableList:MutableList<Table> = mutableListOf()

    private var connection: Connection? = null

    fun connect(host:String, username:String, password:String, table:String, port:Int, ssl:Boolean): DataService{

        connection = DriverManager.getConnection("jdbc:mysql://$host:$port/$table?useSSL=$ssl", username, password)

        return this
    }

    fun getConnection(): Connection? {
        return connection
    }

    fun execute(value:String){
        if(connection == null || connection!!.isClosed) return
        connection!!.prepareStatement(value).execute()
    }

    fun prepareStatement(value:String): PreparedStatement?{
        if(connection == null || connection!!.isClosed) return null
        return connection!!.prepareStatement(value)
    }

    fun availableConnection(): Boolean{
        return connection != null && !connection!!.isClosed
    }

    fun getTable(name:String): Table?{
        return tableList.stream().filter { table -> table.name == name }.findFirst().orElse(null)
    }

    fun <T> download(table:String, clazz: Class<T>): List<T>{
        if(!availableConnection()) return listOf()

        val result:ResultSet = prepareStatement("select * from $table")!!.executeQuery()

        val list: MutableList<T> = mutableListOf()
        while (result.next())
            list.add(clazz.getConstructor(ResultSet::class.java).newInstance(result)!!)
        result.close()

        return list
    }

    fun createTable(name:String): Table{
        val prevTable:Table? = getTable(name)
        if(prevTable != null) return prevTable
        val table:Table = Table(name, this)
        tableList.add(table)
        return table
    }

    fun deleteTable(table:Table, key:String){
        execute("delete from ${table.name} where `${table.key}`='$key'")
    }

}