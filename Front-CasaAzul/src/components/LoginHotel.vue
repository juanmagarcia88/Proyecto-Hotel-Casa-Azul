<template>
  <!-- Encabezado con botón para volver -->
  <div id="bodyFormulario">
    <header class="headerReserva">
      <h1 @click="Home" class="tituloReserva">Iniciar sesión para reservar</h1>
    </header>

    <main>
      <!-- Formulario de inicio de sesión -->
      <form id="formularioReserva" @submit.prevent="iniciarSesion">
        <label class="label" for="dni">DNI:</label>
        <input class="inputSelect" v-model="dni" type="text" required />

        <label class="label" for="contrasena">Contraseña:</label>
        <input class="inputSelect" v-model="contrasena" type="password" required />

        <!-- Mensaje de error bonito -->
        <p v-if="error" class="errorMensaje">{{ error }}</p>

        <button class="inputSelect" type="submit">Iniciar sesión</button>

        <!-- Enlace para ir al registro -->
        <a class="noRegistrado" @click="Registro">¿Aún no está registrado?</a>
      </form>
    </main>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { api } from '../api.js';

const dni = ref("");
const contrasena = ref("");
const error = ref("");

const route = useRoute();
const router = useRouter();

// Datos de la reserva pasada por query
const fecIni = route.query.fecIni ?? "";
const fecFin = route.query.fecFin ?? "";
const habitaciones = JSON.parse(route.query.habitaciones ?? "[]");

// Intenta iniciar sesión y redirigir a la reserva
const iniciarSesion = async () => {
  error.value = ""; // limpiar error previo
  try {
    const response = await api.post("/cliente/login", {
      dni: dni.value,
      contrasena: contrasena.value,
    });

    const cliente = response.data;

    // Redirige a la vista de reserva final
    router.push({
      path: "/reserva",
      query: {
        fecIni,
        fecFin,
        habitaciones: JSON.stringify(habitaciones),
        dniCliente: cliente.dni,
      },
    });
  } catch (err) {
    error.value = "DNI o contraseña incorrectos.";
    console.error("Error de inicio de sesión:", err);
  }
};

// Redirige al formulario de registro
const Registro = () => {
  router.push("/RegistroHotel");
};

// Redirige a la página principal
const Home = () => {
  router.push("/");
};
</script>
