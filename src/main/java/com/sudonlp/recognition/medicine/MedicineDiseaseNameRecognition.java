package com.sudonlp.recognition.medicine;

import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.dictionary.CoreDictionary;
import com.sudonlp.dictionary.medicine.DiseaseNameDic;
import com.sudonlp.seg.common.Vertex;
import com.sudonlp.seg.common.WordNet;

import java.util.List;

public class MedicineDiseaseNameRecognition {
    public static void recognition(List<Vertex> pWordSegResult, WordNet wordNetOptimum, WordNet wordNetAll) {
        char[] charArray = wordNetAll.charArray;
        DoubleArrayTrie<CoreDictionary.Attribute>.Searcher searcher = DiseaseNameDic.wordDic.getSearcher(String.valueOf(wordNetAll.charArray), 0);
        while(searcher.next()){
            wordNetOptimum.add(searcher.begin + 1, new Vertex(new String(charArray, searcher.begin, searcher.length), searcher.value, searcher.index));
        }
    }
}
