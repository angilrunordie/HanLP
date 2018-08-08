package adventure

import util.IOUtil.{ExcelIOUtil, FileIOUtil}

import scala.io.Source

object Dic0807 {
    def main(args: Array[String]): Unit = {
//        val classes = Source.fromFile("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\DiseaseNames.txt").getLines().toList.map{_.split("\t")(1)}.distinct
//        println(classes.mkString("{\"", "\",\"", "\"}"))
//        ExcelIOUtil.readExcel()

        val neInfos = ExcelIOUtil.readExcel("C:\\work\\data\\comments实体抽取.xls").head
        val head = neInfos.head
        val data = neInfos.zipWithIndex.filter(_._2 != 0).map(_._1)

        println(head.zipWithIndex)
        // 疾病名称
        val diseaseName1 = Source.fromFile("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\DiseaseNames-20180807.txt").getLines().toList.map(_.split("\t")(0)).distinct
        val diseaseName2 = data.map(_(0).trim).distinct
        val diseaseNameNew = (diseaseName1 ++ diseaseName2).distinct.filter(_.length > 0)
        val diseaseNameS = diseaseNameNew.map(name => name + "\t" + "疾病" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\DiseaseNames.txt", diseaseNameS)

        // 药名
        val drugName1 = Source.fromFile("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\drugNames-20180807.txt").getLines().toList.map(_.split("\t")(0)).distinct
        val drugName2 = data.map(_(1).trim).distinct
        val drugNameNew = (drugName1 ++ drugName2).distinct.filter(_.length > 0)
        val drugNameS = drugNameNew.map(name => name + "\t" + "药物" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\drugNames.txt", drugNameS)

        // 身体部位
        val bodyPart1 = Source.fromFile("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\BodyPartNames-20180807.txt").getLines().toList.map(_.split("\t")(0)).distinct
        val bodyPart2 = data.map(_(3).trim).distinct
        val bodyPartNew = (bodyPart1 ++ bodyPart2).distinct.filter(_.length > 0)
        val bodyPartS = bodyPartNew.map(name => name + "\t" + "部位" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\BodyPartNames.txt", bodyPartS)

        // 食物
        val foodName1 = Source.fromFile("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\foodNames-20180807.txt").getLines().toList.map(_.split("\t")(0)).distinct
        val foodName2 = data.map(_(4).trim).distinct
        val foodNameNew = (foodName1 ++ foodName2).distinct.filter(_.length > 0)
        val foodNameS = foodNameNew.map(name => name + "\t" + "食物" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\foodNames.txt", foodNameS)

        // 行为方式
        val behaviorName = data.map(_(2).trim).distinct.filter(_.length > 0)
        val behaviorS = behaviorName.map(name => name + "\t" + "行为方式" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\behaviorName.txt", behaviorS)

        // 功效
        val functionName = data.map(_(6).trim).distinct.filter(_.length > 0)
        val functionS = functionName.map(name => name + "\t" + "功效" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\functionName.txt", functionS)

        // 症状
        val symptomName = data.map(_(7).trim).distinct.filter(_.length > 0)
        val symptomS = symptomName.map(name => name + "\t" + "症状" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\symptomName.txt", symptomS)

        // 心里感受
        val mentalFeelName = data.map(_(8).trim).distinct.filter(_.length > 0)
        val mentalFeelS = mentalFeelName.map(name => name + "\t" + "心里感受" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\mentalFeelName.txt", mentalFeelS)

        // 病情阶段
        val diseaseStageName = data.map(_(10).trim).distinct.filter(_.length > 0)
        val diseaseStageS = diseaseStageName.map(name => name + "\t" + "病情阶段" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\diseaseStageName.txt", diseaseStageS)

        // 治疗方式
        val treatMethodName = data.map(_(11).trim).distinct.filter(_.length > 0)
        val treatMethodS = treatMethodName.map(name => name + "\t" + "治疗方式" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\treatMethodName.txt", treatMethodS)

        // 检查手段
        val checkMethodName = data.map(_(14).trim).distinct.filter(_.length > 0)
        val checkMethodS = checkMethodName.map(name => name + "\t" + "检查方式" + "\t" + 1).mkString("\n")
        FileIOUtil.newFileWriter("C:\\work\\jiaofu\\HanLP\\data\\dictionary\\medicine\\checkMethodName.txt", checkMethodS)

    }
}
