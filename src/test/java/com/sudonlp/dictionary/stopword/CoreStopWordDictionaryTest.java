package com.sudonlp.dictionary.stopword;

import com.sudonlp.collection.MDAG.MDAGSet;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class CoreStopWordDictionaryTest extends TestCase
{
    public void testContains() throws Exception
    {
        assertTrue(CoreStopWordDictionary.contains("这就是说"));
    }

    public void testContainsSomeWords() throws Exception
    {
        assertEquals(true, CoreStopWordDictionary.contains("可以"));
    }

    public void testMDAG() throws Exception
    {
        List<String> wordList = new LinkedList<String>();
        wordList.add("zoo");
        wordList.add("hello");
        wordList.add("world");
        MDAGSet set = new MDAGSet(wordList);
        set.add("bee");
        assertEquals(true, set.contains("bee"));
        set.remove("bee");
        assertEquals(false, set.contains("bee"));
    }

//    public void testRemoveDuplicateEntries() throws Exception
//    {
//        StopWordDictionary dictionary = new StopWordDictionary(new File(SudoNLP.Config.CoreStopWordDictionaryPath));
//        BufferedWriter bw = IOUtil.newBufferedWriter(SudoNLP.Config.CoreStopWordDictionaryPath);
//        for (String word : dictionary)
//        {
//            bw.write(word);
//            bw.newLine();
//        }
//        bw.close();
//    }
}