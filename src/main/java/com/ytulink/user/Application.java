package com.ytulink.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import com.ytulink.user.config.FileStorageProperties;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@ComponentScan({ "com.ytulink.user" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}