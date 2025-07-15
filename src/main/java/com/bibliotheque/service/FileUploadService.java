package com.bibliotheque.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {
    
    private static final String UPLOAD_DIR = "uploads/images/";
    
    public FileUploadService() {
        // Créer le répertoire d'upload s'il n'existe pas
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le répertoire d'upload", e);
        }
    }
    
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide");
        }
        
        // Vérifier le type de fichier
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Le fichier doit être une image");
        }
        
        // Générer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;
        
        // Sauvegarder le fichier
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return filename;
    }
    
    public void deleteImage(String filename) {
        if (filename != null && !filename.trim().isEmpty()) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR, filename);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Log l'erreur mais ne pas faire échouer l'opération
                System.err.println("Erreur lors de la suppression de l'image: " + e.getMessage());
            }
        }
    }
    
    public String getImageUrl(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return null;
        }
        return "/images/" + filename;
    }
} 