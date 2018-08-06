package com.sudonlp.seg.sodu;

import com.sudonlp.HanLP;
import com.sudonlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.dictionary.CoreDictionary;
import com.sudonlp.dictionary.CustomDictionary;
import com.sudonlp.recognition.medicine.*;
import com.sudonlp.recognition.nr.JapanesePersonRecognition;
import com.sudonlp.recognition.nr.PersonRecognition;
import com.sudonlp.recognition.nr.TranslatedPersonRecognition;
import com.sudonlp.recognition.ns.PlaceRecognition;
import com.sudonlp.recognition.nt.OrganizationRecognition;
import com.sudonlp.seg.WordBasedSegment;
import com.sudonlp.seg.common.Term;
import com.sudonlp.seg.common.Vertex;
import com.sudonlp.seg.common.WordNet;
import com.sudonlp.recognition.medicine.*;
import com.sudonlp.recognition.nr.JapanesePersonRecognition;
import com.sudonlp.recognition.nr.PersonRecognition;
import com.sudonlp.recognition.nr.TranslatedPersonRecognition;
import com.sudonlp.recognition.ns.PlaceRecognition;
import com.sudonlp.recognition.nt.OrganizationRecognition;
import com.sudonlp.seg.common.Term;
import com.sudonlp.seg.common.Vertex;
import com.sudonlp.seg.common.WordNet;

import java.util.LinkedList;
import java.util.List;

public class SoduSegment extends WordBasedSegment {
    @Override
    protected List<Term> segSentence(char[] sentence)
    {
//        long start = System.currentTimeMillis();
        WordNet wordNetAll = new WordNet(sentence);
        ////////////////生成词网 ////////////////////
        generateWordNet(wordNetAll); //  修改 ：这里调用本类的初始化函数，之生成最原始的网络////2018.8.1
        ///////////////生成词图////////////////////
//        System.out.println("构图：" + (System.currentTimeMillis() - start));
        if (HanLP.Config.DEBUG)
        {
            System.out.printf("粗分词网：\n%s\n", wordNetAll);
        }
//        start = System.currentTimeMillis();
        List<Vertex> vertexList = viterbi(wordNetAll);
//        System.out.println("最短路：" + (System.currentTimeMillis() - start));

//        if (config.useCustomDictionary)     // 这里去掉对 自定义词典 的使用
//        {
//            if (config.indexMode > 0)
//                combineByCustomDictionary(vertexList, wordNetAll);
//            else combineByCustomDictionary(vertexList);
//        }

        if (HanLP.Config.DEBUG)
        {
            System.out.println("粗分结果" + convert(vertexList, false));
        }

        // 数字识别
        if (config.numberQuantifierRecognize)
        {
            mergeNumberQuantifier(vertexList, wordNetAll, config);
        }

        if(config.medicineNeRecognize){
            WordNet wordNetOptinum = new WordNet(sentence, vertexList);
            if(config.drugNameRecognize){
                MedicineDrugRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
            }

            if(config.foodNameRecognize){
                MedicineFoodRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
            }

            if(config.diseaseNameRecognize){
                MedicineDiseaseNameRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
            }

            if(config.bodyPartRecognize){
                MedicineBodyPartNameRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
            }

            if(config.bodaBadFeelRecognize){
                MedicineBodyBadFeelRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
            }

            vertexList = viterbi_sodu(wordNetOptinum);
        }

        // 实体命名识别
        if (config.ner)
        {
            WordNet wordNetOptimum = new WordNet(sentence, vertexList);
            int preSize = wordNetOptimum.size();
            if (config.nameRecognize)
            {
                PersonRecognition.recognition(vertexList, wordNetOptimum, wordNetAll);
            }
            if (config.translatedNameRecognize)
            {
                TranslatedPersonRecognition.recognition(vertexList, wordNetOptimum, wordNetAll);
            }
            if (config.japaneseNameRecognize)
            {
                JapanesePersonRecognition.recognition(vertexList, wordNetOptimum, wordNetAll);
            }
            if (config.placeRecognize)
            {
                PlaceRecognition.recognition(vertexList, wordNetOptimum, wordNetAll);
            }
            if (config.organizationRecognize)
            {
                // 层叠隐马模型——生成输出作为下一级隐马输入
                wordNetOptimum.clean();
                vertexList = viterbi(wordNetOptimum);
                wordNetOptimum.clear();
                wordNetOptimum.addAll(vertexList);
                preSize = wordNetOptimum.size();
                OrganizationRecognition.recognition(vertexList, wordNetOptimum, wordNetAll);
            }
            if (wordNetOptimum.size() != preSize)
            {
                wordNetOptimum.cleanVertexFrom();
//                vertexList = viterbi(wordNetOptimum);
                vertexList = viterbi_sodu(wordNetOptimum);
                if (HanLP.Config.DEBUG)
                {
                    System.out.printf("细分词网：\n%s\n", wordNetOptimum);
                }
            }
        }

        // 如果是索引模式则全切分
        if (config.indexMode > 0)
        {
            return decorateResultForIndexMode(vertexList, wordNetAll);
        }

        // 是否标注词性
        if (config.speechTagging)
        {
            speechTagging(vertexList);
        }

        return convert(vertexList, config.offset);
    }

