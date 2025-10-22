package es.hotel_back_v2.hotelV2.services;

import es.hotel_back_v2.hotelV2.model.Admin;
import es.hotel_back_v2.hotelV2.repositories.AdminRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Crea una cuenta admin por defecto si no existe
    @PostConstruct
    public void initAdmin() {
        if (adminRepository.findByUsuario("admin255").isEmpty()) {
            Admin admin = new Admin("admin255", passwordEncoder.encode("0361"));
            adminRepository.save(admin);
            System.out.println("✅ Cuenta admin creada automáticamente (usuario: admin255)");
        }
    }

    /**
     * Valida login de admin
     * @param usuario nombre de usuario del admin
     * @param contrasena contraseña ingresada
     * @return true si las credenciales son correctas
     */
    public boolean login(String usuario, String contrasena) {
        Optional<Admin> adminOpt = adminRepository.findByUsuario(usuario);

        if (adminOpt.isPresent()) {
            return passwordEncoder.matches(contrasena, adminOpt.get().getContrasena());
        } else {
            throw new RuntimeException("Admin no encontrado");
        }
    }
}
