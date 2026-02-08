package com.test.bob.Service;

import com.test.bob.Config.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    private final MinioProperties props;

    public String uploadOfferImage(Long offerId,
                                   MultipartFile file){
        try{
            String objectName =
                    "offers/" + offerId + "/" +
                            UUID.randomUUID() + "-" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(props.getBucket())
                            .object(objectName)
                            .stream(
                                    file.getInputStream(),
                                    file.getSize(),
                                    -1
                            )
                            .contentType(file.getContentType())
                            .build()
            );

            return objectName;
        } catch (Exception e){
            throw new RuntimeException("Błąd uploadu do minio " + e);
        }
    }

    public void deleteObject(String objectKey){
        try{
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(props.getBucket())
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Błąd usuwania zdjećia z MINIO"+e);
        }
    }

}
