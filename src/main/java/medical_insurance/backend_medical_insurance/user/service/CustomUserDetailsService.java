package medical_insurance.backend_medical_insurance.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import medical_insurance.backend_medical_insurance.user.entity.UserEntity;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // @Autowired
    // private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            UserEntity user = this.userService.getUserById(UUID.fromString(userId));
            if (user == null) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }

            // Convertir UserEntity a UserDetails
            return new org.springframework.security.core.userdetails.User(
                    user.email,
                    user.password,
                    user.active,
                    true,
                    true,
                    true,
                    getAuthorities(user.role.name));
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error al obtener el usuario", e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roleName) {
        // Devuelve una colección con el rol del usuario
        return Collections.singleton(new SimpleGrantedAuthority(roleName));
    }
}

// en el controller de user agregar @PreAuthorize("hasRole('ADMIN')") en el
// metodo createUser