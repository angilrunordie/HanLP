package com.sudonlp.dictionary.common;

import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.corpus.io.IOUtil;
import com.sudonlp.corpus.tag.Nature;
import com.sudonlp.dictionary.CoreDictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

import static com.sudonlp.utility.Predefine.logger;

public class LoadDicUtil {
    public static boolean loadDic(String path, DoubleArrayTrie<CoreDictionary.Attribute> dic, String name) {
        logger.info("开始加载" + name + ":" + path);
        TreeMap<String, CoreDictionary.Attribute> map = new TreeMap<String, CoreDictionary.Attribute>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(IOUtil.newInputStream(path), "UTF-8"));
            String line;
            long start = System.currentTimeMillis();
            while ((line = br.readLine()) != null) {
                String param[] = line.split("\t");
                int natureCount = (param.length - 1) / 2;
                CoreDictionary.Attribute attribute = new CoreDictionary.Attribute(natureCount);
                for (int i = 0; i < natureCount; ++i) {
                    attribute.nature[i] = Nature.create(param[1 + 2 * i]);
                    attribute.frequency[i] = Integer.parseInt(param[2 + 2 * i]);
                    attribute.totalFrequency += attribute.frequency[i];
                }
                map.put(param[0], attribute);
            }
            logger.info(name + "读入词条" + map.size() + "，耗时" + (System.currentTimeMillis() - start) + "ms");
            br.close();
            dic.build(map);
            logger.info(name + "加载成功:" + dic.size() + "个词条");
            // 这里暂时只从txt文本当中读取信息
        } catch (FileNotFoundException e) {
            logger.warning(name + path + "不存在！" + e);
            return false;
        } catch (IOException e) {
            logger.warning(name + path + "读取错误！" + e);
            return false;
        }
        return true;
    }

//    // 此部分代码用于测试 字典是否读取成功？
//    public static void main(String [] args){
//        DoubleArrayTrie<CoreDictionary.Attribute> trie = new DoubleArrayTrie<CoreDictionary.Attribute>();
//        LoadDicUtil.loadDic("C:\\work\\HanLp\\SudoNLP\\data\\dictionary\\medicine\\drugNames.txt", trie, "药名");
//        System.out.println(trie.get("新达罗颗粒剂").nature[0]);
//    }
}
