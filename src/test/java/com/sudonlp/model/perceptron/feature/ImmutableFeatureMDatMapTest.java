package com.sudonlp.model.perceptron.feature;

import com.sudonlp.SudoNLP;
import com.sudonlp.model.perceptron.model.LinearModel;
import junit.framework.TestCase;

public class ImmutableFeatureMDatMapTest extends TestCase
{
    public void testCompress() throws Exception
    {
        LinearModel model = new LinearModel(SudoNLP.Config.PerceptronCWSModelPath);
        model.compress(0.1);
    }
}