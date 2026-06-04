package vn.io.vutiendat3601.instamini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.instamini.dto.request.UploadImageRequest;
import vn.io.vutiendat3601.instamini.dto.response.UploadImageResponse;
import vn.io.vutiendat3601.instamini.service.UploadService;

@Tag(name = "Upload")
@Slf4j
@RequestMapping("v1/upload")
@RestController
public class UploadController {
  UploadService uploadService;

  @PostMapping()
  public ResponseEntity<UploadImageResponse> postMethodName(
      @Valid @RequestBody UploadImageRequest uploadImageReq) {
    var uploadImageResponse = uploadService.uploadImage(uploadImageReq);
    return ResponseEntity.ok(uploadImageResponse);
  }
}
