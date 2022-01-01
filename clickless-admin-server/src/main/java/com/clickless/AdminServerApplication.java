package com.clickless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author clickless
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class AdminServerApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(AdminServerApplication.class, args);
        System.out.println(
        "--------------------------------------------------\n" +
        "\n" +
        "    Clickless Application started successfully!   \n" +
        "\n" +
        "--------------------------------------------------");
    }
}
