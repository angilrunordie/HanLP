package util.IOUtil

import java.io.{File, FileInputStream, FileOutputStream}

import jxl.Workbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook


object ExcelIOUtil {

    /**
      * 将一个Excel 文件的内容 存在一个  List[List[List[String]]] 结构中， 第一层代表 sheet页， 第二层代表行， 第三层代表列
      * 注： 本函数只能读取03版（老版格式的Excel文件）
      * @param excelFilePath
      * @return
      */
    def readExcel(excelFilePath : String): List[List[List[String]]] = {
        val is = new FileInputStream(new File(excelFilePath).getAbsolutePath)
        val wb = Workbook.getWorkbook(is)
        val sheet_size = wb.getNumberOfSheets() // excel sheet 页的数量
        (0 until sheet_size).map(sheetIndex => {
            val sheet = wb.getSheet(sheetIndex)
            val columnNumber = sheet.getColumns
            (0 until sheet.getRows).map(rowIndex => {
                (0 until columnNumber).map(columnIndex => {
                    sheet.getCell(columnIndex, rowIndex).getContents()
                }).toList
            }).toList
        }).toList
    }

//    def readExcel

    /**
      * 生成一个Excel 文件
      * @param filePath     文件路径
      * @param sheetName    页签名称
      * @param head          表头
      * @param data          数据
      * 注 ： 表头列数 与 数据的 列数需要相同。
      */
    def createExcel_withHead(filePath : String, sheetName : String, head : List[String], data : List[List[String]]): Unit ={
        val workbook = new HSSFWorkbook()
        val sheet1 = workbook.createSheet(sheetName)

        // 表头内容
        val columnNumber = head.length
        val rowhead = workbook.getSheet(sheetName).createRow(0)
        (0 until columnNumber).foreach(index => {
            val cell = rowhead.createCell(index)
            cell.setCellValue(head(index))
        })

        // 数据内容
        (1 to data.length).foreach(rowNumber => {
            val row = workbook.getSheet(sheetName).createRow(rowNumber)
            val rowContent = data(rowNumber - 1)
            (0 until columnNumber).foreach(index => {
                val cell = row.createCell(index)
                cell.setCellValue(rowContent(index))
            })
        })

        workbook.write(new File(filePath))
    }


