package adventure

import util.IOUtil.ExcelIOUtil

import scala.io.Source

object comments0806 {
    def main(args: Array[String]): Unit = {
        val filePath = "C:\\work\\data\\聊天记录\\脉灯数据\\comment.xls"
        val commentsContent = ExcelIOUtil.readExcel(filePath)
        //        val head =
    }
}
