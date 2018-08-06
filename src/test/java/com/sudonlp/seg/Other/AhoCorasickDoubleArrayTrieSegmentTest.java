package com.sudonlp.seg.Other;

import com.sudonlp.SudoNLP;
import junit.framework.TestCase;

public class AhoCorasickDoubleArrayTrieSegmentTest extends TestCase
{
    public void testLoadMyDictionary() throws Exception
    {
        AhoCorasickDoubleArrayTrieSegment segment
            = new AhoCorasickDoubleArrayTrieSegment("data/dictionary/CoreNatureDictionary.mini.txt");
        SudoNLP.Config.ShowTermNature = false;
        assertEquals("[江西, 鄱阳湖, 干枯]", segment.seg("江西鄱阳湖干枯").toString());
    }
}