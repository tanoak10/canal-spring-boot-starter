package com.example.canatest;

import io.github.tanoak10.starter.canal.config.CanalClientConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CanalTestApplicationTests {

	@Resource
	private CanalClientConfiguration configuration;

	@Test
	public void contextLoads() {
	}

}
