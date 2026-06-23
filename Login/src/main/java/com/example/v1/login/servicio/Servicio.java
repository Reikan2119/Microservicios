package com.example.v1.login.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.v1.login.DTO.EliminarCuentaDTO;
import com.example.v1.login.DTO.LoginDTO;
import com.example.v1.login.DTO.RegistroDTO;
import com.example.v1.login.Model.Usuario;
import com.example.v1.login.repositorio.Repositorio;


@Service
public class Servicio {
@Autowired
    private Repositorio repositorio;

    public String registrarUsuario(RegistroDTO dto) {
        // VERIFICADOR GMAIL: Se convierte a minúsculas y se revisa si termina estrictamente en "@gmail.com"
        if (dto.getEmail() == null || !dto.getEmail().toLowerCase().endsWith("@gmail.com")) {
            throw new IllegalArgumentException("Error: Solo se permiten correos con dominio @gmail.com");
        }

        // VALIDACIÓN UNICIDAD DE USERNAME
        if (repositorio.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Error: El nombre de usuario ya está registrado.");
        }

        // VALIDACIÓN UNICIDAD DE CORREO
        if (repositorio.existsByEmail(dto.getEmail().toLowerCase())) {
            throw new IllegalArgumentException("Error: El correo electrónico ya está en uso.");
        }

        // GUARDADO DIRECTO: Guardamos la contraseña en texto plano, tal como la envió el usuario.
        Usuario nuevoUsuario = new Usuario(
                null, // ID se genera automáticamente
                dto.getUsername(),
                dto.getEmail().toLowerCase(), // Guardamos el correo en minúsculas para mantener la consistencia
                dto.getPassword(), // Contraseña sin encriptar
                dto.getRol()
        );

        repositorio.save(nuevoUsuario);
        return "Usuario creado con éxito con el rol: " + nuevoUsuario.getRol();
    }

    public String loginUsuario(LoginDTO dto) {
        // BUSQUEDA
        Usuario usuario = repositorio.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Error: Usuario no encontrado."));

        // VALIDACIÓN SIMPLE DE CONTRASEÑA
        if (!usuario.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Error: Contraseña incorrecta.");
        }

        // Retornamos el rol para confirmar el inicio de sesión y saber qué tipo de cuenta es.
        return "Login exitoso. Bienvenido, tu rol es: " + usuario.getRol();
    }

    public String eliminarCuentaUsuario(EliminarCuentaDTO dto) {
        // BUSQUEDA: Buscamos si el usuario existe mediante su username
        Usuario usuario = repositorio.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Error: Usuario no encontrado."));

        // CONFIRMACIÓN DE IDENTIDAD:
        // Comparamos la contraseña en texto plano enviada con la guardada en la base de datos.
        if (!usuario.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Error: Contraseña incorrecta. Autorización denegada.");
        }

        // Si la contraseña es idéntica, procedemos al borrado físico de la fila en la BD
        repositorio.delete(usuario);
        return "Cuenta eliminada correctamente del sistema.";
    }
}
