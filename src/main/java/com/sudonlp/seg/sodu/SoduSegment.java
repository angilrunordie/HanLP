package com.sudonlp.seg.sodu;

import com.sudonlp.SudoNLP;
import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.dictionary.CoreDictionary;
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
import com.sudonlp.utility.Predefine;
import scala.Predef;

import java.util.*;

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
        if (SudoNLP.Config.DEBUG)
        {
            System.out.printf("粗分词网：\n%s\n", wordNetAll);
        }
//        start = System.currentTimeMillis();
        List<Vertex> vertexList = viterbi(wordNetAll);
//        System.out.println("最短路：" + (System.currentTimeMillis() - start));

        if (SudoNLP.Config.DEBUG)
        {
            System.out.println("粗分结果" + convert(vertexList, false));
        }

        Set<String> priorNeSet = new HashSet<String>(); // 用于存储优先级别高的实体类型。这些实体类型被分出来之后，不可以被合并和替换。
        if(config.medicineNeRecognize){  // 这里医疗分词占主导地位，后续的分词结果不能够覆盖医疗分词的结果。
            WordNet wordNetOptinum = new WordNet(sentence, vertexList);
            if(config.drugNameRecognize){
                MedicineDrugRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.drugName);
            }

            if(config.foodNameRecognize){
                MedicineFoodRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.foodName);
            }

            if(config.diseaseNameRecognize){
                MedicineDiseaseNameRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.diseaseName);
                for(String disease : Predefine.diseaseNameClassifications){
                    priorNeSet.add(disease);  // 逐个加入疾病类别
                }
            }

            if(config.bodyPartRecognize){
                MedicineBodyPartNameRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.bodyPart);
            }

            if(config.bodaBadFeelRecognize){
                MedicineBodyBadFeelRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.BodyBadFeel);
            }

            if(config.behaviorRecognize){
                MedicineBehaviorRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.behaviorName);
            }

            if(config.checkMethodRecognize){
                MedicineCheckMethodRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.checkMethodName);
            }

            if(config.diseaseStageRecognize){
                MedicineDiseaseStageRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.diseaseStageName);
            }

            if(config.functionNameRecognize){
                MedicineFunctionNameRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.functionName);
            }

            if(config.mentalFeelRecognize){
                MedicineMentalFeelRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.mentalFeelName);
            }

            if(config.symptomRecognize){
                MedicineSymptomNameRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.symptomName);
            }

            if(config.treatMethodRecognize){
                MedicineTreatMethodRecognition.recognition(vertexList, wordNetOptinum, wordNetAll);
                priorNeSet.add(Predefine.treatMethodName);
            }

            vertexList = viterbi_sodu(wordNetOptinum);
        }

        // 在这里加入对核心字典的使用
        { // 写到块里面是为了增加程序的独立性
            WordNet wordNetOptinum = new WordNet(sentence, vertexList);
            DoubleArrayTrie<CoreDictionary.Attribute>.Searcher searcher = CoreDictionary.trie.getSearcher(sentence, 0);
            for(int i = 1; i < wordNetOptinum.getVertexes().length - 1; i++){
                // 这里删除掉非优先的实体分词
                if(wordNetOptinum.getVertexes()[i].size() == 1 && !priorNeSet.contains(wordNetOptinum.getVertexes()[i].get(0).attribute.nature[0].toString())){
                    wordNetOptinum.getVertexes()[i] = new LinkedList<Vertex>();
                }
            }

            int[] ifPrior = new int[sentence.length];
            int index = 0;
            for(int i = 1; i < vertexList.size() - 1; i++){
                if(priorNeSet.contains(vertexList.get(i).attribute.nature[0].toString())){
                    for(int j = index; j < index + vertexList.get(i).word.length(); j++){
                        ifPrior[j] = 1;
                    }
                    index = index + vertexList.get(i).word.length();
                }else{
                    for(int j = index; j < index + vertexList.get(i).word.length(); j++){
                        ifPrior[j] = 0;
                    }
                    index = index + vertexList.get(i).word.length();
                }
            }
            while(searcher.next()){
                // 首先查看这个分词是否会覆盖 priorNeSet 中的类型。
                boolean flag = true;
                for(int i = searcher.begin; i < searcher.begin + searcher.length; i++){
                    if(ifPrior[i] == 1){
                        flag = false; break;
                    }
                }
                if(flag){
                    wordNetOptinum.add(searcher.begin + 1, new Vertex(new String(sentence, searcher.begin, searcher.length), searcher.value, searcher.index)); // add 函数已经保证了唯一性，即不会与之前的分词结果重合，但无法解决覆盖问题。
                }
            }

            LinkedList<Vertex>[] vertexes = wordNetOptinum.getVertexes();
            for (int i = 1; i < vertexes.length; )
            {
                if (vertexes[i].isEmpty())
                {
                    int j = i + 1;
                    for (; j < vertexes.length - 1; ++j)
                    {
                        if (!vertexes[j].isEmpty()) break;
                    }
                    wordNetOptinum.add(i, quickAtomSegment(sentence, i - 1, j - 1));
                    i = j;
                }
                else i += vertexes[i].getLast().realWord.length();
            }

            wordNetOptinum.cleanVertexFrom();
            vertexList = viterbi(wordNetOptinum);
//            System.out.println("ssss");
        }

        if (config.useCustomDictionary)     // 这里后续解决掉 当之前已经出现医疗分词结果后， 新的字典不进行覆盖。 或者不用用户字典（Good idea :)）
        {
            if (config.indexMode > 0)
                combineByCustomDictionary(vertexList, wordNetAll);
            else combineByCustomDictionary(vertexList);
        }

        // 数字识别
        if (config.numberQuantifierRecognize)
        {
            mergeNumberQuantifier(vertexList, wordNetAll, config);
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
                vertexList = viterbi(wordNetOptimum);
//                vertexList = viterbi_sodu(wordNetOptimum);
                if (SudoNLP.Config.DEBUG)
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

    /**
     * 使用CoreDic 中的信息进行 加入分词信息
     * @param vertexList
     */
    public void useCoreDic(List<Vertex> vertexList){

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
//         原子分词，保证图连通
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
