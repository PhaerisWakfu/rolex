package com.phaeris.rolex;

import com.phaeris.rolex.RolexApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

/**
 * @author wyh
 * @since 2021/4/9 11:37
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RolexApplication.class)
//@AutoConfigureMockMvc
public abstract class BaseTest {

    protected static MockMvc mockMvc;

    @BeforeAll
    static void setUp(WebApplicationContext context) {
        //解决@AutoConfigureMockMvc自动装配的mockMvc字符集默认是ISO-8859-1导致中文乱码的问题
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter((request, response, chain) -> {
                    request.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    /**
     * 常用assert 输出内容同时断言
     *
     * @param data 输出内容
     */
    protected <T> void assertOk(T data) {
        Assertions.assertDoesNotThrow(() -> System.out.println(data));
    }
}
