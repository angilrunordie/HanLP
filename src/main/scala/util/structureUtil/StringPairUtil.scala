package util.structureUtil

object StringPairUtil {
    // 寻找两个字符串的最大公共子集及 s1 的 非公共子集部分
    def maximum_common_subset(s1 : String, s2 : String): (Set[String], Set[String]) = {
        var common_subset: Set[String] = Set.empty[String]
        var small_not_common_index: List[(Int, Int)] = List.empty[(Int, Int)]
        var begin = 0; var end = 1; //subString 在smallS 中的坐标
        val (smallS, largeS) = if(s1.length < s2.length){(s1, s2)} else { (s2, s1) }
        while(end <= smallS.length){ //
            val maySub = smallS.substring(begin, end)
            if(largeS.contains(maySub)){ // 包含 begin，end 对应的subString, 继续向后尝试
                end = end + 1
            } else{  // 不包含的情况
                if(end - begin == 1){  // 第一个字符就不符合
                    begin = begin + 1; end = end + 1
                }else{
                    common_subset = common_subset.+(smallS.substring(begin, end - 1)) // 至少有一个字符符合
                    small_not_common_index = small_not_common_index ++ List((begin, end - 1))
                    begin = end - 1
                }
            }
        }

        if(end - begin >= 2){
            common_subset = common_subset.+(smallS.substring(begin, end - 1))
            small_not_common_index = small_not_common_index ++ List((begin, end - 1))
        }
        var small_not_common_subSet =  (0 until small_not_common_index.length - 1).map(index => {
            val not_common_begin = small_not_common_index(index)._2
            val not_common_end = small_not_common_index(index + 1)._1
            smallS.substring(not_common_begin, not_common_end)
        }).filter(!_.equals("")).toSet
        // 添加 small_not_common_subSet 的首尾
        if(small_not_common_index.length >0 && small_not_common_index.head._1 != 0){
            small_not_common_subSet = small_not_common_subSet.+(smallS.substring(0, small_not_common_index.head._1))
        }
        if(small_not_common_index.length >0 && small_not_common_index.last._2 != smallS.length){
            small_not_common_subSet = small_not_common_subSet.+(smallS.substring(small_not_common_index.last._2, smallS.length))
        }

        (common_subset, small_not_common_subSet)
    }

    def main(args: Array[String]): Unit = {
        val s1 = "东边山上有个太阳"; val s2 = "西边草坪上也有个太岁"
        //        val s1 = "东边山上有个太阳，太阳下山"; val s2 = "西边草坪上也有个太阳，太阳上山"
        println(maximum_common_subset(s1, s2))

        //        println(Set(1,2,3)++ Set(2,3,4))

    }
}

