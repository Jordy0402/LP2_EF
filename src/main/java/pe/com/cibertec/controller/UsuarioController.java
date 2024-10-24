package pe.com.cibertec.controller;

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
        return "registro"; // Cambiado a "registro" para que coincida con la vista de registro.
    }
    
    @PostMapping("/registrar_usuario")
    public String registrarUsuario(@ModelAttribute("usuario") UsuarioEntity usuarioFormulario,
                                   Model model, @RequestParam("foto") MultipartFile foto) {
        
        usuarioService.crearUsuario(usuarioFormulario, foto);
        return "redirect:/"; // Redirigir a la página de login después del registro.
    }
    
    @GetMapping("/")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "login";    
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute("usuario") UsuarioEntity usuarioFormulario,
                        Model model, HttpSession session) {
        boolean validarUsuario = usuarioService.validarUsuario(usuarioFormulario);
        if(validarUsuario) {
            session.setAttribute("usuario", usuarioFormulario.getCorreo());
            return "redirect:/Listar"; // Asegúrate de que esta ruta esté mapeada.
        }
        model.addAttribute("loginInvalido", "No existe el usuario");
        return "login"; // Permite volver a intentar el login si es inválido.
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
