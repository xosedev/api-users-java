package com.ytulink.user.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.ytulink.user.dto.NewUserDTO;
import com.ytulink.user.dto.OkDTO;
import com.ytulink.user.dto.RequestActivate;
import com.ytulink.user.dto.RequestreCoverPass;
import com.ytulink.user.dto.UserAccountDTO;
import com.ytulink.user.dto.UserDTO;
import com.ytulink.user.dto.UserImgDTO;
import com.ytulink.user.exception.YtulinkException;
import com.ytulink.user.helper.HelperValidation;
import com.ytulink.user.helper.UserHelper;
import com.ytulink.user.service.FileStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */

@RestController
@RequestMapping(path = "/")
public class UserController {

	private static final String CONTENT_LENGTH = "Content-Length";

	static Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	UserHelper userHelper;

	@Autowired
	HelperValidation helperValidation;

	@Autowired
	FileStorageService fileStorageService;

	/**
	 * Metodo encargado registrar nuevo comentario
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = "/register-user")
	public @ResponseBody ResponseEntity<Object> registerUser(@RequestBody NewUserDTO userDTO) {
		logger.info("Inicio  registerUser");

		try {
			List<String> erros = helperValidation.validarObjeto(userDTO);

			if (!erros.isEmpty()) {
				logger.warn("Validation errors encountered: {}.", erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
			} else {
				return userHelper.newUser(userDTO);
			}
		} catch (YtulinkException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("Ocurrió un error al llamar al servicio de users method registerUser"
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@GetMapping(value = "/user")
	public @ResponseBody ResponseEntity<Object> getAlluser(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		logger.info("Inicio getAlluser");
		try {
			Page<UserDTO> dto = userHelper.findAll(pageNo, pageSize, sortBy);
			if (dto.getContent().isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
			}

			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio getAlluser "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

	}

	/**
	 * Metodo encargado de realizar una consulta por fire base id
	 * 
	 * @param idFireBase
	 * @return
	 */
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@GetMapping(value = "userFireBase/{id}")
	public ResponseEntity<Object> oneUsersFireBaseID(@PathVariable("id") String idFireBase) {

		logger.info("Inicio oneUsersFireBaseID");

		try {
			 Optional<UserAccountDTO>  user = userHelper.getOneUsersFireBase(idFireBase);
			if (!user.isPresent()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
			}
			return ResponseEntity.status(HttpStatus.OK).body(user.get());
		} catch (Exception e) {
			logger.error("Ocurrió un error al llamar al servicio users method oneUsersFireBaseID"
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}

	/**
	 * Metodo encargado de realizar una consulta por usuario
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "user-id/{id}")
	public ResponseEntity<Object> oneUser(@PathVariable("id") Long id) {

		logger.info("Inicio oneUser");

		try {
			Optional<UserImgDTO> user = userHelper.getOneUsers(id);
			if (!user.isPresent()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
			}
			return ResponseEntity.status(HttpStatus.OK).body(user.get());
		} catch (Exception e) {
			logger.error("Ocurrió un error al llamar al servicio users method oneUser"
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * Metodo encargado de realizar una consulta por usuario
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "user-name/{name}")
	public ResponseEntity<Object> getUserByUserName(@PathVariable("name") String name) {

		logger.info("Inicio getUserByName");

		try {
			Optional<UserImgDTO> user = userHelper.getUserByUserName(name);
			if (!user.isPresent()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).header(CONTENT_LENGTH, "0").build();
			}
			return ResponseEntity.status(HttpStatus.OK).body(user.get());
		} catch (Exception e) {
			logger.error("Ocurrió un error al llamar al servicio getUserByName"
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}

	@Deprecated
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PutMapping(value = "/user")
	public @ResponseBody ResponseEntity<Object> modifytUser(@RequestBody UserDTO userDTO) {
		logger.info("Inicio modifytUser");
		try {

			// TODO: validar si se esta ocupando o modificar el llamado newUser

			Object resp = null;
			HttpStatus status = null;

			List<String> erros = helperValidation.validarObjeto(userDTO);

			// validacion extra solo para update
			if (userDTO.getId() == null) {
				erros.add("Id no puede ser Nulo");
			}

			if (!erros.isEmpty()) {
				resp = erros.get(0);
				logger.warn("Validation errors encountered: {}.", resp);
				status = HttpStatus.BAD_REQUEST;
			} else {
//				resp = userHelper.newUser(userDTO);
				status = HttpStatus.OK;
			}

			return ResponseEntity.status(status).body(resp);

		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio modifytUser "
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
	@PutMapping(value = "/user/activate/{id}")
	public @ResponseBody ResponseEntity<Object> activate(@PathVariable("id") Long id) {
		logger.info("Inicio activate");
		try {
			Optional<UserDTO> dto = Optional.ofNullable(userHelper.activate(id));
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
	@PutMapping(value = "/user/inactivate/{id}")
	public @ResponseBody ResponseEntity<Object> inactivate(@PathVariable("id") Long id) {
		logger.info("Inicio inactivate");
		try {
			Optional<UserDTO> dto = Optional.ofNullable(userHelper.inactivate(id));
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

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PostMapping(value = "/update-user")
	public @ResponseBody ResponseEntity<Object> updateUser(@RequestBody UserImgDTO userImgDTO) {
		logger.info("Inicio updateUser");

		try {

			List<String> erros = helperValidation.validarObjeto(userImgDTO);

			if (erros.isEmpty()) {

				return ResponseEntity.status(HttpStatus.OK).body(fileStorageService.update(userImgDTO));
			} else {

				logger.warn("Validation errors encountered: {}", erros);

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
			}

		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio createLinkImg "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

	}

	@PostMapping(value = "/activate-account")
	public @ResponseBody ResponseEntity<Object> activateAccount(@RequestBody RequestActivate activate) {
		logger.info("Inicio createToken");
		try {

			List<String> erros = helperValidation.validarObjeto(activate);

			if (erros.isEmpty()) {
				userHelper.activateAccount(activate);
				return ResponseEntity.status(HttpStatus.OK).body("Bienvenido a Ytulink.com ");

			} else {

				logger.warn("Validation errors encountered: {}", erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.toString());
			}

		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio createToken "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}

	}
	@PostMapping(value = "/recover-account", produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> recoverAccount(@RequestBody RequestActivate activate) throws Exception {
		logger.info("Inicio recover-account");
		try {

			List<String> erros = helperValidation.validarObjeto(activate);

			if (erros.isEmpty()) {
				OkDTO val = new OkDTO();
				val.setOk(userHelper.recoverAccount(activate.getEmail()));
				return ResponseEntity.status(HttpStatus.OK).body(new OkDTO());

			} else {

				logger.warn("Validation errors encountered: {}", erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.toString());
			}

		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio recover-account "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}

	@PostMapping(value = "/recover-password")
	public @ResponseBody ResponseEntity<Object> recoverPass(@RequestBody RequestreCoverPass recover) throws Exception {
		logger.info("Inicio recover-password");
		try {

			List<String> erros = helperValidation.validarObjeto(recover);

			if (erros.isEmpty()) {

				
				OkDTO val = new OkDTO();
				val.setOk(userHelper.recoverPass(recover.getPassword(), recover.getEmail(), recover.getConfirm(), recover.getCode()));
				
				return ResponseEntity.status(HttpStatus.OK)
						.body( val );

			} else {

				logger.warn("Validation errors encountered: {}", erros);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.toString());
			}

		} catch (YtulinkException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		} catch (Exception e) {

			logger.error("Ocurrió un error al llamar al servicio recover-password "
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}

}