package com.sudonlp.model.perceptron;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PerceptronTaggerTest extends TestCase
{
    public void testEmptyInput() throws Exception
    {
        PerceptronPOSTagger tagger = new PerceptronPOSTagger();
        tagger.tag(new ArrayList<String>());
    }
}