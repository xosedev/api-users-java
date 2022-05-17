package com.ytulink.user.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ytulink.user.dto.TypeShortCutDTO;
import com.ytulink.user.entity.TypeShortCut;
import com.ytulink.user.helper.HelperValidation;
import com.ytulink.user.helper.TypeShortCutHelper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */

@RestController
@RequestMapping(path = "/")
public class TypeShortCutController {



	static Logger logger = LogManager.getLogger(TypeShortCutController.class);

	
	@Autowired
	HelperValidation helperValidation;

	
	@Autowired
	TypeShortCutHelper typeShortCutHelper;
	
	private static final String CONTENT_LENGTH = "Content-Length";

	private static final String NO_DATA = "No se encontraron registros";
	
	
	/**
	 * Metodo encargado registrar typeShortCut 
	 * 
	 * @param user
	 * @return
	 */
	//@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PostMapping(value = "/type-shortcut")
	public @ResponseBody ResponseEntity<?> newTypeShortCutDTO(@RequestBody TypeShortCutDTO typeShortCutDTO ) {
		logger.info("Inicio  registerSuggetion");

		try {
			List<String> erros = helperValidation.validarObjeto(typeShortCutDTO);
			
			if (!erros.isEmpty()) {
				logger.warn("Validation errors encountered: {}", erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
			} else {
				return typeShortCutHelper.saveOrUpdate(typeShortCutDTO);
			}
		} catch (Exception e) {
			logger.error("Ocurrió un error al llamar al servicio de users method registerSuggetion"
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}
		
	
	
	/**
	 * Metodo encargado registrar nuevo comentario
	 * 
	 * @param createShortcut
	 * @return
	 */
	@GetMapping(value = "/type-shortcut")
	public @ResponseBody ResponseEntity<Object> findAllTypeShortcut() {
		logger.info("Inicio  findAllTypeShortcut");
		

		try {
			List<TypeShortCut> countries = typeShortCutHelper.getAllTypeShortCut();
			if (countries.size() == 0) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(NO_DATA);
			}
			return ResponseEntity.status(HttpStatus.OK).body(countries);
		} catch (Exception e) {
			logger.error("Ocurrió un error al llamar al servicio  method findAllTypeShortcut"
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
	@PutMapping(value = "/type-shortcut/activate/{id}")
	public @ResponseBody ResponseEntity<Object> activate(@PathVariable("id") Long id) {
		logger.info("Inicio activate");
		try {
			Optional<TypeShortCutDTO> dto = Optional.ofNullable(typeShortCutHelper.activate(id));
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
	@PutMapping(value = "/type-shortcut/inactivate/{id}")
	public @ResponseBody ResponseEntity<Object> inactivate(@PathVariable("id") Long id) {
		logger.info("Inicio inactivate");
		try {
			Optional<TypeShortCutDTO> dto = Optional.ofNullable(typeShortCutHelper.inactivate(id));
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