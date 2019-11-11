package rsoi.lab2.dservice;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DiscoveryServiceApp.class)
public class Test {

    @Autowired
    private DiscoveryServiceApp discoveryServiceApp;

    @org.junit.Test
    public void testApplication() {
        Assert.assertNotNull(discoveryServiceApp);
    }
}
