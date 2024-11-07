package medical_insurance.backend_medical_insurance.inventory.service;

import medical_insurance.backend_medical_insurance.inventory.entity.ImageEntity;
import medical_insurance.backend_medical_insurance.inventory.repository.ImageRepository;
import medical_insurance.backend_medical_insurance.inventory.utils.ImageUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalStorageService {

  @Autowired
  private ImageRepository repository;

  // @Autowired
  // private FileDataRepository fileDataRepository;

  @Transactional // Asegurarse de que la operación esté envuelta en una transacción
  public ImageEntity uploadImage(MultipartFile file) throws IOException {
    // Generar código aleatorio usando UUID
    String randomCode = UUID.randomUUID().toString();
    // Crear el nombre de archivo único basado en el código aleatorio
    String fileName = randomCode + "_" + file.getOriginalFilename();
    ImageEntity imageData = repository.save(ImageEntity.builder()
        .name(fileName)
        .type(file.getContentType())
        .imageData(ImageUtils.compressImage(file.getBytes())).build());

    if (imageData != null) {
      return imageData;
    }
    return null;
  }

  @Transactional(readOnly = true) // Para la descarga de la imagen
  public byte[] downloadImage(String fileName) {
    Optional<ImageEntity> dbImageData = repository.findByName(fileName);
    return ImageUtils.decompressImage(dbImageData.get().imageData);
  }

  @Transactional // Para eliminar imágenes
  public void deleteImage(String fileName) {
    Optional<ImageEntity> dbImageData = repository.findByName(fileName);
    repository.delete(dbImageData.get());
  }

  @Transactional // Para eliminar imágenes por id
  public void deleteImageById(UUID id) {
    repository.deleteById(id);
  }
}
