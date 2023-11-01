package cn.edu.neu.mgzmsys.common;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) throws IOException {
        generate();
    }
    private  static void generate() throws IOException {
        File directory = new File("");
        String courseFile = directory.getCanonicalPath();

//            **********************必须修改的数据库连接参数**************
        String dataUrl="jdbc:mysql://39.101.65.147:3306/mgzmsys?serverTimezone=GMT%2b8";
        String userName="msn";
        String password="password";

        FastAutoGenerator.create(dataUrl,userName ,password )
                .globalConfig(builder -> {
                    builder.author("team15") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(courseFile+"\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cn.edu.neu.mgzmsys") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, courseFile+"\\src\\main\\resources\\cn\\edu\\neu\\mgzmsys\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
//                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器

//                    **********************必须修改的表名**************
                    builder.addInclude("child").addInclude("volunteer").addInclude("conversation").addInclude("message").addInclude("task");// 设置需要生成的表名


//                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

