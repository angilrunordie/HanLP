package com.sudonlp.corpus.io;

import com.sudonlp.HanLP;
import com.sudonlp.dictionary.stopword.CoreStopWordDictionary;
import com.sudonlp.utility.Predefine;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class IIOAdapterTest extends TestCase
{
    /**
     * 这个方法演示通过IOAdapter阻止HanLP加载和生成缓存
     *
     * @throws Exception
     */
    public void testReturnNullInIOAdapter() throws Exception
    {
        HanLP.Config.IOAdapter = new FileIOAdapter()
        {
            @Override
            public InputStream open(String path) throws FileNotFoundException
            {
                if (path.endsWith(Predefine.BIN_EXT)) return null;
                return super.open(path);
            }

            @Override
            public OutputStream create(String path) throws FileNotFoundException
            {
                if (path.endsWith(Predefine.BIN_EXT)) return null;
                return super.create(path);
            }
        };

        HanLP.Config.enableDebug(false);
        assertEquals(true, CoreStopWordDictionary.contains("的"));
    }
}