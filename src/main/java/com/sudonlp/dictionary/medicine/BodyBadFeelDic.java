package com.sudonlp.dictionary.medicine;

import com.sudonlp.SudoNLP;
import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.dictionary.CoreDictionary;
import com.sudonlp.dictionary.common.LoadDicUtil;

public class BodyBadFeelDic {
    public static DoubleArrayTrie<CoreDictionary.Attribute> wordDic = new DoubleArrayTrie<CoreDictionary.Attribute>();
    public final static String medicine_BodyBadFeelDic_path = SudoNLP.Config.BodyBadFeelDicPath;
    static {
        // 在静态块中加载药名词典
        LoadDicUtil.loadDic(medicine_BodyBadFeelDic_path, wordDic, "不适感");
    }
}
