/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/1/19 21:02</create-date>
 *
 * <copyright file="Node.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.sudonlp.seg.Viterbi.Path;

import com.sudonlp.utility.MathUtility;
import com.sudonlp.seg.common.Vertex;
import com.sudonlp.utility.MathUtility;

/**
 * @author hankcs
 * ！！！！！！！！！这个类从来就没有被应用过！！！！ 2018.8.1  ！！！！！
 *
 */
public class Node
{
    /**
     * 到该节点的最短路径的前驱节点
     */
    Node from;
    /**
     * 最短路径对应的权重
     */
    double weight;
    /**
     * 节点代表的顶点
     */
    Vertex vertex;

    public Node(Vertex vertex)
    {
        this.vertex = vertex;
    }

    public void updateFrom(Node from)
    {
        double weight = from.weight + MathUtility.calculateWeight(from.vertex, this.vertex);
        if (this.from == null || this.weight > weight)
        {
            this.from = from;
            this.weight = weight;
        }
    }

    @Override
    public String toString()
    {
        return vertex.toString();
    }
}