    def main(args: Array[String]): Unit = {

        val path = "C:\\work\\data\\聊天记录\\脉灯数据\\excelWriteTest.xls";
        val head: List[String] = List("1", "2", "3")
        val data: List[List[String]] = (0 to 5).toList.map(i => (1 to 3).toList.map(j => "**" + i.toString + i.toString + "**") )
        createExcel_withHead(path, "test", head, data)

//        val excelInfos = readExcel("C:/work/data/food.xls")
//        val foodS = excelInfos(0).map(_(1)).filter(!_.equals("name")).map(_.trim).map(_.split("\\(|\\[|\\{")(0)).distinct.sorted.map(name => name + "\t" + "食物" + "\t" + "1").mkString("\n")
//        FileIOUtil.newFileWriter("C:/work/HanLp/SudoNLP/data/dictionary/medicine/foodNames.txt", foodS)

//        val excelInfos = readExcel("C:\\work\\data\\drugInfos.xls")
////        println(excelInfos.length)
//////         提取药名列表( 其中有4个药名是重复的 )
////        excelInfos(0).map(_(1)).filter(!_.equals("name")).map(_.trim).distinct.zipWithIndex.foreach(println)
////
////        // 将药名输出到文件中去 输出格式： 药名（name）  西药名   1
////        val medicineNameS = excelInfos(0).map(_(1)).filter(!_.equals("name")).map(_.trim).distinct.map(medicineName => medicineName + "\t" + "药名" + "\t" + "1").mkString("\n")
////        FileIOUtil.newFileWriter("C:\\work\\HanLp\\SudoNLP\\data\\dictionary\\medicine\\drugNames.txt", medicineNameS)
//
//        // 搜索药名中含有空格的
////        excelInfos(0).map(_(1)).filter(!_.equals("name")).map(_.trim).distinct.filter(_.contains(" ")).foreach(println)
//        /**
//          * 钙尔奇D 600
//          * 钙尔奇D 300
//          */
//        GroupStat.stringLengthDistribution(excelInfos(0).map(_(1)).filter(!_.equals("name")).map(_.trim).distinct).foreach(println)
//        val name1 = excelInfos(0).map(_(1)).filter(!_.equals("name")).map(_.trim).distinct.filter(_.length == 1).toSet   // 有七种药药名长度为一
//        /**
//          * 氧
//          * 氨
//          * 氙
//          * 硒
//          * 铬
//          * 铟
//          * 碘
//          */
//
//        println(excelInfos(0).filter(line => name1.contains(line(1))).map(_.mkString("\n")).mkString("\n\n"))
//
//        /**
//          * 氧
//          * 【适应症】
//          * 主要用于窒息、肺炎、肺水肿、哮喘、心力衰竭、周围循环衰竭、呼吸衰竭、麻醉药中毒、氧化碳中毒等各缺氧情况；也用于驱除肠道蛔虫
//          * 【用量用法】
//          * 1.治疗缺氧：将氧气筒（或含５％二氧化碳气的）与吸入装置连接，按以每分钟３００～１０００ｍｌ的速度使氧通过洗气瓶，经鼻导管或漏斗给病人吸入。 2.驱蛔虫：清晨空腹经胃管缓慢输入氧气；剂量：（年龄＋１）×１００ｍｌ，最不超过１２００，输氧后卧床休息２～３小时。
//          * 【注意事项】
//          * 1.切实做到防火、防油、防震，氧气瓶或袋存放在阴凉处，周围严禁烟火或放置易燃物品。 2.持续用氧者应经常检查鼻导管是否通畅，每８～１２小时换鼻导管１次，并更换鼻孔插入。 3.长期使用氧的浓度以３０％～４０％（ｍｌ／ｍｌ） 限，应急可吸纯氧。注意吸气内的水蒸气的饱和度。4.消化道溃疡，胃肠出血病人忌用氧气驱虫。
//          *
//          * 1266
//          * 氨
//          * 【适应症】
//          * 昏迷、醉酒者吸入氨水有苏醒作用，对昏厥者作用较好外用配成２５％搽剂作刺激药，尚有中和酸的作用，用于昆虫咬伤等。
//          * 【用量用法】
//          * 稀氨水吸入：每１００ｍｌ中含氨１０ｇ。 外用：２５％搽剂。
//          *
//          * 2379
//          * 氙
//          * 【适应症】
//          * 氙在水溶液中溶解度很小，当含有氙的溶液通过含气肺泡时，９５％注射的氙从溶液中溢出，它的分布与肺毛细血管血流量成正比
//          *
//          * 2380
//          * 硒
//          * 【适应症】
//          * 制剂７５硒蛋氨酸，静注后可被甲状腺摄取，也可在胰腺合成消化酶
//          *
//          * 2383
//          * 铬
//          * 【适应症】
//          * 1.循环血量测定 2.脾脏扫描。 3.胎盘扫描。 4.红细胞、血小板寿命测定。
//          *
//          * 2388
//          * 铟
//          * 【适应症】 肝、脾、骨髓扫描用铟胶体脑、肾扫描用铟－ＤＴＰＡ。肺扫描用铟Ｆｅ（ＯＨ）＊＊３颗粒。 胎盘扫描用铟Ｆｅ抗坏血酸。 肝血池扫描用铟输铁蛋白。
//          *
//          * 2419
//          * 碘
//          * 【适应症】 1.诊断：甲状腺功能测定；甲状腺扫描 2.治疗：甲状腺功能亢进；甲状腺癌转移；顽固性心绞痛。
//          */

    }
}
