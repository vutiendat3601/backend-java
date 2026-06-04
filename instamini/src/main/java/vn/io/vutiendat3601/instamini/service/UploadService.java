package vn.io.vutiendat3601.instamini.service;

import vn.io.vutiendat3601.instamini.dto.request.UploadImageRequest;
import vn.io.vutiendat3601.instamini.dto.response.UploadImageResponse;

public interface UploadService {
  UploadImageResponse uploadImage(UploadImageRequest uploadImageReq);
}
