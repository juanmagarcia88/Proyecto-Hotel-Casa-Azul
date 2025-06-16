package es.hotel_back_v2.hotelV2.controllers;

import es.hotel_back_v2.hotelV2.model.Cliente;
import es.hotel_back_v2.hotelV2.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //añadir cliente
    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
    }

    //eliminar cliente por dni
    @DeleteMapping("/eliminar/{dni}")
    public void delete(@PathVariable String dni) {
        clienteService.deleteCliente(dni);
    }

    //mostrar cliente por dni
    @GetMapping("/buscar/{dni}")
    public ResponseEntity<Optional<Cliente>> findCliente(@PathVariable String dni) {
        return ResponseEntity.ok(clienteService.findCliente(dni));
    }

    //modificar cliente por dni
    @PutMapping("/modificarpordni/{dni}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String dni, @RequestBody Cliente clienteActualizado) {

        return ResponseEntity.ok(clienteService.updateCliente(dni, clienteActualizado));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String dni = credentials.get("dni");
            String contrasena = credentials.get("contrasena");

            Cliente cliente = clienteService.login(dni, contrasena);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
