package com.gxcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gxcy.mina.MinaServerTest;

@SpringBootApplication
public class GxcyserverApplication {

    public static void main(String[] args) {
    	MinaServerTest test = new MinaServerTest();
    	test.MinaServerTest(args);
        SpringApplication.run(GxcyserverApplication.class, args);
    }

}
