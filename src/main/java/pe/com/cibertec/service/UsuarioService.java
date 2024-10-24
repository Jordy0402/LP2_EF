package pe.com.cibertec.service;

import org.springframework.web.multipart.MultipartFile;  
import pe.com.cibertec.entity.UsuarioEntity;

public interface UsuarioService {
    void saveUser(UsuarioEntity user, MultipartFile profilePicture);
}
