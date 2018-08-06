package com.sudonlp.corpus;

import com.sudonlp.corpus.dictionary.EasyDictionary;
import com.sudonlp.corpus.dictionary.NTDictionaryMaker;
import com.sudonlp.corpus.document.CorpusLoader;
import com.sudonlp.corpus.document.Document;

public class TestNTDcitionaryMaker
{

    public static void main(String[] args)
    {
        EasyDictionary dictionary = EasyDictionary.create("data/dictionary/2014_dictionary.txt");
        final NTDictionaryMaker ntDictionaryMaker = new NTDictionaryMaker(dictionary);
        // CorpusLoader.walk("D:\\JavaProjects\\CorpusToolBox\\data\\2014\\", new CorpusLoader.Handler()
        CorpusLoader.walk("data/test/nt/test/", new CorpusLoader.Handler()
        {
            @Override
            public void handle(Document document)
            {
                ntDictionaryMaker.compute(document.getComplexSentenceList());
            }
        });
        ntDictionaryMaker.saveTxtTo("D:\\JavaProjects\\HanLP\\data\\test\\organization\\nt");
    }

}
