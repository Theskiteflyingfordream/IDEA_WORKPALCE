package mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Mybatis逆向工程
 * @Description TODO
 * @DATE 2021/9/16 9:09
 */
public class mbgTest {

    public static void main(String args[]) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        /*注意不能以/开头，此时使用相对路径基于工程而不是模块*/
        File configFile = new File("MyBlog/src/mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
