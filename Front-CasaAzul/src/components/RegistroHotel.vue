<template>
  <!-- Encabezado con botón para volver -->
  <div id="bodyFormulario">
    <header class="headerReserva">
      <h1 @click="Home" class="tituloReserva">Crear una cuenta para reservar</h1>
    </header>

    <main>
      <!-- Formulario de registro -->
      <form id="formularioReserva" @submit.prevent="crearCliente">
        <label class="label" for="nombre">Nombre:</label>
        <input class="inputSelect" v-model="nombre" type="text" required />

        <label class="label" for="apellidos">Apellidos:</label>
        <input class="inputSelect" v-model="apellido" type="text" required />

        <label class="label" for="dni">DNI:</label>
        <input class="inputSelect" v-model="dni" type="text" required />

        <label class="label" for="email">Correo:</label>
        <input class="inputSelect" v-model="email" type="email" required />

        <label class="label" for="telefono">Teléfono:</label>
        <input class="inputSelect" v-model="telefono" type="tel" required />

        <label class="label" for="contrasena">Contraseña:</label>
        <input class="inputSelect" v-model="contrasena" type="password" required />

        <button class="inputSelect" type="submit">Crear cuenta</button>
      </form>
    </main>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";

const router = useRouter();
const route = useRoute();

// Datos del formulario
const nombre = ref("");
const apellido = ref("");
const dni = ref("");
const email = ref("");
const telefono = ref("");
const contrasena = ref("");

// Obtiene reserva temporal (si existe)
const fecIni = route.query.fecIni ?? "";
const fecFin = route.query.fecFin ?? "";
const habitaciones = JSON.parse(route.query.habitaciones ?? "[]");

// Envía los datos para crear un nuevo cliente
const crearCliente = async () => {
  const clienteData = {
    dni: dni.value,
    nombre: nombre.value,
    apellido: apellido.value,
    email: email.value,
    telefono: telefono.value,
    contrasena: contrasena.value,
  };

  try {
    await axios.post("http://localhost:8080/cliente", clienteData);

    // Si hay datos de reserva guardados, continúa con el flujo
    const reservaDatos = JSON.parse(localStorage.getItem('reservaDatos'));
    if (reservaDatos) {
      router.push({
        path: "/reserva",
        query: {
          fecIni: reservaDatos.fecIni,
          fecFin: reservaDatos.fecFin,
          habitaciones: JSON.stringify(reservaDatos.habitaciones),
          dniCliente: dni.value,
        },
      });
      localStorage.removeItem('reservaDatos');
    } else {
      console.error("No se encontraron datos de reserva.");
    }
  } catch (error) {
    console.error("Error al crear el cliente", error);
    alert("Hubo un error al crear el cliente. Inténtalo de nuevo.");
  }
};

// Redirige a la página principal
const Home = () => {
  router.push("/");
};
</script>
