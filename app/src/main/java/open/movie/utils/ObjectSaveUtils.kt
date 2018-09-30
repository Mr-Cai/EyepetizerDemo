package open.movie.utils

import android.content.Context
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object ObjectSaveUtils {
    fun saveObject(context: Context, name: String, value: Any) {
        val fos: FileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE)
        val oos: ObjectOutputStream?
        oos = ObjectOutputStream(fos)
        oos.writeObject(value)
    }

    fun getValue(context: Context, name: String): Any? {
        val fis: FileInputStream = context.openFileInput(name)
        val ois: ObjectInputStream
        ois = ObjectInputStream(fis)
        return ois.readObject()
    }

    fun deleteFile(name: String, context: Context) {
        context.deleteFile(name)
    }
}
