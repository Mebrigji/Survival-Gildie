package qu.moon.learn.helpers

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayOutputStream

open class SerializeHelper {

    fun ItemStack.serializeFromHelper(): String{

        val outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
        val stream: BukkitObjectOutputStream = BukkitObjectOutputStream(outputStream)

        stream.writeObject(this)

        stream.close()
        outputStream.close()

        return Base64Coder.encodeLines(outputStream.toByteArray())

    }
    //@SneakyThrows
    //    public static String itemStackToString(ItemStack[] inventory) {
    //
    //        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    //        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
    //        dataOutput.writeInt(inventory.length);
    //
    //        for (ItemStack itemStack : inventory) {
    //            dataOutput.writeObject(itemStack);
    //        }
    //
    //        dataOutput.close();
    //        return Base64Coder.encodeLines(outputStream.toByteArray());
    //    }
    //
    //    @SneakyThrows
    //    public static ItemStack[] itemStackFromString(String data) {
    //        if(data == null) return null; // pozdro dla kumatych XDD
    //        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
    //        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
    //        int length = dataInput.readInt();
    //        ItemStack[] items = new ItemStack[length];
    //
    //        for (int i = 0; i < length; ++i) {
    //            items[i] = (ItemStack) dataInput.readObject();
    //        }
    //
    //        dataInput.close();
    //        return items;
    //    }

}