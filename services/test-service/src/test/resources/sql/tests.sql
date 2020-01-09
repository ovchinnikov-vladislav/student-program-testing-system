INSERT INTO test (id, source_code, description, created_at, updated_at, status, id_task, id_user)
VALUES (random_uuid(), 'import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class AppTest {

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
                output = new ByteArrayOutputStream();
                System.setOut(new PrintStream(output));
                input = new ByteArrayInputStream(builder.toString().getBytes());
                System.setIn(input);
                NameTask.main(null);
                String out = output.toString();
                String next = "";
                Assert.assertEquals(String.format("%f + %f = %f", i, j), output.toString(), (i + j) + "\r\n");
            }
        }
    }
}', '', CURRENT_DATE(), CURRENT_DATE(), 0, random_uuid(), random_uuid()),
(random_uuid(), 'import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;

public class AppTest {

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
}', '', CURRENT_DATE(), CURRENT_DATE(), 0, random_uuid(), random_uuid()),
(random_uuid(), 'import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;

public class AppTest {

}', '', CURRENT_DATE(), CURRENT_DATE(), 0, random_uuid(), random_uuid());