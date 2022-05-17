package com.ytulink.user.helper;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.ytulink.user.componet.Sha1HexComponent;
import com.ytulink.user.dto.NewUserDTO;
import com.ytulink.user.dto.RequestActivate;
import com.ytulink.user.dto.RequestMail;
import com.ytulink.user.dto.RequestToken;
import com.ytulink.user.dto.UserAccountDTO;
import com.ytulink.user.dto.UserDTO;
import com.ytulink.user.dto.UserImgDTO;
import com.ytulink.user.entity.Desktop;
import com.ytulink.user.entity.DesktopSubCategory;
import com.ytulink.user.entity.Image;
import com.ytulink.user.entity.Plan;
import com.ytulink.user.entity.TypePlan;
import com.ytulink.user.entity.User;
import com.ytulink.user.exception.YtulinkException;
import com.ytulink.user.mapper.ResponseUserMapper;
import com.ytulink.user.mapper.UserAccountMapper;
import com.ytulink.user.mapper.UserImgMapper;
import com.ytulink.user.mapper.UserMapper;
import com.ytulink.user.repository.DesktopSubCategoryRepository;
import com.ytulink.user.repository.IDesktopRepository;
import com.ytulink.user.repository.IImageRepository;
import com.ytulink.user.repository.IPlanRepository;
import com.ytulink.user.repository.ITypePlanRepository;
import com.ytulink.user.repository.IUserRepository;
import com.ytulink.user.service.SendMailServices;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */

@Component
public class UserHelper {
	private static final String NOTIFICACIÓN_YTULINK = "Notificación ytulink.com";


	static Logger logger = LogManager.getLogger(UserHelper.class);

	
	@Autowired
	IUserRepository iUserRepository;

	@Autowired
	UserMapper userMapper;

	@Autowired
	SendMailServices sendMailServices;
	
	@Autowired
	UserAccountMapper userAccountMapper;
	
	@Autowired
	ResponseUserMapper responseUserMapper;

	@Autowired
	IDesktopRepository desktopRepository;
	
	@Autowired
	DesktopSubCategoryRepository desktopSubCategoryRepository;
	
	@Autowired
	IImageRepository imageRepository;
	
	@Autowired
	UserImgMapper userImgMapper;

	@Autowired
	ITypePlanRepository typePlanRepository;
	
	
	@Autowired
	IPlanRepository planRepository;
	
	
	@Autowired 
	Sha1HexComponent sha1HexComponent;
	
	
	@Value("${url.service.send.mail}")
	private String sendMail;
	
	@Value("${url.img.default}")
	private String defaultImg;
	
	@Value("${url.img.default.cover.page}")
	private String defaultCoverPage;

	/**
	 * Metodo encargado registrar nuevo usuario
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Object> newUser(NewUserDTO userDTO) throws Exception {
		User userEntity = null;
		
		UserDTO	userNewDTO = null;
		

		if (userDTO == null) {
			logger.error("Debe completar los campos requeridos");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe completar los campos requeridos");
		}
		
        try{
        	Long.parseLong(userDTO.getUserName());
    		throw new YtulinkException("Nombre de usuario no puede contener solo números. ".concat(userDTO.getUserName()));
        }
        catch (NumberFormatException ex){
			logger.info("Nombre de usuario correcto Alfanumérico");
        }
		
		/* creaciòn de usuario firebase */
		RequestToken tokenRe = new RequestToken();
		tokenRe.setEmail(userDTO.getEmail());
		tokenRe.setPassword(userDTO.getPassword());
		
		logger.log(Level.INFO , "el usuario es {}.",tokenRe );
		
		String uuId = createNewUserFirebase(tokenRe);

		userEntity = new User();
		userEntity.setActivate(false);
		userEntity.setRegisterComplete(false);
			
		userEntity.setIdFireBase(uuId);

