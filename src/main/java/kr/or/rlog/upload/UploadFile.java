package kr.or.rlog.upload;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import kr.or.rlog.common.BaseTimeEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "TBL_UPLOAD_FILE")
@Getter
@Setter
public class UploadFile extends BaseTimeEntity {
    
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