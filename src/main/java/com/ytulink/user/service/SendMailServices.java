package com.ytulink.user.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ytulink.user.dto.RequestMail;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */

@Service
public class SendMailServices {

	static Logger logger = LogManager.getLogger(SendMailServices.class);
	
	/**
	 * @param request
	 * @param endPoint
	 * @return
	 */
	public ResponseEntity<String> send(RequestMail request, String endPoint) {
		try {
			logger.error("envio de correo");
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = null;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<RequestMail> requestEntity = new HttpEntity<>(request, headers);
			response = restTemplate.postForEntity(endPoint, requestEntity, String.class);
			return response;
		} catch (HttpStatusCodeException exception) {
			logger.error(exception.getLocalizedMessage());
			return new ResponseEntity<>(null, exception.getResponseHeaders(), exception.getStatusCode());

		}
	}
}