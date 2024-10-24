package pe.com.cibertec.controller;

import java.io.File;
import java.io.IOException; 

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.entity.UsuarioEntity;
import pe.com.cibertec.service.UsuarioService;

@Controller
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
  
    @GetMapping("/registrar_usuario")
    public String mostrarRegistrarUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "registro"; 
    }
    

    @PostMapping("/registrar_usuario")
    public String registrarUsuario(@ModelAttribute("usuario") UsuarioEntity usuarioFormulario,
                                   Model model, @RequestParam("foto") MultipartFile foto) {
        try {
          
            if (foto.isEmpty()) {
                model.addAttribute("error", "La foto de perfil es obligatoria.");
                return "registro";
            }

            
            String nombreArchivo = guardarFotoPerfil(foto);
            usuarioFormulario.setUrlImagen(nombreArchivo); 

          
            usuarioService.crearUsuario(usuarioFormulario, foto);
            return "redirect:/listar_productos"; 
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Error de integridad de datos: " + e.getMessage());
            return "registro"; 
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "registro"; 
        }
    }
    
    private String guardarFotoPerfil(MultipartFile foto) {
        if (foto.isEmpty()) {
            return null;
        }

        String directorio = new File("src/main/resources/static/usuario_foto").getAbsolutePath();
        String nombreArchivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();

        try {
            
            File archivoDestino = new File(directorio, nombreArchivo);
            foto.transferTo(archivoDestino);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return nombreArchivo;
    }

    
    
    @GetMapping("/")
    public String mostrarLogin(Model model, HttpSession session) {
        String correoUsuario = (String) session.getAttribute("usuario");
        
       
        if (correoUsuario != null) {
            UsuarioEntity usuario = usuarioService.buscarUsuarioPorCorreo(correoUsuario);
            model.addAttribute("usuario", usuario);
        }
        
        model.addAttribute("usuarioForm", new UsuarioEntity()); // Si no está logueado, form vacío
        return "login";    
    }
    
   
    @PostMapping("/login")
    public String login(@ModelAttribute("usuario") UsuarioEntity usuarioFormulario,
                        Model model, HttpSession session) {
        boolean validarUsuario = usuarioService.validarUsuario(usuarioFormulario);
        if(validarUsuario) {
            session.setAttribute("usuario", usuarioFormulario.getCorreo());
            return "redirect:/listar_productos"; 
        }
        model.addAttribute("loginInvalido", "No existe el usuario");
        return "login"; 
    }
    
    // 
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
