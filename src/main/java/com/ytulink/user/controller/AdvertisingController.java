package com.ytulink.user.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ytulink.user.dto.AdvertisingDTO;
import com.ytulink.user.helper.AdvertisingHelper;
import com.ytulink.user.helper.HelperValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */

@RestController
@RequestMapping(path = "/")
public class AdvertisingController {



	static Logger logger = LogManager.getLogger(AdvertisingController.class);

	
	@Autowired
	HelperValidation helperValidation;

	
	@Autowired
	AdvertisingHelper advertisingHelper;
	
	private static final String CONTENT_LENGTH = "Content-Length";
	
	
	/**
	 * Metodo encargado registrar nuevo comentario
	 * 
	 * @param createShortcut
	 * @return
	 */
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PostMapping(value = "/advertising")
	public @ResponseBody ResponseEntity<Object> createAdvertising(@RequestBody AdvertisingDTO advertisingDTO) {
		logger.info("Inicio  createAdvertising");
		
		try {
			Optional<AdvertisingDTO> dto = Optional.ofNullable(advertisingHelper.create(advertisingDTO));
			if (dto.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(dto.get());
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio createAdvertising "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
		

	}
	
	

	@GetMapping(value = "/advertising/{userId}")
	public @ResponseBody ResponseEntity<Object> advertisingByUser(@PathVariable("id") Long userId ,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		logger.info("Inicio advertisingByUser");
		try {
			Page<AdvertisingDTO> dto = advertisingHelper.findAllByUserId(userId,pageNo, pageSize, sortBy);
			if (dto.getContent().isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
			}

			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio shortcutByUser "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

	}

	@GetMapping(value = "/advertising")
	public @ResponseBody ResponseEntity<Object> findAll(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		logger.info("Inicio findAll");
		try {
			Page<AdvertisingDTO> dto = advertisingHelper.findAll(pageNo, pageSize, sortBy);
			if (dto.getContent().isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
			}

			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio findAll "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

	}
	

	
	/**
	 * Metodo encargado realizar el borradode un comentario
	 * 
	 * @param user
	 * @return
	 */
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PutMapping(value = "/advertising/activate/{id}")
	public @ResponseBody ResponseEntity<Object> activate(@PathVariable("id") Long id) {
		logger.info("Inicio activate");
		try {
			Optional<AdvertisingDTO> dto = Optional.ofNullable(advertisingHelper.activate(id));
			if (dto.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(dto.get());
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio activate "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PutMapping(value = "/advertising/inactivate/{id}")
	public @ResponseBody ResponseEntity<Object> inactivate(@PathVariable("id") Long id) {
		logger.info("Inicio inactivate");
		try {
			Optional<AdvertisingDTO> dto = Optional.ofNullable(advertisingHelper.inactivate(id));
			if (dto.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(dto.get());
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio inactivate "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

	}
	

}