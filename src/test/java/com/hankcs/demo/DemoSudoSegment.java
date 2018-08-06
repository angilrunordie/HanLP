package com.hankcs.demo;

import com.sudonlp.HanLP;
import com.sudonlp.seg.sodu.SoduSegment;

public class DemoSudoSegment {
    public static void main(String[] args){
        SoduSegment segment = new SoduSegment();
        HanLP.Config.enableDebug();
        System.out.println(segment.seg("李亚鹏吃香蕉后消化不良，以为得了心脏病，时常头痛，呕吐！"));
    }
}
