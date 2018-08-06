package com.sudonlp.dependency;

import com.sudonlp.HanLP;
import com.sudonlp.corpus.dependency.CoNll.CoNLLLoader;
import com.sudonlp.corpus.dependency.CoNll.CoNLLSentence;
import com.sudonlp.corpus.dependency.CoNll.CoNLLWord;
import com.sudonlp.corpus.dependency.CoNll.Evaluator;
import com.sudonlp.corpus.tag.Nature;
import com.sudonlp.seg.common.Term;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class MaxEntDependencyParserTest extends TestCase
{
    public void testMaxEntParser() throws Exception
    {
//        HanLP.Config.enableDebug();
//        System.out.println(MaxEntDependencyParser.compute("我每天骑车上学"));
    }

//    public void testEvaluate() throws Exception
//    {
//        LinkedList<CoNLLSentence> sentenceList = CoNLLLoader.loadSentenceList("D:\\Doc\\语料库\\依存分析训练数据\\THU\\dev.conll");
//        Evaluator evaluator = new Evaluator();
//        int id = 1;
//        for (CoNLLSentence sentence : sentenceList)
//        {
//            System.out.printf("%d / %d...", id++, sentenceList.size());
//            long start = System.currentTimeMillis();
//            List<Term> termList = new LinkedList<Term>();
//            for (CoNLLWord word : sentence.word)
//            {
//                termList.add(new Term(word.LEMMA, Nature.valueOf(word.POSTAG)));
//            }
//            CoNLLSentence out = CRFDependencyParser.compute(termList);
//            evaluator.e(sentence, out);
//            System.out.println("done in " + (System.currentTimeMillis() - start) + " ms.");
//        }
//        System.out.println(evaluator);
//    }
}