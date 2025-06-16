<template>
  <div id="bodyFormulario">
    <header class="headerReserva">
      <!-- Botón que redirige a la página de inicio -->
      <h1 @click="Home" class="tituloReserva">Iniciar sesión como admin</h1>
    </header>

    <main>
      <!-- Formulario de inicio de sesión para admin -->
      <form id="formularioReserva" @submit.prevent="login">
        <label class="label" for="usuario">Usuario:</label>
        <input class="inputSelect" type="text" id="usuario" v-model="usuario" required>

        <label class="label" for="contraseña">Contraseña:</label>
        <input class="inputSelect" type="password" id="contraseña" v-model="contraseña" required>

        <button class="inputSelect" type="submit">Iniciar sesión</button>

        <!-- Mensaje de error si las credenciales no son válidas -->
        <p v-if="error" class="error">{{ error }}</p>
      </form>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// Variables reactivas para almacenar los datos del formulario
const usuario = ref('');
const contraseña = ref('');
const error = ref('');

// Redirige al inicio
const Home = () => {
  router.push("/");
};

// Función que valida el login del admin con credenciales fijas
const login = () => {
  if (usuario.value === "admin255" && contraseña.value === "0361") {
    router.push("/admin"); // Si son válidas, redirige a la vista de administrador
  } else {
    error.value = "Usuario o contraseña incorrectos"; // Si no, muestra error
  }
};
</script>