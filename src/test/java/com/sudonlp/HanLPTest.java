package com.sudonlp;

import com.sudonlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.sudonlp.seg.Viterbi.ViterbiSegment;
import junit.framework.TestCase;

public class HanLPTest extends TestCase
{
    public void testNewSegment() throws Exception
    {
        assertTrue(HanLP.newSegment("维特比") instanceof ViterbiSegment);
        assertTrue(HanLP.newSegment("感知机") instanceof PerceptronLexicalAnalyzer);
    }
}