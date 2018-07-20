import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MacUtil {
    public static String generateHash(File file) throws  HashGenerationException{
        return hashFile(file);
    }

    private static String hashFile(File file) throws HashGenerationException{
        try(FileInputStream inputStream=new FileInputStream(file)){
            KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA1");
            SecretKey key=keyGenerator.generateKey();
            Mac mac=Mac.getInstance(key.getAlgorithm());
            mac.init(key);
            byte[] buffer=new byte[1024];
            int bytesRead=-1;
            while ((bytesRead = inputStream.read(buffer))!=-1) mac.update(buffer, 0, bytesRead);
            byte[] hash=mac.doFinal();
            return byteArrayToString(hash);
        }catch (NoSuchAlgorithmException|IOException|InvalidKeyException e) {
            throw new HashGenerationException("could not hash the file");
        }
    }

    private static String byteArrayToString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}
