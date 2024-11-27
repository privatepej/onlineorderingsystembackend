package org.acumen.training.codes.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    
    
    private static final String UPLOAD_DIR = "src/main/resources/img";

    public String saveImage(MultipartFile image) throws IOException {
        // Ensure the upload directory exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Get the original filename
        String originalFilename = image.getOriginalFilename();
//      String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        // Check if the file already exists
        Path filePath = uploadPath.resolve(originalFilename);
        if (Files.exists(filePath)) {
            throw new IllegalArgumentException("File with the name '" + originalFilename + "' already exists.");
        }

        // Save the file to disk
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return originalFilename; // Return the saved filename
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
