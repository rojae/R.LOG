package kr.or.rlog.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtils {
    
    private static final Map<String, MediaType> mediaMap;
    
    static {
        mediaMap = new HashMap<>();
        
        mediaMap.put("JPEG", MediaType.IMAGE_JPEG);
        mediaMap.put("JPG", MediaType.IMAGE_JPEG);
        mediaMap.put("GIF", MediaType.IMAGE_GIF);
        mediaMap.put("PNG", MediaType.IMAGE_PNG);
    }
    
    public static MediaType getMediaType(String type) {
        return mediaMap.get(type.toUpperCase());
    }
    
    public static boolean containsImageMediaType(String mediaType) {
        return mediaMap.containsValue(MediaType.valueOf(mediaType));
    }
    
}