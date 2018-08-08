/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>me@hankcs.com</email>
 * <create-date>16/2/10 PM6:51</create-date>
 *
 * <copyright file="MathUtility.java" company="码农场">
 * Copyright (c) 2008-2016, 码农场. All Right Reserved, http://www.hankcs.com/
 * This source is subject to Hankcs. Please contact Hankcs to get more information.
 * </copyright>
 */
package com.sudonlp.utility;

import com.sudonlp.dictionary.CoreBiGramTableDictionary;
import com.sudonlp.seg.common.Vertex;
import com.sudonlp.dictionary.CoreBiGramTableDictionary;

import java.util.Map;
import java.util.Set;

import static com.sudonlp.utility.Predefine.MAX_FREQUENCY;
import static com.sudonlp.utility.Predefine.dSmoothingPara;
import static com.sudonlp.utility.Predefine.dTemp;

/**
 * 一些数学小工具
 * @author hankcs
 */
public class MathUtility
{
    public static int sum(int ... var)
    {
        int sum = 0;
        for (int x : var)
        {
            sum += x;
        }

        return sum;
    }

    public static float sum(float ... var)
    {
        float sum = 0;
        for (float x : var)
        {
            sum += x;
        }

        return sum;
    }

    public static double percentage(double current, double total)
    {
        return current / total * 100.;
    }

    public static double average(double array[])
    {
        double sum = 0;
        for (int i = 0; i < array.length; i++)
            sum += array[i];
        return sum / array.length;
    }

    /**
     * 使用log-sum-exp技巧来归一化一组对数值
     *
     * @param predictionScores
     */
    public static void normalizeExp(Map<String, Double> predictionScores)
    {
        Set<Map.Entry<String, Double>> entrySet = predictionScores.entrySet();
        double max = Double.NEGATIVE_INFINITY;
        for (Map.Entry<String, Double> entry : entrySet)
        {
            max = Math.max(max, entry.getValue());
        }

        double sum = 0.0;
        //通过减去最大值防止浮点数溢出
        for (Map.Entry<String, Double> entry : entrySet)
        {
            Double value = Math.exp(entry.getValue() - max);
            entry.setValue(value);

            sum += value;
        }

        if (sum != 0.0)
        {
            for (Map.Entry<String, Double> entry : entrySet)
            {
                predictionScores.put(entry.getKey(), entry.getValue() / sum);
            }
        }
    }

    public static void normalizeExp(double[] predictionScores)
    {
        double max = Double.NEGATIVE_INFINITY;
        for (double value : predictionScores)
        {
            max = Math.max(max, value);
        }

        double sum = 0.0;
        //通过减去最大值防止浮点数溢出
        for (int i = 0; i < predictionScores.length; i++)
        {
            predictionScores[i] = Math.exp(predictionScores[i] - max);
            sum += predictionScores[i];
        }

        if (sum != 0.0)
        {
            for (int i = 0; i < predictionScores.length; i++)
            {
                predictionScores[i] /= sum;
            }
        }
    }

    /**
     * 从一个词到另一个词的词的花费
     *
     * @param from 前面的词
     * @param to   后面的词
     * @return 分数
     */
    public static double calculateWeight(Vertex from, Vertex to)
    {
        int frequency = from.getAttribute().totalFrequency;
        if (frequency == 0)
        {
            frequency = 1;  // 防止发生除零错误
        }
//        int nTwoWordsFreq = BiGramDictionary.getBiFrequency(from.word, to.word);
        int nTwoWordsFreq = CoreBiGramTableDictionary.getBiFrequency(from.wordID, to.wordID);
        double value = -Math.log(Predefine.dSmoothingPara * frequency / (Predefine.MAX_FREQUENCY) + (1 - Predefine.dSmoothingPara) * ((1 - Predefine.dTemp) * nTwoWordsFreq / frequency + Predefine.dTemp));
        if (value < 0.0)
        {
            value = -value;
        }

        if (frequency == 1 && nTwoWordsFreq == 1){
            // 这条语句为了解决 frequency 与 nTwoWordsFreq 都找不到时 weight 还很小的问题。   原因，value 的第二部分时根据条件概率算出来的，当frequency与nTwoWordsFreq 都为 1 时，条件概率为 1 .
            value = 10.0;
        }
//        logger.info(String.format("%5s frequency:%6d, %s nTwoWordsFreq:%3d, weight:%.2f", from.word, frequency, from.word + "@" + to.word, nTwoWordsFreq, value));
        return value;
    }

}