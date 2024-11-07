package medical_insurance.backend_medical_insurance.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import medical_insurance.backend_medical_insurance.inventory.entity.ImageEntity;
import medical_insurance.backend_medical_insurance.inventory.service.LocalStorageService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/files/image")
public class FileController {

  @Autowired
  private LocalStorageService service;

  @Autowired

  @PostMapping
  public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
    ImageEntity imgCreated = service.uploadImage(file);
    return ResponseEntity.status(HttpStatus.OK)
        .body(imgCreated.name);
  }

  // crear imagen y relacionarla con un option-product
  @PostMapping("/product-option/{productOptionId}")
  public ResponseEntity<?> uploadImageProductOption(@RequestParam("image") MultipartFile file,
      @PathVariable UUID productOptionId) throws IOException {
    ImageEntity imgCreated = service.uploadImage(file);
    return ResponseEntity.status(HttpStatus.OK)
        .body(imgCreated.name);
  }

  @GetMapping("/{fileName}")
  public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
    byte[] imageData = service.downloadImage(fileName);
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.valueOf("image/png"))
        .body(imageData);
  }
}
