package com.sudonlp.dictionary.medicine;

import com.sudonlp.SudoNLP;
import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.dictionary.CoreDictionary;
import com.sudonlp.dictionary.common.LoadDicUtil;

public class BodyPartDic {
    public static DoubleArrayTrie<CoreDictionary.Attribute> wordDic = new DoubleArrayTrie<CoreDictionary.Attribute>();
    public final static String medicine_BodyPartDic_path = SudoNLP.Config.BodyPartDicPath;
    static {
        // 在静态块中加载药名词典
        LoadDicUtil.loadDic(medicine_BodyPartDic_path, wordDic, "身体部位");
    }
}
