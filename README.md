# Proyecto Integrado - Hotel Casa Azul

Este proyecto ha sido desarrollado como parte del módulo de Proyecto Integrado del segundo curso del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Web (2º DAW). Bajo la tutela del profesor Manuel Arroyo Juan, los alumnos Jaime Araque Ruíz, Juan Manuel García Flores y Rafael Martínez Torralba han diseñado y construido una solución web completa para la gestión interna del Hotel Casa Azul.

El objetivo principal del proyecto es ofrecer una herramienta digital que facilite al personal del hotel la administración de clientes, reservas, habitaciones y pagos, mejorando así la eficiencia operativa y la experiencia del cliente. La plataforma está compuesta por un frontend desarrollado en **Vue.js**, un backend basado en **Java con Spring Boot**, y una base de datos **MySQL** que garantiza el almacenamiento estructurado y persistente de toda la información.

Gracias a esta solución, el Hotel Casa Azul puede automatizar tareas clave, reducir errores administrativos y ofrecer un servicio más moderno, profesional y accesible desde cualquier dispositivo con conexión a internet.

Este repositorio contiene todo el código fuente, documentación y detalles técnicos del sistema desarrollado.

# Manual para su instalación y ejecución

## BASE DE DATOS (MySql)

### Abre tu cliente MySQL (MySQL Workbench, consola, phpMyAdmin, etc.).
### Crea la base de datos y selecciona su uso:
     CREATE DATABASE hotel;
     USE hotel;
### Crea el usuario y asigna permisos (si no existe):
     CREATE USER 'hotel'@'localhost' IDENTIFIED BY '1234';
     GRANT ALL PRIVILEGES ON hotel.* TO 'hotel'@'localhost';
     FLUSH PRIVILEGES;
### Crea las tablas según las entidades del proyecto:

-- Tabla Cliente
CREATE TABLE cliente (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL,
    contrasena VARCHAR(100) NOT NULL
);

-- Tabla Habitacion
CREATE TABLE habitacion (
    numero BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20),
    capacidad INT NOT NULL
);

-- Tabla Reserva
CREATE TABLE reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    total DECIMAL(10,2),
    cliente_id VARCHAR(20),
    FOREIGN KEY (cliente_id) REFERENCES cliente(dni)
);

-- Tabla Pago
CREATE TABLE pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    metodo VARCHAR(20) NOT NULL,
    factura VARCHAR(100),
    fecha DATE NOT NULL,
    reserva_id BIGINT,
    FOREIGN KEY (reserva_id) REFERENCES reserva(id)
);

-- Tabla intermedia Reserva-Habitacion (Many-to-Many)
CREATE TABLE reserva_habitacion (
    reserva_id BIGINT,
    habitacion_num BIGINT,
    PRIMARY KEY (reserva_id, habitacion_num),
    FOREIGN KEY (reserva_id) REFERENCES reserva(id),
    FOREIGN KEY (habitacion_num) REFERENCES habitacion(numero)
);

### Edita el fichero application.properties para configurar la conexión a MySQL:
spring.datasource.url=jdbc:mysql://localhost:3306/hotel
spring.datasource.username=hotel
spring.datasource.password=1234

### Después ejecuta el proyecto:
     mvn spring-boot:run¡

## BACKEND (SpringBoot)

### Requisitos Previos:
- Java 17 o superior
- Maven
- Spring Boot

### Descarga del Proyecto:
git clone https://github.com/IESJandula/hotel_casa_azul.git

cd Back-CasaAzul

### Configuración de la Base de Datos (MySQL):
Edita el fichero application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/hotel

spring.datasource.username=hotel

spring.datasource.password=1234


### Después ejecuta el proyecto.
El backend se iniciará en:
http://localhost:8080

## Frontend (Vue)

### Requisitos Previos:
- NPM (el gestor de paquetes incluido con Node.js) para instalar las dependencias del proyecto y ejecutar los scripts de construcción y arranque.

### Proceso de instalación y ejecución:
Clona el repositorio.

cd Front-CasaAzul

Instala las dependencias:
     npm install
### Ejecuta el proyecto:
     npm run dev

El frontend se iniciará en:
http://localhost:5173

# Galería

![Imagen Hotel Casa Azul](assets/CasaAzul1.png)

![Imagen Hotel Casa Azul Móvil](assets/CasaAzul2.png)

![Imagen Habitaciones Hotel Casa Azul](assets/CasaAzul3.png)

![Imagen Footer Hotel Casa Azul](assets/CasaAzul4.png)

