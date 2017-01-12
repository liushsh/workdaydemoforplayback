package resource.customeraccountservice;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


import javax.imageio.ImageIO;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

public class ImageUtils {

    /**
     * Decode string to image
     * @param imageString The string to decode
     * @return decoded image
     */
    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public static org.bson.types.Binary decodeStringToImageBinary(String imageString)
    {
    	
        byte[] imageByte=null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new org.bson.types.Binary(BsonBinarySubType.BINARY,imageByte);
    	
    }

    public static String encodeImageBinaryToString(org.bson.types.Binary image) {
        
        String imageString="";
    	byte[] imageBytes = image.getData();
        try {
            

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    /**
     * Encode image to string
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
}
