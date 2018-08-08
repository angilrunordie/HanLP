package adventure

import com.sudonlp.SudoNLP
import com.sudonlp.seg.common.Term
import com.sudonlp.seg.sodu.SoduSegment
import util.IOUtil.ExcelIOUtil

import scala.io.Source

object comments0806 {
    def main(args: Array[String]): Unit = {
        val filePath = "C:\\work\\data\\聊天记录\\脉灯数据\\comment.xls"
//        val filePath = "C:\\work\\data\\聊天记录\\脉灯数据\\topicComment.xls"
        val commentsContent = ExcelIOUtil.readExcel(filePath).head

        val head = commentsContent.head
        val data = (1 until commentsContent.length).map(index => commentsContent(index)).toList

        // 下面生成解析数据
//        val headNew = head ++ List("segResult")
        val segment = new SoduSegment()
//        val resultData = data.map(line => {
//            val segmentS = segment.seg(line(2)).toArray().map(_.asInstanceOf[Term]).map(term => {
//                if(term.nature.toString.equals("n") || term.nature.toString.equals("nr")){
//                    term.word
//                }else{
//                    "(" + term.word + ")" + "[" + term.nature + "]"
//                }
//            }).mkString("")
//            line ++ List(segmentS)
//        })
//        ExcelIOUtil.createExcel_withHead("C:\\work\\data\\聊天记录\\脉灯数据\\topicCommentResultExcel.xls", "sheet1", headNew, resultData)

//        resultData.map(_(6)).foreach(println)
        data.map(line => line(2)).filter(_.length > 0).map(sentence => segment.seg(sentence)).foreach(println)

        println(segment.seg("马化腾是腾讯高管，曾在山东大学发表AI如何诊断肺癌的演讲！"))

    }
}
