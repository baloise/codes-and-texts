package ch.basler.cat.generator.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileUtilTest {

    @Test
    public void writeAndRead() throws Exception{
        String content = "hello cat";
        FileUtil.write("gen", "FileUtilTest.txt", content);
        String read = FileUtil.read("./gen", "FileUtilTest.txt");
        assertEquals(content, read);
    }
}