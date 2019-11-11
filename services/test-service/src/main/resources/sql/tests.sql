INSERT INTO test (source_code, description, create_date, id_task, id_user)
VALUES ('import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class SourceTest {

    private ByteArrayOutputStream output;
    private ByteArrayInputStream input;
    private PrintStream oldO;
    private InputStream oldI;

    @Before
    public void setUpStreams() {
        oldO = System.out;
        oldI = System.in;

    }

    @After
    public void cleanUpStreams() {
        System.setOut(oldO);
        System.setIn(oldI);
    }

    @Test
    public void testMain() throws Exception {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                StringBuilder builder = new StringBuilder();
                builder.append(i).append("\n")
                .append(j).append("\n");
                input = new ByteArrayInputStream(builder.toString().getBytes());
                System.setIn(input);
                output = new ByteArrayOutputStream();
                System.setOut(new PrintStream(output));
                App.main();
                String out = output.toString().replace(''\n'', ''\0'').replace(''\r'', ''\0'').trim();
                String result = (i + j) + "";
                Assert.assertEquals("Неверный результат", out, result.trim());
            }
        }
    }
}', '', CURRENT_DATE(), 1, 1),
('import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;

public class SourceTest {

    @Test
    public void testAgeNotNull() throws Exception {
        Field age = App.class.getDeclaredField("age");
        Assert.assertNotNull("age не обявлено", age);
    }

    @Test
    public void testNameNotNull() throws Exception {
        Field name = App.class.getDeclaredField("name");
        Assert.assertNotNull("name не объявлено", name);
    }

    @Test
    public void testTypeName() throws Exception {
        Field age = App.class.getDeclaredField("age");
        Assert.assertEquals("age не int типа", age.getType(), int.class);
    }

    @Test
    public void testTypeAge() throws Exception {
        Field name = App.class.getDeclaredField("name");
        Assert.assertEquals("name не String типа", name.getType(), String.class);
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertEquals("Вы объявлили больше или недостаточно полей", App.class.getDeclaredFields().length, 2);
    }
}', '', CURRENT_DATE(), 2, 1);