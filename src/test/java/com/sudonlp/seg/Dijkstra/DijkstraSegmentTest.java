package com.sudonlp.seg.Dijkstra;

import com.sudonlp.seg.SegmentTestCase;
import com.sudonlp.corpus.tag.Nature;
import com.sudonlp.seg.Segment;
import com.sudonlp.seg.common.Term;

import java.util.List;

public class DijkstraSegmentTest extends SegmentTestCase
{
    public void testWrongName() throws Exception
    {
        Segment segment = new DijkstraSegment();
        List<Term> termList = segment.seg("好像向你借钱的人跑了");
        assertNoNature(termList, Nature.nr);
//        System.out.println(termList);
    }

    public void testIssue770() throws Exception
    {
//        SudoNLP.Config.enableDebug();
        Segment segment = new DijkstraSegment();
        List<Term> termList = segment.seg("为什么我扔出的瓶子没有人回复？");
//        System.out.println(termList);
        assertSegmentationHas(termList, "瓶子 没有");
    }
}