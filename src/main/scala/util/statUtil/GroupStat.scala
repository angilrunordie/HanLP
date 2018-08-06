package util.statUtil

object GroupStat {
    /**
      * 统计一个String 列表中 字符串 长度的分布
      * @param listString
      */
    def stringLengthDistribution(listString : List[String]): List[(Int, Int)] ={
        listString.map(s => (s.length, 1)).groupBy(_._1).map{case (key, infos) => (key, infos.length)}.toList.sortBy(_._1)
    }
}
