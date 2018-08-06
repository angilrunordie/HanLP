/*
 * <author>Hankcs</author>
 * <email>me@hankcs.com</email>
 * <create-date>2017-10-28 11:39</create-date>
 *
 * <copyright file="NERTrainer.java" company="码农场">
 * Copyright (c) 2017, 码农场. All Right Reserved, http://www.hankcs.com/
 * This source is subject to Hankcs. Please contact Hankcs to get more information.
 * </copyright>
 */
package com.sudonlp.model.perceptron;

import com.sudonlp.model.perceptron.feature.FeatureMap;
import com.sudonlp.model.perceptron.instance.Instance;
import com.sudonlp.model.perceptron.instance.NERInstance;
import com.sudonlp.model.perceptron.tagset.NERTagSet;
import com.sudonlp.model.perceptron.tagset.TagSet;
import com.sudonlp.corpus.document.sentence.Sentence;
import com.sudonlp.corpus.document.sentence.Sentence;
import com.sudonlp.model.perceptron.feature.FeatureMap;
import com.sudonlp.model.perceptron.instance.Instance;
import com.sudonlp.model.perceptron.instance.NERInstance;
import com.sudonlp.model.perceptron.tagset.NERTagSet;
import com.sudonlp.model.perceptron.tagset.TagSet;

/**
 * @author hankcs
 */
public class NERTrainer extends PerceptronTrainer
{
    /**
     * 重载此方法以支持任意自定义NER类型，例如：<br>
     * NERTagSet tagSet = new NERTagSet();<br>
     * tagSet.nerLabels.add("nr");<br>
     * tagSet.nerLabels.add("ns");<br>
     * tagSet.nerLabels.add("nt");<br>
     * return tagSet;<br>
     * @return
     */
    @Override
    protected TagSet createTagSet()
    {
        NERTagSet tagSet = new NERTagSet();
        tagSet.nerLabels.add("nr");
        tagSet.nerLabels.add("ns");
        tagSet.nerLabels.add("nt");
        return tagSet;
    }

    @Override
    protected Instance createInstance(Sentence sentence, FeatureMap featureMap)
    {
        return NERInstance.create(sentence, featureMap);
    }
}
