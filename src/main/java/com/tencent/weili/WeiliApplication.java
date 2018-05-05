package com.tencent.weili;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.tencent.weili.dao")
@EnableTransactionManagement
public class WeiliApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeiliApplication.class, args);
	}

}
