package com.sudonlp;

import com.sudonlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.sudonlp.seg.Viterbi.ViterbiSegment;
import junit.framework.TestCase;

public class SudoNLPTest extends TestCase
{
    public void testNewSegment() throws Exception
    {
        assertTrue(SudoNLP.newSegment("维特比") instanceof ViterbiSegment);
        assertTrue(SudoNLP.newSegment("感知机") instanceof PerceptronLexicalAnalyzer);
    }
}