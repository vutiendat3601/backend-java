package vn.io.vutiendat3601.instamini.service.impl;

import static vn.io.vutiendat3601.instamini.constant.Constant.MINIO_DEFAULT_PUBLIC_IMAGE_BUCKET;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.instamini.dto.request.UploadImageRequest;
import vn.io.vutiendat3601.instamini.dto.response.UploadImageResponse;
import vn.io.vutiendat3601.instamini.service.UploadService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadServiceV1 implements UploadService {
  private final MinioClient minioClient;
  private final Environment env;

  @Override
  public UploadImageResponse uploadImage(UploadImageRequest uploadImageReq) {
    var fileName =
        "%s.%s"
            .formatted(
                UUID.randomUUID().toString(), getFileExtension(uploadImageReq.base64ImageString()));
    var minioPublicImageBucket =
        env.getProperty("minio.public-image-bucket", MINIO_DEFAULT_PUBLIC_IMAGE_BUCKET);
    try {
      minioClient.putObject(
          PutObjectArgs.builder().bucket(minioPublicImageBucket).object(fileName).stream(
                  getImageFromBase64(uploadImageReq.base64ImageString()), -1L, 5242880L)
              .build());
    } catch (Exception e) {
      log.error("Error when upload image", e);
    }
    return new UploadImageResponse(fileName);
  }

  private String getFileExtension(String base64String) {
    var strs = base64String.split(",");
    var extension = "";
    switch (strs[0]) {
      case "data:image/jpeg;base64":
        extension = "jpeg";
        break;
      case "data:image/png;base64":
        extension = "png";
        break;
      default:
        extension = "jpg";
        break;
    }
    return extension;
  }

  private InputStream getImageFromBase64(String base64String) {
    var strs = base64String.split(",");
    var data = Base64.getDecoder().decode(strs[1]);
    return new ByteArrayInputStream(data);
  }
}
