package com.ytulink.user.componet;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class Sha1HexComponent {

	static Logger logger = LogManager.getLogger(Sha1HexComponent.class);
	
    private static final String UTF_8 = "UTF-8";
    
       

	public String makeSHA1Hash(String uuId)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
        {
		
		
		  logger.log(Level.INFO, "Generando código hash Ytulink uuId {}.", uuId);
		
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = uuId.getBytes(UTF_8);
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            
            logger.log(Level.INFO, "Código generado: {} ",hexStr); 
            
            
            return hexStr;
        }
}