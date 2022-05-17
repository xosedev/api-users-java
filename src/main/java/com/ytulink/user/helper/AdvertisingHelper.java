package com.ytulink.user.helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ytulink.user.config.FileStorageProperties;
import com.ytulink.user.dto.AdvertisingDTO;
import com.ytulink.user.entity.Advertising;
import com.ytulink.user.entity.User;
import com.ytulink.user.exception.FileStorageException;
import com.ytulink.user.exception.YtulinkException;
import com.ytulink.user.mapper.AdvertisingMapper;
import com.ytulink.user.repository.IAdvertisingRepository;
import com.ytulink.user.repository.IUserRepository;
import com.ytulink.user.service.FtpService;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */

@Component
public class AdvertisingHelper {
	static Logger logger = LogManager.getLogger(AdvertisingHelper.class);

	private static final String ID_NO_VALIDA = "¡Lo Siento! El nombre de archivo contiene una id no válida ";
		
	@Autowired
	AdvertisingMapper advertisingMapper;

	@Autowired
	IAdvertisingRepository advertisingRepository;

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	FtpService ftpService;
	
	private final Path fileStorageLocation;

	private final String urlFtp;
	private final String internalPath;
	private final String userFtp;
	private final String passFtp;
	private final String imgPath;
	
	@Autowired
	public AdvertisingHelper(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		this.urlFtp = fileStorageProperties.getUrlFtp();
		this.internalPath = fileStorageProperties.getInternalPath();
		this.userFtp = fileStorageProperties.getUserFtp();
		this.passFtp = fileStorageProperties.getPassFtp();
		this.imgPath = fileStorageProperties.getImgPath();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",ex);
		}
	}
	
	
	
	@Transactional(rollbackOn = Exception.class)
	public AdvertisingDTO create(AdvertisingDTO advertisingDTO) throws Exception {

		Optional<User> user = userRepository.findById(advertisingDTO.getUserId());

		if (user.isPresent()) {
			
			// creacion de las rutas para las imagenes del usuario
			String serverImgPath = internalPath.concat("user/").concat(user.get().getIdFireBase().toString().concat("/"));
			String urlImgPath = imgPath.concat("user/").concat(user.get().getIdFireBase().toString().concat("/"));
			ftpService.connectToFTP(urlFtp, userFtp, passFtp);
			ftpService.creteDir(serverImgPath);
			ftpService.creteDir(serverImgPath.concat("publicidad/"));
			
			
			
			Advertising advertising = advertisingMapper.toEntity(advertisingDTO);
			/**
			 * INICIO UrlImg (publicidad)
			 */
			if (null != advertisingDTO.getUrlImg() && !"".equalsIgnoreCase(advertisingDTO.getUrlImg())) {
				StringBuffer fileNameImgCover = new StringBuffer();
				fileNameImgCover.append(UUID.randomUUID().toString().replaceAll("-", ""));

				if (null != advertisingDTO.getUrlImg()  && advertisingDTO.getUrlImg().isEmpty()) {
					throw new FileStorageException("el archivo no puede ser predeterminado");
				} else if (null != advertisingDTO.getUrlImg() 
						&& advertisingDTO.getUrlImg() .indexOf("data:image/png;") != -1) {
					advertisingDTO.setUrlImg(advertisingDTO.getUrlImg().replace("data:image/png;base64,", ""));
					fileNameImgCover.append(".png");
				} else if (null != advertisingDTO.getUrlImg() 
						&& advertisingDTO.getUrlImg() .indexOf("data:image/jpeg;") != -1) {
					advertisingDTO.setUrlImg(advertisingDTO.getUrlImg().replace("data:image/jpeg;base64,", ""));
					fileNameImgCover.append(".jpeg");
				} else {
					throw new FileStorageException("Seleccione una imagen en formato .png.jpg");
				}

				// Compruebe si el nombre del archivo contiene caracteres no validos
				String fileNameImgPortada = StringUtils.cleanPath(fileNameImgCover.toString());

				byte[] fileBytesCoverImg = Base64.getDecoder().decode(advertisingDTO.getUrlImg());

				InputStream targetStreamImgCover = new ByteArrayInputStream(fileBytesCoverImg);

				if (fileNameImgPortada.contains("..")) {
					throw new FileStorageException(ID_NO_VALIDA + fileNameImgCover);
				}
				/**
				 * FIN urlImg
				 */

				advertising.setUrlImg(urlImgPath.concat("publicidad/").concat(fileNameImgPortada));
				ftpService.uploadFileToFTP2(targetStreamImgCover, serverImgPath.concat("publicidad/"), fileNameImgPortada);
			}
			
			
			Advertising out = advertisingRepository.save(advertising);
			return advertisingMapper.toDto(out);
		}

		throw new YtulinkException("El user id no existe.");

	}

	public Page<AdvertisingDTO> findAllByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
		logger.info("Request to get findAllByUserId");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<AdvertisingDTO> page = advertisingRepository.findAllByUserId(userId, pageable).map(advertisingMapper::toDto);
		return page;
	}

	
	
	public Page<AdvertisingDTO> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		logger.info("Request to get findAll");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<AdvertisingDTO> page = advertisingRepository.findAll(pageable).map(advertisingMapper::toDto);
		return page;
	}
	
	
	
	public AdvertisingDTO activate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		Advertising out = advertisingRepository.reactivate(id);
		return advertisingMapper.toDto(out);
	}

	public AdvertisingDTO inactivate(Long id) throws Exception {
		logger.info("Request to get delete : {}", id);
		Advertising out = advertisingRepository.deactivate(id);
		return advertisingMapper.toDto(out);

	}
}