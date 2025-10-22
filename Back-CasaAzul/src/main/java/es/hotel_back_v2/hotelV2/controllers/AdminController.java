package es.hotel_back_v2.hotelV2.controllers;

import es.hotel_back_v2.hotelV2.services.AdminService;
import es.hotel_back_v2.hotelV2.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtService jwtService;

    // Endpoint para autenticar administrador. Devuelve un JWT si las credenciales son correctas
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String usuario = credentials.get("usuario");
        String contrasena = credentials.get("contrasena");

        try {
            if (adminService.login(usuario, contrasena)) {
                String token = jwtService.generateToken(usuario);
                return ResponseEntity.ok(Map.of("token", token, "rol", "ADMIN"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
