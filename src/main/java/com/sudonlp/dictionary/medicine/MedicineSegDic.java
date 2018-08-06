package com.sudonlp.dictionary.medicine;

import com.sudonlp.collection.trie.DoubleArrayTrie;
import com.sudonlp.dictionary.CoreDictionary;

/**
 * 本类用于存放：
 * 1. 医疗词汇
 * 2. 医疗词汇的公共子集
 * 3. 医疗词汇的非公共子集
 */

public class MedicineSegDic {
    public static DoubleArrayTrie<CoreDictionary.Attribute> wordDic = new DoubleArrayTrie<CoreDictionary.Attribute>();
    public static DoubleArrayTrie<CoreDictionary.Attribute> wordCommonSubset = new DoubleArrayTrie<CoreDictionary.Attribute>();
    public static DoubleArrayTrie<CoreDictionary.Attribute> wordNotCommonSubset = new DoubleArrayTrie<CoreDictionary.Attribute>();
    public final static String medicine_word_path = "C:/work/HanLp/SudoNLP/data/dictionary/medicine/medicineWordDictionary.txt";
    public final static String medicine_word_common_subset_path = "C:/work/HanLp/SudoNLP/data/dictionary/medicine/medicine_common_subset.txt";
    public final static String medicine_word_not_common_subset_path = "C:/work/HanLp/SudoNLP/data/dictionary/medicine/medicine_not_common_subset.txt";

//    private static boolean loadDic(String path, DoubleArrayTrie<CoreDictionary.Attribute> dic, String name) {
//        logger.info("开始加载" + name + ":" + medicine_word_path);
//        TreeMap<String, CoreDictionary.Attribute> map = new TreeMap<String, CoreDictionary.Attribute>();
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(IOUtil.newInputStream(path), "UTF-8"));
//            String line;
//            long start = System.currentTimeMillis();
//            while ((line = br.readLine()) != null) {
//                String param[] = line.split("\\s");
//                int natureCount = (param.length - 1) / 2;
//                CoreDictionary.Attribute attribute = new CoreDictionary.Attribute(natureCount);
//                for (int i = 0; i < natureCount; ++i) {
//                    attribute.nature[i] = Nature.create(param[1 + 2 * i]);
//                    attribute.frequency[i] = Integer.parseInt(param[2 + 2 * i]);
//                    attribute.totalFrequency += attribute.frequency[i];
//                }
//                map.put(param[0], attribute);
//            }
//            logger.info(name + "读入词条" + map.size() + "，耗时" + (System.currentTimeMillis() - start) + "ms");
//            br.close();
//            wordDic.build(map);
//            logger.info(name + "加载成功:" + wordDic.size() + "个词条");
//            // 这里暂时只从txt文本当中读取信息
//        } catch (FileNotFoundException e) {
//            logger.warning(name + path + "不存在！" + e);
//            return false;
//        } catch (IOException e) {
//            logger.warning(name + path + "读取错误！" + e);
//            return false;
//        }
//        return true;
//    }
}
