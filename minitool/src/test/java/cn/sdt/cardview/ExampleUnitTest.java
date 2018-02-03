package cn.sdt.cardview;



import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void extTest() throws Exception {
        // Context of the app under test.
        String fileName="google_map.mp3";
        int index = fileName.lastIndexOf('.');
        String ext = fileName.substring(index + 1, fileName.length());
        System.out.println(ext);
    }
}