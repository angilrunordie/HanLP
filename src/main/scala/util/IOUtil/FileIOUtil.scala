package util.IOUtil

import java.io.{File, PrintWriter}

object FileIOUtil {
    /**
      * 新建文件filePath，将 content 写入 filePath 中
      * @param filePath
      * @param content
      */
    def newFileWriter(filePath : String, content : String) = {
        val writer = new PrintWriter(new File(filePath))
        writer.write(content); writer.close()
    }
}