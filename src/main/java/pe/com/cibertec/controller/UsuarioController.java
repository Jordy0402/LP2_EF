package pe.com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;  // Para el manejo del archivo
import jakarta.servlet.http.HttpSession;
import pe.com.cibertec.entity.UsuarioEntity;
import pe.com.cibertec.service.UsuarioService;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UsuarioEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UsuarioEntity user, @RequestParam("profilePicture") MultipartFile file) {
        usuarioService.saveUser(user, file);
        return "redirect:/login";
    }
        
       // Cerrar sesión
        @GetMapping("/logout")
        public String cerrarSesion(HttpSession session) {
            session.invalidate();  // Cierra la sesión del usuario
            return "redirect:/login";
        }
    }