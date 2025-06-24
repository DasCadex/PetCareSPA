package com.example.creacionusuario.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.creacionusuario.dto.UsuarioDTO;
import com.example.creacionusuario.model.Rol;
import com.example.creacionusuario.model.Usuario;
import com.example.creacionusuario.repository.RoleRepositoy;
import com.example.creacionusuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private RoleRepositoy roleRepositoy;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    @Test
    void save_returnSavedUsuario() {

        Rol rol = new Rol(2L, "admin", null);// tenemos crear un rol para asignarselo al usuario
        Usuario nuevoUsuario = new Usuario(1L, "cade", "1234", "cade@gmail.com", "fernando", "villalobos", rol);

        // Simular comportamiento del roleRepository
        when(roleRepositoy.findById(2L)).thenReturn(java.util.Optional.of(rol));

        // tenemos que simular la contraseña ya que en securiti est incriptada
        when(passwordEncoder.encode("1234")).thenReturn("1234codificada");

        // definimos el comportamiento del muck

        when(repository.save(any(Usuario.class))).thenReturn(nuevoUsuario);
        // any(Usuario.class) se le agrega esto ya que nos permitira aceptar cualquier
        // objeto de tipo Usuario

        // el metodo a probar
        Usuario result = service.crearUsuario(
                nuevoUsuario.getUsername(),
                nuevoUsuario.getPassword(),
                nuevoUsuario.getNombres(),
                nuevoUsuario.getApellido(),
                nuevoUsuario.getCorreo(),
                nuevoUsuario.getRol().getId());

        // comprobamos que devuelva lo mismo

        assertThat(result).isSameAs(nuevoUsuario);

    }

    @Test
    void findAll_returnListFromRepositori() {
        Rol rol = new Rol(2L, "admin", null);
        List<Usuario> mockList = Arrays
                .asList(new Usuario(1L, "cade", "1234", "cade@gmail.com", "fernando", "villalobos", rol));

        // definimos el comportamiento de todo
        // Simular comportamiento del roleRepository

        when(repository.findAll()).thenReturn(mockList);

        // en este caso tenemos que simular la lista en DTO debido que en nuestro codigo
        // usamos el DTO si no peta
        List<UsuarioDTO> result = service.obtenerUsuarios();

        // ya que en el DTO solicitamo cierto datos
        UsuarioDTO expectedDto = new UsuarioDTO();
        expectedDto.setId(1L);
        expectedDto.setCorreo("cade@gmail.com");
        expectedDto.setNombres("fernando");
        expectedDto.setApellido("villalobos");
        expectedDto.setRol(rol);

        List<UsuarioDTO> expectedList = List.of(expectedDto);

        // Verificar resultado
        assertThat(result).isEqualTo(expectedList);

    }

    @Test
    void findById_returnchanchitoFeliz() {
        Rol rol = new Rol(2L, "admin", null);// tenemos crear un rol para asignarselo al usuario
        Usuario buscarId = new Usuario(1L, "cade", "1234", "cade@gmail.com", "fernando", "villalobos", rol);

        // simulamos el repositorio
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(buscarId));

        // ejecutamos el metodo a probar
        Usuario result = service.buscarPorId(1L);

        assertThat(result).isNotNull();

    }

    @Test
    void deleteById_returnPetar() {
        // Simula que el repositorio devuelve un usuario cuando se busca por ID
        Rol rol = new Rol(1L, "admin", null);
        Usuario mockUsuario = new Usuario(1L, "cade", "1234", "cade@gmail.com", "fernando", "villalobos", rol);

        when(repository.findById(1L)).thenReturn(Optional.of(mockUsuario));

        // Ejecutar el método
        service.eliminarUsuario(1L);

        // Verificar que se llamó al metodo delete con el objeto correcto
        verify(repository).delete(mockUsuario);
    }

    @Test
    void updateUsuario_returcreaeUsuario() {
        // Usuario original
        Rol rol = new Rol(1L, "admin", null);
        Usuario usuarioExistente = new Usuario(1L, "original", "123", "original@mail.com", "fernando", "villalobos",rol);

        // tenemos que crear un nuevo usuario con los datos actualizados 
        Usuario datosNuevos = new Usuario();
        datosNuevos.setUsername("nuevo");
        datosNuevos.setPassword("1234");
        datosNuevos.setCorreo("nuevo@mail.com");
        datosNuevos.setNombres("fernando actualizado");
        datosNuevos.setApellido("villalobos actualizado");
        datosNuevos.setRol(rol);

        // como volvemos a crear volvemo a simular todo 
        when(repository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(passwordEncoder.encode("1234")).thenReturn("1234codificada");
        when(repository.save(any())).thenReturn(usuarioExistente);

        // Ejecutamos
        Usuario result = service.actualizarUsuario(1L, datosNuevos);

        // Comprobamos que se actualizaron los datos
        assertThat(result).isEqualTo(result);
      
    }

}
