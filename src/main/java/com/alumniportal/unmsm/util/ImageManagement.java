package com.alumniportal.unmsm.util;

import com.alumniportal.unmsm.model.AbstractEntity;
import com.alumniportal.unmsm.model.Company;
import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.persistence.ICompanyDAO;
import com.alumniportal.unmsm.persistence.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ImageManagement<T extends AbstractEntity> {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ICompanyDAO companyDAO;

    // Obtener ruta relativa del proyecto y crear carpeta 'uploads' si no existe
    private final String UPLOAD_DIR = Paths.get(System.getProperty("user.dir"), "uploads/").toString();

    public ImageManagement() {
        // Verificar si la carpeta 'uploads' existe, si no, crearla
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }

    public String uploadOrUpdateImage(T entity, MultipartFile file) throws IOException {
        String fileNameAndId;
        String filePath;

        // Determinar el nombre del archivo y la ruta según el tipo de entidad
        if (entity instanceof User) {
            User user = userDAO.findById(entity.getId());
            fileNameAndId = user.getId() + "_" + user.getName() + "_" + file.getOriginalFilename();
            filePath = Paths.get(UPLOAD_DIR, fileNameAndId).toString();

            // Eliminar imagen anterior si existe
            deleteImageByUrl(user.getPhotoUrl());
            user.setPhotoUrl(filePath);
            userDAO.save(user);
        } else if (entity instanceof Company) {
            Company company = companyDAO.findById(entity.getId());
            fileNameAndId = company.getId() + "_" + company.getName() + "_" + file.getOriginalFilename();
            filePath = Paths.get(UPLOAD_DIR, fileNameAndId).toString();

            // Eliminar imagen anterior si existe
            deleteImageByUrl(company.getPhotoUrl());
            company.setPhotoUrl(filePath);
            companyDAO.save(company);
        } else {
            throw new IllegalArgumentException("Entity type is not supported");
        }

        // Transferir el archivo a la ruta especificada
        file.transferTo(new File(filePath));

        return filePath;
    }

    private void deleteImageByUrl(String photoUrl) {
        if (photoUrl != null) {
            File fileToDelete = new File(photoUrl);
            fileToDelete.delete();
        }
    }

    public byte[] downloadImageFromFileSystem(T entity) throws IOException {
        String photoUrl;

        if (entity instanceof User) {
            User user = userDAO.findById(entity.getId());
            photoUrl = user.getPhotoUrl();
        } else if (entity instanceof Company) {
            Company company = companyDAO.findById(entity.getId());
            photoUrl = company.getPhotoUrl();
        } else {
            throw new IllegalArgumentException("Entity type is not supported");
        }

        if (photoUrl == null) {
            throw new RuntimeException("Error: Image not found");
        }

        return Files.readAllBytes(Paths.get(photoUrl));
    }

    public void deleteImage(T entity) {
        String photoUrl;

        if (entity instanceof User) {
            User user = userDAO.findById(entity.getId());
            photoUrl = user.getPhotoUrl();
            user.setPhotoUrl(null);
            userDAO.save(user);
        } else if (entity instanceof Company) {
            Company company = companyDAO.findById(entity.getId());
            photoUrl = company.getPhotoUrl();
            company.setPhotoUrl(null);
            companyDAO.save(company);
        } else {
            throw new IllegalArgumentException("Entity type is not supported");
        }
        deleteImageByUrl(photoUrl);
    }




}
