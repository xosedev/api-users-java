package com.ytulink.user.config;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Configuration
public class FirebaseConfig {

	@Bean
	public DatabaseReference firebaseDatabse() {
		DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
		return firebase;
	}

	@Value("${rs.pscode.firebase.database.url}")
	private String databaseUrl;

	@Value("${rs.pscode.firebase.config.path}")
	private String configPath;

	@PostConstruct
	public void init() throws Exception {
		InputStream inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream(configPath);

				FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(inputStream))
				  .setDatabaseUrl(databaseUrl)
				  .build();
				FirebaseApp.initializeApp(options);
	}
}