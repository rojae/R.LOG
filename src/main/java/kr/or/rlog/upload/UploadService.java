package kr.or.rlog.upload;

import kr.or.rlog.upload.UploadFile;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    UploadFile store(MultipartFile file) throws Exception;
}
