package com.phaeris.rolex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wyh
 * @since 2024/7/19
 */
@MapperScan("com.phaeris.rolex.mapper")
@SpringBootApplication
public class RolexApplication {

    public static void main(String[] args) {
        SpringApplication.run(RolexApplication.class, args);
    }
}
