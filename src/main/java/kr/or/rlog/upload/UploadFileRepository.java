package kr.or.rlog.upload;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

    public UploadFile findOneByFileName(String fileName);

}
