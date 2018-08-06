package com.sudonlp.dictionary.other;

import com.sudonlp.SudoNLP;
import com.sudonlp.utility.TextUtility;
import junit.framework.TestCase;

public class CharTypeTest extends TestCase
{
    public void testNumber() throws Exception
    {
//        for (int i = 0; i <= Character.MAX_VALUE; ++i)
//        {
//            if (CharType.get((char) i) == CharType.CT_NUM)
//                System.out.println((char) i);
//        }
        assertEquals(CharType.CT_NUM, CharType.get('1'));

    }

    public void testWhiteSpace() throws Exception
    {
//        CharType.type[' '] = CharType.CT_OTHER;
        String text = "1 + 2 = 3; a+b= a + b";
        assertEquals("[1/m,  /w, +/w,  /w, 2/m,  /w, =/w,  /w, 3/m, ;/w,  /w, a/nx, +/w, b/nx, =/w,  /w, a/nx,  /w, +/w,  /w, b/nx]", SudoNLP.segment(text).toString());
    }

    public void testTab() throws Exception
    {
        assertTrue(TextUtility.charType('\t') == CharType.CT_DELIMITER);
        assertTrue(TextUtility.charType('\r') == CharType.CT_DELIMITER);
        assertTrue(TextUtility.charType('\0') == CharType.CT_DELIMITER);

//        System.out.println(SudoNLP.segment("\t"));
    }
}