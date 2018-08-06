package com.sudonlp.model.hmm;

import com.sudonlp.corpus.PKU;
import com.sudonlp.model.perceptron.PerceptronSegmenter;
import com.sudonlp.tokenizer.lexical.AbstractLexicalAnalyzer;
import com.sudonlp.utility.TestUtility;
import com.sudonlp.corpus.PKU;
import junit.framework.TestCase;

import java.util.Arrays;

public class HMMPOSTaggerTest extends TestCase
{
    public void testTrain() throws Exception
    {
        HMMPOSTagger tagger = new HMMPOSTagger(); // 创建词性标注器
//        HMMPOSTagger tagger = new HMMPOSTagger(new SecondOrderHiddenMarkovModel()); // 或二阶隐马
        tagger.train(PKU.PKU199801); // 训练
        System.out.println(Arrays.toString(tagger.tag("他", "的", "希望", "是", "希望", "上学"))); // 预测
        AbstractLexicalAnalyzer analyzer = new AbstractLexicalAnalyzer(new PerceptronSegmenter(), tagger); // 构造词法分析器
        System.out.println(analyzer.analyze("他的希望是希望上学").translateLabels()); // 分词+词性标注
    }
}