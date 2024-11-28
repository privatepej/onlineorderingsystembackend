package org.acumen.training.codes.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    
    
    private static final String UPLOAD_DIR = "src/main/resources/img";

    public String saveImage(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        String originalFilename = image.getOriginalFilename();
//      String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        Path filePath = uploadPath.resolve(originalFilename);
        if (Files.exists(filePath)) {
            throw new IllegalArgumentException("File with the name '" + originalFilename + "' already exists.");
        }

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return originalFilename; 
    }
    
    public void deleteImage(String imagePath) throws IOException {
        Path filePath = Paths.get(imagePath);
        if (Files.exists(filePath)) {
            if (filePath.getFileName().toString().equals("default.png")) {
            	return;
            } else {
            	 Files.delete(filePath);
                 System.out.println("Deleted file: " + filePath.toString());
            }
        } else {
            System.out.println("File does not exist: " + filePath.toString());
        }
    }


}
