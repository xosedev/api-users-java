package com.ytulink.user.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ytulink.user.config.FileStorageProperties;
import com.ytulink.user.dto.ImageDTO;
import com.ytulink.user.dto.UserDTO;
import com.ytulink.user.dto.UserImgDTO;
import com.ytulink.user.entity.Image;
import com.ytulink.user.entity.User;
import com.ytulink.user.exception.FileStorageException;
import com.ytulink.user.exception.YtulinkException;
import com.ytulink.user.mapper.UserMapper;
import com.ytulink.user.repository.IImageRepository;
import com.ytulink.user.repository.IUserRepository;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */
@Service
public class FileStorageService {

	private static final String ID_NO_VALIDA = "¡Lo Siento! El nombre de archivo contiene una id no válida ";

	private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

	private final Path fileStorageLocation;

	private final String urlFtp;
	private final String internalPath;
	private final String userFtp;
	private final String passFtp;
	private final String imgPath;

	@Autowired
	FtpService ftpService;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IImageRepository imageRepository;

	@Autowired
	UserMapper userMapper;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		this.urlFtp = fileStorageProperties.getUrlFtp();
		this.internalPath = fileStorageProperties.getInternalPath();
		this.userFtp = fileStorageProperties.getUserFtp();
		this.passFtp = fileStorageProperties.getPassFtp();
		this.imgPath = fileStorageProperties.getImgPath();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public UserDTO update(UserImgDTO userImgDTO) throws Exception {

		logger.debug("inicio servicio update");

		User user = null;

		try {
			Long.parseLong(userImgDTO.getUserName());
			throw new YtulinkException(
					"Nombre de usuario no puede contener solo números. ".concat(userImgDTO.getUserName()));
		} catch (NumberFormatException ex) {
			logger.info("Nombre de usuario correcto Alfanumérico");
		}
		Optional<User> validarUser = userRepository.findByEmail(userImgDTO.getEmail());

		if (validarUser.isPresent()) {
			user = validarUser.get();
		} else {
			throw new Exception("El usuario no existe, favor contactarse con el administrador");
		}

		try {

			if (userImgDTO.getName() != null) {
				user.setName(userImgDTO.getName());
			}
			if (userImgDTO.getUserName() != null) {

				Optional<User> userName = userRepository.findByUserName(userImgDTO.getUserName());

				if (userName.isPresent() && userName.get().getId() != user.getId() && userName.isPresent()) {
					throw new YtulinkException("El nombre de Usuario ingresado ya existe.");
				}

				user.setUserName(userImgDTO.getUserName());
			}

			if (userImgDTO.getAbout() != null) {
				user.setAbout(userImgDTO.getAbout());
			}
			if (userImgDTO.getEmail() != null) {
				user.setEmail(userImgDTO.getEmail());
			}

			if (userImgDTO.getBirthday() != null) {
				user.setBirthday(userImgDTO.getBirthday());
			}
			if (userImgDTO.getLastName() != null) {
				user.setLastName(userImgDTO.getLastName());
			}
			if (userImgDTO.getPhoneCode() != null) {
				user.setPhoneCode(userImgDTO.getPhoneCode());
			}
			if (userImgDTO.getPhone() != null) {
				user.setPhone(userImgDTO.getPhone());
			}
			if (userImgDTO.getSex() != null) {
				user.setSex(userImgDTO.getSex());
			}
			user.setPrivateProfile(userImgDTO.isPrivateProfile());
			user.setRegisterComplete(true);

			// creacion de las rutas para las imagenes del usuario
			String serverImgPath = internalPath.concat("user/").concat(user.getIdFireBase().toString().concat("/"));
			String urlImgPath = imgPath.concat("user/").concat(user.getIdFireBase().toString().concat("/"));
			ftpService.connectToFTP(urlFtp, userFtp, passFtp);
			ftpService.creteDir(serverImgPath);
			ftpService.creteDir(serverImgPath.concat("portada/"));

			/**
			 * INICIO ImgCoverPage (portada)
			 */
			if (null != userImgDTO.getImgCoverPage() && !"".equalsIgnoreCase(userImgDTO.getImgCoverPage())
					&& !userImgDTO.getImgCoverPage().contains("ytulink.com")) {
				StringBuffer fileNameImgCover = new StringBuffer();
				fileNameImgCover.append(UUID.randomUUID().toString().replaceAll("-", ""));

				if (null != userImgDTO.getImgCoverPage() && userImgDTO.getImgCoverPage().isEmpty()) {
					throw new FileStorageException("el archivo no puede ser predeterminado");
				} else if (null != userImgDTO.getImgCoverPage()
						&& userImgDTO.getImgCoverPage().indexOf("data:image/png;") != -1) {
					userImgDTO.setImgCoverPage(userImgDTO.getImgCoverPage().replace("data:image/png;base64,", ""));
					fileNameImgCover.append(".png");
				} else if (null != userImgDTO.getImgCoverPage()
						&& userImgDTO.getImgCoverPage().indexOf("data:image/jpeg;") != -1) {
					userImgDTO.setImgCoverPage(userImgDTO.getImgCoverPage().replace("data:image/jpeg;base64,", ""));
					fileNameImgCover.append(".jpeg");
				} else {
					throw new FileStorageException("Seleccione una imagen en formato .png.jpg");
				}

				// Compruebe si el nombre del archivo contiene caracteres no validos
				String fileNameImgPortada = StringUtils.cleanPath(fileNameImgCover.toString());

				byte[] fileBytesCoverImg = Base64.getDecoder().decode(userImgDTO.getImgCoverPage());

				InputStream targetStreamImgCover = new ByteArrayInputStream(fileBytesCoverImg);

				if (fileNameImgPortada.contains("..")) {
					throw new FileStorageException(ID_NO_VALIDA + fileNameImgCover);
				}
				/**
				 * FIN ImgCoverPage
				 */

				user.setUrlImgCoverPage(urlImgPath.concat("portada/").concat(fileNameImgPortada));
				ftpService.uploadFileToFTP2(targetStreamImgCover, serverImgPath.concat("portada/"), fileNameImgPortada);
			}
			user = userRepository.save(user);

			if (null != userImgDTO.getUserImg()) {

				
				/* variable para controlar si se creo una imagen nueva o no y las demas actualizarlas a no principal */
				boolean isNewImg = false;
				
				/* dejamos todas las imagenes como no principal */
				List<Image> imgsList = imageRepository.findByUserId(user.getId());

				for (ImageDTO imageDTO : userImgDTO.getUserImg()) {

					if (!imageDTO.getUrlImg().contains("ytulink.com")) {

						StringBuffer fileName = new StringBuffer();
						fileName.append(UUID.randomUUID().toString().replaceAll("-", ""));

						if (imageDTO.getUrlImg().isEmpty()) {

							throw new FileStorageException("el archivo no puede ser predeterminado");

						} else if (imageDTO.getUrlImg().indexOf("data:image/png;") != -1) {
							imageDTO.setUrlImg(imageDTO.getUrlImg().replace("data:image/png;base64,", ""));
							fileName.append(".png");
						} else if (imageDTO.getUrlImg().indexOf("data:image/jpeg;") != -1) {
							imageDTO.setUrlImg(imageDTO.getUrlImg().replace("data:image/jpeg;base64,", ""));
							fileName.append(".jpeg");
						} else {

							throw new FileStorageException("Seleccione una imagen en formato .png.jpg");
						}
						
						
						isNewImg = true;

						// Compruebe si el nombre del archivo contiene caracteres no válidos
						String fileNameImg = StringUtils.cleanPath(fileName.toString());

						byte[] fileBytes = Base64.getDecoder().decode(imageDTO.getUrlImg());

						InputStream targetStream = new ByteArrayInputStream(fileBytes);

						if (fileNameImg.contains("..")) {
							throw new FileStorageException(ID_NO_VALIDA + fileNameImg);
						}

						Image image = new Image();
						image.setName(fileNameImg);
						image.setUrlImg(urlImgPath.concat(fileNameImg));
						image.setUser(user);
						image.setPrincipal(true);
						imageRepository.save(image);

						ftpService.uploadFileToFTP2(targetStream, serverImgPath, fileNameImg);

					}
				}

				if(!userImgDTO.getUserImg().isEmpty() && isNewImg) {
					imgsList.forEach(u -> u.setPrincipal(false));
					imageRepository.saveAll(imgsList);
				}
				
				

			}
			ftpService.disconnectFTP();

		} catch (FileStorageException ex) {
			throw new FileStorageException("Could not store file ", ex);
		} catch (YtulinkException e) {
			throw new YtulinkException(e.getLocalizedMessage());
		}

		return userMapper.toDto(user);
	}
}