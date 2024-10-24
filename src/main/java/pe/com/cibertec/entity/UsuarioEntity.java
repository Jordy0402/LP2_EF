package pe.com.cibertec.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity { 
    @Id
    @Column(name = "correo", nullable = false, length = 60)
    private String correo;
    
    @Column(name = "password", nullable = false, length = 60)
    private String password;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "apellidos", nullable = false, length = 60)
    private String apellidos;
    
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;
    
    @Column(name = "foto_perfil", nullable = false)
    private String fotoPerfil;
    
    @Column(name = "url_imagen")
    private String urlImagen;  
}
