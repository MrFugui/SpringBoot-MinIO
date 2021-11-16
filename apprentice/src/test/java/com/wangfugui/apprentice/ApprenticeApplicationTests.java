package com.wangfugui.apprentice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.wangfugui.apprentice.dao.domain.User;
import com.wangfugui.apprentice.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApprenticeApplicationTests {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${base.javaDir}")
    private String javaDir;
    @Value("${base.mapperDir}")
    private String mapperDir;


    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.lambda().eq(User::getUsername,"fugui");
        List<User> user = userMapper.selectList(objectQueryWrapper);
        System.out.println(user);
    }

    /** 生成代码
     * @Param: []
     * @return: void
     * @Author: MaSiyi
     * @Date: 2021/11/13
     */
    @Test
    void generateCode() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("MrFugui") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()
                            .commentDate("yyyy-MM-dd")
                            .outputDir(javaDir); // 指定输出目录

                })
                .packageConfig(builder -> {
                    builder.parent("com.wangfugui") // 设置父包名
                            .moduleName("apprentice") // 设置父包模块名
                            .entity("dao.domain")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("dao.mapper")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperDir)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("function"); // 设置需要生成的表名
                })
                .execute();
    }



}
