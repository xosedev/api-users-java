package com.ytulink.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytulink.user.exception.ErrorMessage;
import com.ytulink.user.exception.FTPErrors;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Service
public class FtpService {

	/**
	 * FTP connection handler
	 */
	FTPClient ftpconnection;

	private Logger logger = LoggerFactory.getLogger(FtpService.class);

	/**
	 * Method that implement FTP connection.
	 * 
	 * @param host IP of FTP server
	 * @param user FTP valid user
	 * @param pass FTP valid pass for user
	 * @throws FTPErrors Set of possible errors associated with connection process.
	 */

	public void connectToFTP(String host, String user, String pass) throws FTPErrors {

		ftpconnection = new FTPClient();
		ftpconnection.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		int reply;

		try {
			ftpconnection.connect(host);
		} catch (IOException e) {
			ErrorMessage errorMessage = new ErrorMessage(-1,
					"No fue posible conectarse al FTP a través del host=" + host);
			logger.error(errorMessage.toString());
			throw new FTPErrors(errorMessage);
		}

		reply = ftpconnection.getReplyCode();

		if (!FTPReply.isPositiveCompletion(reply)) {

			try {
				ftpconnection.disconnect();
			} catch (IOException e) {
				ErrorMessage errorMessage = new ErrorMessage(-2,
						"No fue posible conectarse al FTP, el host=" + host + " entregó la respuesta=" + reply);
				logger.error(errorMessage.toString());
				throw new FTPErrors(errorMessage);
			}
		}

		try {
			ftpconnection.login(user, pass);
		} catch (IOException e) {
			ErrorMessage errorMessage = new ErrorMessage(-3,
					"El usuario=" + user + ", y el pass=**** no fueron válidos para la autenticación.");
			logger.error(errorMessage.toString());
			throw new FTPErrors(errorMessage);
		}

		try {
			ftpconnection.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			ErrorMessage errorMessage = new ErrorMessage(-4, "El tipo de dato para la transferencia no es válido.");
			logger.error(errorMessage.toString());
			throw new FTPErrors(errorMessage);
		}

		ftpconnection.enterLocalPassiveMode();
	}

	/**
	 * Method that allow upload file to FTP
	 * 
	 * @param file           File object of file to upload
	 * @param ftpHostDir     FTP host internal directory to save file
	 * @param serverFilename Name to put the file in FTP server.
	 * @throws FTPErrors Set of possible errors associated with upload process.
	 */

	public void uploadFileToFTP2(InputStream input, String ftpHostDir, String serverFilename) throws FTPErrors {

		try {

			this.ftpconnection.storeFile(ftpHostDir + serverFilename, input);
		} catch (IOException e) {
			ErrorMessage errorMessage = new ErrorMessage(-5, "No se pudo subir el archivo al servidor.");
			logger.error(errorMessage.toString());
			throw new FTPErrors(errorMessage);
		}

	}

	public void uploadFileToFTP(File file, String ftpHostDir, String serverFilename) throws FTPErrors {

		try {
			InputStream input = new FileInputStream(file);
			this.ftpconnection.storeFile(ftpHostDir + serverFilename, input);
		} catch (IOException e) {
			ErrorMessage errorMessage = new ErrorMessage(-5, "No se pudo subir el archivo al servidor.");
			logger.error(errorMessage.toString());
			throw new FTPErrors(errorMessage);
		}

	}

	/**
	 * Method for download files from FTP.
	 * 
	 * @param ftpRelativePath Relative path of file to download into FTP server.
	 * @param copytoPath      Path to copy the file in download process.
	 * @throws FTPErrors Set of errors associated with download process.
	 */

	public void downloadFileFromFTP(String ftpRelativePath, String copytoPath) throws FTPErrors {

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(copytoPath);
		} catch (FileNotFoundException e) {
			ErrorMessage errorMessage = new ErrorMessage(-6,
					"No se pudo obtener la referencia a la carpeta relativa donde guardar, verifique la ruta y los permisos.");
			logger.error(errorMessage.toString());
			throw new FTPErrors(errorMessage);
		}

		try {
			this.ftpconnection.retrieveFile(ftpRelativePath, fos);
		} catch (IOException e) {
			ErrorMessage errorMessage = new ErrorMessage(-7, "No se pudo descargar el archivo.");
			logger.error(errorMessage.toString());
			throw new FTPErrors(errorMessage);
		}
	}

	/**
	 * Method for release the FTP connection.
	 * 
	 * @throws FTPErrors Error if unplugged process failed.
	 */
	public void disconnectFTP() throws FTPErrors {
		if (this.ftpconnection.isConnected()) {
			try {
				this.ftpconnection.logout();
				this.ftpconnection.disconnect();
			} catch (IOException f) {
				throw new FTPErrors(
						new ErrorMessage(-8, "Ha ocurrido un error al realizar la desconexión del servidor FTP"));
			}
		}
	}

	public void creteDir(String dirName) throws FTPErrors {
		try {
			this.ftpconnection.makeDirectory(dirName);
		} catch (IOException e) {
			ErrorMessage errorMessage = new ErrorMessage(-9, "Ha ocurrido un error al crear el directorio.");
			logger.error(errorMessage.toString());
			throw new FTPErrors(errorMessage);
		}
	}
}