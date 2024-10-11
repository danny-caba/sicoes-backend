package pe.gob.osinergmin.sicoes.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String AES = "AES";
    private static final String SECRET = "s3cr3t-k3y-3ncr1";
//    private static final String SECRET = "secret-key-12345";
    private static Key key;
    private static Cipher cipher;
    
 
  
    
    public static void main(String[] args) throws Exception {
    	System.out.println(String.format("%.2f", 120.03));
//    	 key = new SecretKeySpec(SECRET.getBytes(), AES);
//         cipher = Cipher.getInstance(AES);
//         cipher.init(Cipher.ENCRYPT_MODE, key);
//         System.out.println(Base64.getEncoder().encodeToString(cipher.doFinal("123456".getBytes())));
	}
    
    
    public static String secretKeyToString(SecretKey s) {
        byte[] bytes = Base64.getEncoder().encode(s.getEncoded());
        return new String(bytes);
}
    

    public AttributeEncryptor() throws Exception {
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
    	if(attribute==null) {return null;}
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
    	if( dbData==null) {return null;}
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            Double value=Double.parseDouble(new String(cipher.doFinal(Base64.getDecoder().decode(dbData))));            
            return String.format("%.2f", value);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        }
    }
}