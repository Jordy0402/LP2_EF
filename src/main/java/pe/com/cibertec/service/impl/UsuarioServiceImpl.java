package pe.com.cibertec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;  

import pe.com.cibertec.entity.UsuarioEntity;
import pe.com.cibertec.repository.UsuarioRepository;
import pe.com.cibertec.service.UsuarioService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Directorio donde se almacenarán las imágenes
    private final String uploadDirectory = "uploads/";

    @Override
    public void saveUser(UsuarioEntity user, MultipartFile profilePicture) {
        // Encriptar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        
        userRepository.save(user);

        
        if (!profilePicture.isEmpty()) {
            try {
                // Generar un nombre único para la imagen usando el ID del usuario
                String fileName = user.getId() + "_" + profilePicture.getOriginalFilename();

                // Ruta completa del archivo
                Path filePath = Paths.get(uploadDirectory + fileName);

                // Escribir el archivo en el sistema
                Files.write(filePath, profilePicture.getBytes());

                // Almacenar la ruta de la imagen en la entidad Usuario
                user.setFotoPerfil(fileName);  // Asegúrate de que esta línea esté correcta

                // Actualizar el usuario con la ruta de la imagen
                userRepository.save(user);

            } catch (IOException e) {
                e.printStackTrace();
                // Aquí podrías agregar más manejo de excepciones si es necesario
            }
        }
    }
}