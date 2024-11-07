// package eccomerce.backend_eccomerce.inventory.service;

// import eccomerce.backend_eccomerce.inventory.dto.CreateImageDto;
// import eccomerce.backend_eccomerce.inventory.entity.ImageEntity;
// import eccomerce.backend_eccomerce.inventory.entity.ProductOptionEntity;
// import eccomerce.backend_eccomerce.inventory.repository.ImageRepository;
// import eccomerce.backend_eccomerce.inventory.repository.ProductOptionRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
// import java.util.UUID;

// @Service
// public class ImageService {

//   @Autowired
//   private ImageRepository imageRepository;

//   @Autowired
//   private ProductOptionRepository productOptionRepository;

//   @Autowired
//   private LocalStorageService localStorageService;

//   // Crear una nueva imagen para la opción del producto
//   public ImageEntity create(CreateImageDto createImageDto) {
//     try {
//       // Buscar la opción del producto
//       ProductOptionEntity productOption = productOptionRepository.findById(createImageDto.getProductOptionId())
//           .orElseThrow(() -> new RuntimeException(
//               "La opción de producto no existe con ID: " + createImageDto.getProductOptionId()));

//       // Verificar si ya existe una imagen para esta opción de producto
//       if (productOption.image != null) {
//         throw new RuntimeException("La opción de producto ya tiene una imagen asociada.");
//       }

//       // Subir el archivo de imagen usando el LocalStorageService
//       MultipartFile imageFile = createImageDto.getImage();
//       String folder = "product_options/" + productOption.getId().toString(); // Carpeta para almacenar la imagen
//       String imageUrl = localStorageService.uploadFile(imageFile, folder);

//       // Crear la entidad de imagen
//       ImageEntity imageEntity = new ImageEntity();
//       imageEntity.imageUrl = imageUrl;
//       imageEntity.imagePath = folder + "/" + imageFile.getOriginalFilename();
//       imageEntity.productOption = productOption; // Asignar la opción de producto a la imagen

//       // Guardar la entidad de imagen en la base de datos
//       imageRepository.save(imageEntity);

//       return imageEntity;

//     } catch (IOException e) {
//       throw new RuntimeException("Error al subir la imagen: " + e.getMessage());
//     }
//   }

//   // Eliminar una imagen
//   public void delete(UUID imageId) {
//     ImageEntity imageEntity = imageRepository.findById(imageId)
//         .orElseThrow(() -> new RuntimeException("La imagen no existe con ID: " + imageId));

//     try {
//       // Eliminar el archivo del almacenamiento local
//       localStorageService.deleteFile(imageEntity.imagePath);
//     } catch (IOException e) {
//       throw new RuntimeException("Error al eliminar la imagen del sistema de archivos: " + e.getMessage());
//     }

//     // Eliminar la entrada en la base de datos
//     imageRepository.delete(imageEntity);
//   }

//   // Obtener la imagen por ID
//   public ImageEntity getOneById(UUID imageId) {
//     return imageRepository.findById(imageId)
//         .orElseThrow(() -> new RuntimeException("La imagen no existe con ID: " + imageId));
//   }
// }
