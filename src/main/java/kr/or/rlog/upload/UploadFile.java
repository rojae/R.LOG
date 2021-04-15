package kr.or.rlog.upload;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import kr.or.rlog.common.TimeEntity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "TBL_UPLOAD_FILE")
@Getter
@Setter
public class UploadFile extends TimeEntity {
    
    @Id
    @GeneratedValue
    Long id;

    @Column
    String fileName;
    
    @Column
    String saveFileName;
    
    @Column
    String filePath;
    
    @Column
    String contentType;
    
    @Column
    long size;
    
}