package com.sudonlp.corpus;

import java.util.LinkedList;
import java.util.List;

import com.sudonlp.corpus.dictionary.EasyDictionary;
import com.sudonlp.corpus.dictionary.NRDictionaryMaker;
import com.sudonlp.corpus.document.CorpusLoader;
import com.sudonlp.corpus.document.Document;
import com.sudonlp.corpus.document.sentence.word.IWord;
import com.sudonlp.corpus.document.sentence.word.Word;

public class TestNRDcitionaryMaker
{

    public static void main(String[] args)
    {
        EasyDictionary dictionary = EasyDictionary.create("data/dictionary/2014_dictionary.txt");
        final NRDictionaryMaker nrDictionaryMaker = new NRDictionaryMaker(dictionary);
        CorpusLoader.walk("D:\\JavaProjects\\CorpusToolBox\\data\\2014\\", new CorpusLoader.Handler()
        {
            @Override
            public void handle(Document document)
            {
                List<List<Word>> simpleSentenceList = document.getSimpleSentenceList();
                List<List<IWord>> compatibleList = new LinkedList<List<IWord>>();
                for (List<Word> wordList : simpleSentenceList)
                {
                    compatibleList.add(new LinkedList<IWord>(wordList));
                }
                nrDictionaryMaker.compute(compatibleList);
            }
        });
        nrDictionaryMaker.saveTxtTo("D:\\JavaProjects\\HanLP\\data\\test\\person\\nr1");
    }

}