		try {
            
			Optional<User> validarUser = iUserRepository.findByEmail(userDTO.getEmail());

			if (validarUser.isPresent()) {
				throw new Exception("La dirección de correo electrónico que ha ingresado ya está registrada.");
			}
			
			userEntity.setUrlImgCoverPage(defaultCoverPage);

			if (userDTO.getEmail() != null) {
				userEntity.setEmail(userDTO.getEmail());
			}
			
			
			
			if (userDTO.getUserName() != null) {
				
				Optional<User> userName = iUserRepository.findByUserName(userDTO.getUserName());

				if (userName.isPresent()) {
					throw new YtulinkException("El nombre de Usuario ingresado ya existe.");
				}
				
		
				userEntity.setUserName(userDTO.getUserName());
			}
			
			
			if (userDTO.getIdFireBase() != null) {
				userEntity.setIdFireBase(userDTO.getIdFireBase());
			}
			if (userDTO.getName() != null) {
				userEntity.setName(userDTO.getName());
			}
			if (userDTO.getBirthday() != null) {
				userEntity.setBirthday(userDTO.getBirthday());
			}
			if (userDTO.getLastName() != null) {
				userEntity.setLastName(userDTO.getLastName());
			}
			if (userDTO.getPhoneCode() != null) {
				userEntity.setPhoneCode(userDTO.getPhoneCode());
			}
			if (userDTO.getPhone() != null) {
				userEntity.setPhone(userDTO.getPhone());
			}
			if (userDTO.getSex() != null) {
				userEntity.setSex(userDTO.getSex());
			}

            /**
             * Se asigna escritorio por defecto al nuevo usuario
             */
			
			Optional<DesktopSubCategory> desktopSub = desktopSubCategoryRepository.findById(1L);
			
            Desktop desktopEntity = new Desktop(); 
            desktopEntity.setDesktopSubCategory(desktopSub.get());
            desktopEntity.setName("Principal");
            desktopEntity.addUser(userEntity);
            desktopEntity.setCreateUser(userEntity);
            desktopEntity.setMainDesk(true);
            desktopEntity.setIconDesk(userDTO.getIconDesk());
            
         
            Desktop des = desktopRepository.save(desktopEntity);
            
            
            Image image = new Image();
            image.setUser(des.getCreateUser());
            image.setUrlImg(defaultImg);
            image.setName("default-user");
            image.setPrincipal(Boolean.TRUE);
            imageRepository.save(image);
            
            
            /* Asignación de plan basico*/
			Optional<TypePlan> typePlan = typePlanRepository.findById(1L);
			if (!typePlan.isPresent()) {
				throw new IllegalArgumentException(
						String.format("!Plan no se encuentra en nuestros registros %s!", 1L));
			}
			
			

			Plan plan = new Plan();
			plan.setStartDate(new Date());
			plan.setEndDate(new Date());
			plan.setUser(des.getCreateUser());
			plan.setTypePlan(typePlan.get());
			plan.setUpdateDate(new Date());

			planRepository.save(plan);
			
			
	      
			RequestMail requestMail = new RequestMail();
			requestMail.setMessage("https://ytulink.com/activate?email=" + userEntity.getEmail());
			requestMail.setSubject(NOTIFICACIÓN_YTULINK);
			requestMail.setToEmail(userEntity.getEmail());
			requestMail.setTemplate("welcome");
			sendMailServices.send(requestMail, sendMail);
						

		} catch (Exception e) {
			logger.error("Ocurrió un error al registrar el usuario"
					.concat(" : ".concat(e.getMessage().concat(e.getLocalizedMessage()))));
			throw new YtulinkException(e.getLocalizedMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(userNewDTO);
	}

	/**
	 * Metodo encargado de realizar una consulta por fire base id
	 * 
	 * @param idFireBase
	 * @return
	 * @throws Exception
	 */
	public Optional<UserAccountDTO> getOneUsersFireBase(String idFireBase) throws Exception {

		 Optional<UserAccountDTO> optionalUsuario = iUserRepository.findByIdFireBase(idFireBase).map(userAccountMapper::toDto);

		if (!optionalUsuario.isPresent()) {
			logger.debug("FIREBASE NO EXISTE: Firebase no existe en nuestros registros");
			throw new Exception("Firebase no existe en nuestros registros");
		}

		if (!optionalUsuario.get().getIdFireBase().equalsIgnoreCase(idFireBase)) {
			logger.debug("FIREBASE EXISTE: Firebase ya existe en nuestros registros");
			throw new Exception("Firebase ya existe en nuestros registros");
		}

		return optionalUsuario;

	}
	
	public String  createNewUserFirebase(RequestToken requestToken) throws FirebaseAuthException {

		UserRecord userRecord = null;

		CreateRequest request = new CreateRequest().setEmail(requestToken.getEmail()).setEmailVerified(false)
				.setPassword(requestToken.getPassword()).setDisabled(true);

		userRecord = FirebaseAuth.getInstance().createUser(request);
		logger.info("Successfully created new user: {}.", userRecord.getUid());
		
		return userRecord.getUid();

	}
	

	public void activateAccount(RequestActivate activate) throws FirebaseAuthException {


		UserRecord userFirebase = null;


		userFirebase = FirebaseAuth.getInstance().getUserByEmail(activate.getEmail());
		if (userFirebase == null) {
			throw new YtulinkException("Usuario no registrado.");
		}

		UpdateRequest request = new UpdateRequest(userFirebase.getUid()).setDisabled(false);
		userFirebase = FirebaseAuth.getInstance().updateUser(request);

		logger.info("Successfully updated user {}.", userFirebase.getUid());

		Optional<User> validarUser = iUserRepository.findByEmail(activate.getEmail());
		if (!validarUser.isPresent()) {
			throw new YtulinkException("Usuario no registrado.");
		}
		validarUser.get().setActivate(true);
		
		iUserRepository.save(validarUser.get());
		
		
		RequestMail requestMail = new RequestMail();

		requestMail.setMessage("Tu cuenta ha sido activada satisfactoriamente.");
		requestMail.setSubject(NOTIFICACIÓN_YTULINK);
		requestMail.setToEmail(activate.getEmail());
		requestMail.setTemplate("validated");
		sendMailServices.send(requestMail, sendMail);
		
		
		
	}

	public String recoverAccount(String email) throws FirebaseAuthException, NoSuchAlgorithmException, UnsupportedEncodingException {

		RequestMail requestMail = new RequestMail();
		
		UserRecord userFirebase = FirebaseAuth.getInstance().getUserByEmail(email);
		if (userFirebase == null) {
			throw new YtulinkException("Usuario no registrado.");
		}
	   int intRandom = ThreadLocalRandom.current().nextInt();  
		
       String code = sha1HexComponent.makeSHA1Hash(userFirebase.getUid() + intRandom ); 
	
       
       Optional<User> userVeri =   iUserRepository.findByIdFireBase(userFirebase.getUid());
       
       if (!userVeri.isPresent()) {
			throw new YtulinkException("Usuario no registrado.");
		}
       userVeri.get().setCorrelativo(code);
       iUserRepository.save(userVeri.get());
       
		requestMail.setMessage("https://ytulink.com/reset-password?email=".concat(email).concat("&&code=").concat(code));
		requestMail.setSubject(NOTIFICACIÓN_YTULINK);
		requestMail.setToEmail(email);
		requestMail.setTemplate("resetPwd");
		sendMailServices.send(requestMail, sendMail);

		return "Correo enviado..";
	}

	public String recoverPass(String password, String email, String confirm, String code) throws FirebaseAuthException {

		
		UserRecord userFirebase = null;
		if (!password.equals(confirm)) {
			throw new YtulinkException("La contraseña de verificación no coincide.");
		}

		userFirebase = FirebaseAuth.getInstance().getUserByEmail(email);
		if (userFirebase == null) {
			throw new YtulinkException("Usuario no registrado.");
		}
		
		Optional<User> userVeri =   iUserRepository.findByIdFireBase(userFirebase.getUid());
	       
	       if (!userVeri.isPresent()) {
				throw new YtulinkException("Usuario no registrado.");
			}
	       
	       if(userVeri.get().getCorrelativo() == null || !userVeri.get().getCorrelativo().equals(code)) {
	    	   throw new YtulinkException("Información no coincide con la transacción.");
	       }
		

		UpdateRequest request = new UpdateRequest(userFirebase.getUid()).setPassword(password);
		userFirebase = FirebaseAuth.getInstance().updateUser(request);

		logger.info("Successfully updated user {}.", userFirebase.getUid());

		RequestMail requestMail = new RequestMail();

		requestMail.setMessage("Tu cuenta ha sido activada satisfactoriamente.");
		requestMail.setSubject(NOTIFICACIÓN_YTULINK);
		requestMail.setToEmail(email);
		requestMail.setTemplate("notificationResetPwd");
		sendMailServices.send(requestMail, sendMail);
		return "Contraseña Actualizada.";

	}

	/**
	 * Metodo encargado de realizar una consulta por usuario
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Optional<UserImgDTO> getOneUsers(Long id) throws Exception {

		Optional<User> user = iUserRepository.findByUserIdImg(id);
		if(!user.isPresent()){
			return Optional.empty();
		}

		Set<Image> ims = user.get().getImage().stream().filter(img -> !img.isPrincipal()).collect(Collectors.toSet());

		user.get().getImage().removeAll(ims);

		Optional<UserImgDTO> optionalUsuario = Optional.ofNullable(userImgMapper.toDto(user.get()));

		return optionalUsuario;

	}
	
	/**
	 * Metodo encargado de realizar una consulta por usuario
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Optional<UserImgDTO> getUserByUserName(String name) throws Exception {

		
		
		Optional<User> user = iUserRepository.findByUserName(name);
		if(!user.isPresent()){
			return Optional.empty();
		}

		Set<Image> ims = user.get().getImage().stream().filter(img -> !img.isPrincipal()).collect(Collectors.toSet());

		user.get().getImage().removeAll(ims);

		Optional<UserImgDTO> optionalUsuario = Optional.ofNullable(userImgMapper.toDto(user.get()));

		return optionalUsuario;


	}
	
	
	

	public Page<UserDTO> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		logger.info("Request to get findAll");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<UserDTO> page = iUserRepository.findAll(pageable).map(userMapper::toDto);
		return page;
	}

	public UserDTO activate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		User out = iUserRepository.reactivate(id);
		return userMapper.toDto(out);
	}

	public UserDTO inactivate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		User out = iUserRepository.deactivate(id);
		return userMapper.toDto(out);

	}
}