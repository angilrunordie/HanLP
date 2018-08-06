package com.sudonlp.corpus;

import com.sudonlp.corpus.dictionary.EasyDictionary;
import com.sudonlp.corpus.dictionary.NSDictionaryMaker;
import com.sudonlp.corpus.document.CorpusLoader;
import com.sudonlp.corpus.document.Document;

public class TestNSDictionaryMaker {

    public static void main(String[] args)
    {
        EasyDictionary dictionary = EasyDictionary.create("data/dictionary/2014_dictionary.txt");
        final NSDictionaryMaker nsDictionaryMaker = new NSDictionaryMaker(dictionary);
        CorpusLoader.walk("D:\\JavaProjects\\CorpusToolBox\\data\\2014\\", new CorpusLoader.Handler()
        {
            @Override
            public void handle(Document document)
            {
                nsDictionaryMaker.compute(document.getComplexSentenceList());
            }
        });
        nsDictionaryMaker.saveTxtTo("D:\\JavaProjects\\SudoNLP\\data\\test\\place\\ns");
    }
}
