package cn.np.boots.test.common;

import cn.np.boots.EnableNpBoots;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {BaseTest.class})
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"cn.np.*"})
@EnableNpBoots
public class BaseTest {
}
