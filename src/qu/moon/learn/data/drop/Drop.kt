package qu.moon.learn.data.drop

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import qu.moon.learn.Main
import qu.moon.learn.helpers.SerializeHelper
import java.io.ByteArrayInputStream
import java.sql.ResultSet

class Drop() : SerializeHelper() {

    val id:Int = Main.getInstance().serviceManager
        .dropService.getDropList().size+1
    var name:String = ""
    var chance:Double = 0.0
    var amount_min: Int = 0
    var amount_max: Int = 0
    var exp_min: Int = 0
    var exp_max: Int = 0
    var fortune: Boolean = false
    var from: Material? = null
    var item: ItemStack? = null

    constructor(resultSet: ResultSet) : this() {
        name = resultSet.getString("name")

        val inputStream: ByteArrayInputStream = ByteArrayInputStream(Base64Coder.decode(resultSet.getString("serializeCode")))
        val dataInput: BukkitObjectInputStream = BukkitObjectInputStream(inputStream)

        this.name = dataInput.readUTF()
        this.chance = dataInput.readDouble()
        this.amount_min = dataInput.readInt()
        this.amount_max = dataInput.readInt()
        this.exp_min = dataInput.readInt()
        this.exp_max = dataInput.readInt()
        this.fortune = dataInput.readBoolean()
        this.from = Material.valueOf(dataInput.readUTF())
        this.item = BukkitObjectInputStream(ByteArrayInputStream(Base64Coder.decode(resultSet.getString("item")))).readObject() as ItemStack?

        inputStream.close()
        dataInput.close()

    }

    fun save(){
        val map:MutableMap<String, Any> = mutableMapOf()
        map["name"] = name
        map["chance"] = chance
        map["amount_min"] = amount_min
        map["amount_max"] = amount_max
        map["exp_min"] = exp_min
        map["exp_max"] = exp_max
        map["fortune"] = when (fortune) {
            true -> { 1
            }
            else -> { 0
            }
        }
        map["from"] = from!!.name
        map["item"] = item!!.serializeFromHelper()
        Main.getInstance().dataService.getTable("drops")!!.update(map)
    }

}