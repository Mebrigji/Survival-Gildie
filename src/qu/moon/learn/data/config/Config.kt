package qu.moon.learn.data.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import qu.moon.learn.Main
import java.io.File
import java.io.InputStream

class Config(private val plugin:JavaPlugin) {

    private val dataList:MutableList<Data> = mutableListOf()

    var name:String = "config"

    private var configuration: FileConfiguration? = null
    private var file: File = File(plugin.dataFolder, "$name.yml")

    fun getConfiguration(): FileConfiguration?{
        return configuration
    }

    fun getDataList(): List<Data>{
        return dataList.toList()
    }

    // @Runtime

    private fun isFirstLaunch(): Boolean{
        return !file.exists()
    }

    private fun createFile(){
        file.parentFile.mkdirs()
        file.createNewFile()
    }

    fun save(){
        when(isFirstLaunch()){
            true -> {
                createFile()
                val inputStream: InputStream? = Main().getResource(name)
                configuration = YamlConfiguration.loadConfiguration(file)
            }
            false -> {
                if(configuration == null) {
                    configuration = YamlConfiguration.loadConfiguration(file)
                    return
                }
                dataList.forEach {data -> run { configuration!!.set(data.path, data.obj) } }
                configuration!!.save(file)
            }
        }
    }

    fun load(newConfiguration:Boolean){
        if(isFirstLaunch()) createFile()
        if(newConfiguration || configuration == null){
            configuration = YamlConfiguration.loadConfiguration(file)
        }
        dataList.clear()

        for (key in configuration!!.getKeys(true)) {
            val obj: Any? = configuration!!.get(key)
            register(Data(key, obj!!))
        }

    }

    //Getter adn setter

    fun set(path: String, obj: Any) {
        val data: Data = dataList.stream().filter { data -> data.path.equals(path, true) }.findFirst().orElse(null) ?: return
        data.obj = obj
    }

    fun <T> getOrCreate(path: String, obj: T): T{
        return dataList.toMutableList()
            .stream()
            .filter { data -> data.path.equals(path, true) }
            .findFirst()
            .orElse(register(Data(path, obj))).obj as T
    }

    private fun register(data:Config.Data): Data{
        this.dataList.add(data)
        return data
    }

    //Data

    data class Data(val path:String, var obj:Any?){

        fun isList(): Boolean{
            return obj !is Collection<*>
        }

    }

}