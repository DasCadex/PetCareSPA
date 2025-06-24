package com.example.creacionusuario.controller;

import java.util.List;

import com.example.creacionusuario.dto.UsuarioDTO;
import com.example.creacionusuario.model.Rol;
import com.example.creacionusuario.model.Usuario;
import com.example.creacionusuario.service.RoleService;
import com.example.creacionusuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Obtiene la lista de todos los usuarios en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados con éxito",
            content = @Content(schema = @Schema(implementation = UsuarioDTO.class)))
    })
    @GetMapping("/user")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @Operation(summary = "Obtiene todos los roles disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de roles obtenida con éxito",
            content = @Content(schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "204", description = "No hay roles disponibles")
    })
    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> obtenerRoles() {
        List<Rol> roles = roleService.bucarRol();
        return roles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(roles);
    }

    @Operation(summary = "Crea un nuevo usuario con su rol respectivo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Error en la creación del usuario")
    })
    @PostMapping("/users")
    public ResponseEntity<?> crearUsuarios(@RequestBody Usuario usuario) {
        try {
            System.out.println("Password recibida: " + usuario.getPassword());

            Long roleId = usuario.getRol().getId();

            Usuario nuevoUsuario = usuarioService.crearUsuario(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getNombres(),
                usuario.getApellido(),
                usuario.getCorreo(),
                roleId
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Elimina un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Busca un usuario específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado con éxito",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> buscarPorid(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Edita la información de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
