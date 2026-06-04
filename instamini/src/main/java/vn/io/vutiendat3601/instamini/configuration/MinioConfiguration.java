package vn.io.vutiendat3601.instamini.configuration;

import static vn.io.vutiendat3601.instamini.constant.Constant.MINIO_DEFAULT_BASE_URL;
import static vn.io.vutiendat3601.instamini.constant.Constant.MINIO_DEFAULT_PUBLIC_IMAGE_BUCKET;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.MinioException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MinioConfiguration {

  @Bean
  MinioClient minioClient(Environment env) {
    var minioBaseUrl = env.getProperty("minio.base-url", MINIO_DEFAULT_BASE_URL);
    return MinioClient.builder()
        .endpoint(minioBaseUrl)
        .credentials("minioadmin", "minioadmin")
        .build();
  }

  @Bean
  CommandLineRunner autoCreatePublicImageBucket(MinioClient minioClient, Environment env)
      throws MinioException {
    var minioPublicImageBucket =
        env.getProperty("minio.public-image-bucket", MINIO_DEFAULT_PUBLIC_IMAGE_BUCKET);
    var minioPublicImageBucketPolicy =
        """
        {
          "Version":"2012-10-17",
          "Statement":[
            {
              "Effect":"Allow",
              "Principal":{"AWS":["*"]},
              "Action":["s3:GetObject"],
              "Resource":["arn:aws:s3:::%s/*"]
            }
          ]
        }
        """
            .formatted(minioPublicImageBucket);
    return args -> {
      boolean existed =
          minioClient.bucketExists(
              BucketExistsArgs.builder().bucket(minioPublicImageBucket).build());
      if (!existed) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioPublicImageBucket).build());
      }
      minioClient.setBucketPolicy(
          SetBucketPolicyArgs.builder()
              .bucket(minioPublicImageBucket)
              .config(minioPublicImageBucketPolicy)
              .build());
    };
  }
}
