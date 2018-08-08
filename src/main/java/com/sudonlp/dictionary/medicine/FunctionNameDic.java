package com.sudonlp.dictionary.medicine;

import com.sudonlp.SudoNLP;
import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.dictionary.CoreDictionary;
import com.sudonlp.dictionary.common.LoadDicUtil;

public class FunctionNameDic {
    public static DoubleArrayTrie<CoreDictionary.Attribute> wordDic = new DoubleArrayTrie<CoreDictionary.Attribute>();
    public final static String dicPath = SudoNLP.Config.FunctionDicPath;
    static {
        // 在静态块中加载药名词典
        LoadDicUtil.loadDic(dicPath, wordDic, "功效");
    }
}
