package com.sudonlp.dictionary.medicine;

import com.sudonlp.HanLP;
import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.dictionary.CoreDictionary;
import com.sudonlp.dictionary.common.LoadDicUtil;

public class FoodNameDic {
    public static DoubleArrayTrie<CoreDictionary.Attribute> wordDic = new DoubleArrayTrie<CoreDictionary.Attribute>();
    public final static String medicine_food_path = HanLP.Config.FoodNameDicPath;
    static{
        // 在静态块中加载药名词典
        LoadDicUtil.loadDic(medicine_food_path, wordDic, "食物");
    }
}