    private static List<Vertex> viterbi(WordNet wordNet)
    {
        // 避免生成对象，优化速度
        LinkedList<Vertex> nodes[] = wordNet.getVertexes();
        LinkedList<Vertex> vertexList = new LinkedList<Vertex>();
        for (Vertex node : nodes[1])
        {
            node.updateFrom(nodes[0].getFirst());
        }
        for (int i = 1; i < nodes.length - 1; ++i)
        {
            LinkedList<Vertex> nodeArray = nodes[i];
            if (nodeArray == null) continue;
            for (Vertex node : nodeArray)
            {
                if (node.from == null) continue;
                for (Vertex to : nodes[i + node.realWord.length()])
                {
                    to.updateFrom(node);
                }
            }
        }
        Vertex from = nodes[nodes.length - 1].getFirst();
        while (from != null)
        {
            vertexList.addFirst(from);
            from = from.from;
        }
        return vertexList;
    }

    private static List<Vertex> viterbi_sodu(WordNet wordNet)
    {
        // 避免生成对象，优化速度
        LinkedList<Vertex> nodes[] = wordNet.getVertexes();
        LinkedList<Vertex> vertexList = new LinkedList<Vertex>();
        for (Vertex node : nodes[1])
        {
            node.updateFrom_soduSimple(nodes[0].getFirst());
        }
        for (int i = 1; i < nodes.length - 1; ++i)
        {
            LinkedList<Vertex> nodeArray = nodes[i];
            if (nodeArray == null) continue;
            for (Vertex node : nodeArray)
            {
                if (node.from == null) continue;
                for (Vertex to : nodes[i + node.realWord.length()])
                {
                    to.updateFrom_soduSimple(node);
                }
            }
        }
        Vertex from = nodes[nodes.length - 1].getFirst();
        while (from != null)
        {
            vertexList.addFirst(from);
            from = from.from;
        }
        return vertexList;
    }

    @Override
    // 在这里重写 generateWordNet 的代码，去掉根据 核心词典 分词的部分， 只保留根据字去分词
    public void generateWordNet(final WordNet wordNetStorage)
    {
        final char[] charArray = wordNetStorage.charArray;

        for(int i = 0; i < charArray.length; i++){
            wordNetStorage.add(i + 1, new Vertex(new String(charArray, i, 1), new String(charArray, i, 1), null));
        }

//        // 核心词典查询
//        DoubleArrayTrie<CoreDictionary.Attribute>.Searcher searcher = CoreDictionary.trie.getSearcher(charArray, 0);
//        while (searcher.next())
//        {
//            wordNetStorage.add(searcher.begin + 1, new Vertex(new String(charArray, searcher.begin, searcher.length), searcher.value, searcher.index));
//        }
//        // 强制用户词典查询
//        if (config.forceCustomDictionary)
//        {
//            CustomDictionary.parseText(charArray, new AhoCorasickDoubleArrayTrie.IHit<CoreDictionary.Attribute>()
//            {
//                @Override
//                public void hit(int begin, int end, CoreDictionary.Attribute value)
//                {
//                    wordNetStorage.add(begin + 1, new Vertex(new String(charArray, begin, end - begin), value));
//                }
//            });
//        }
        // 原子分词，保证图连通
//        LinkedList<Vertex>[] vertexes = wordNetStorage.getVertexes();
//        for (int i = 1; i < vertexes.length; )
//        {
//            if (vertexes[i].isEmpty())
//            {
//                int j = i + 1;
//                for (; j < vertexes.length - 1; ++j)
//                {
//                    if (!vertexes[j].isEmpty()) break;
//                }
//                wordNetStorage.add(i, quickAtomSegment(charArray, i - 1, j - 1));
//                i = j;
//            }
//            else i += vertexes[i].getLast().realWord.length();
//        }
    }
}
