<template>
  <div id="bodyFormulario">
    <header class="headerReserva">
      <!-- Título que redirige a la página de inicio -->
      <h1 @click="Home" class="tituloReserva">Iniciar sesión como admin</h1>
    </header>

    <main>
      <!-- Formulario de inicio de sesión para admin -->
      <form id="formularioReserva" @submit.prevent="login">
        <label class="label" for="usuario">Usuario:</label>
        <input class="inputSelect" type="text" id="usuario" v-model="usuario" required>

        <label class="label" for="contrasena">Contraseña:</label>
        <input class="inputSelect" type="password" id="contrasena" v-model="contrasena" required>

        <!-- Mensaje de error si las credenciales son incorrectas -->
        <p v-if="error" class="errorMensaje">{{ error }}</p>

        <button class="inputSelect" type="submit">Iniciar sesión</button>

      </form>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

// Variables reactivas para almacenar los datos del formulario
const usuario = ref('');
const contrasena = ref('');
const error = ref('');

// Redirige al inicio
const Home = () => {
  router.push("/");
};

// Función que valida el login del admin
const login = async () => {
  try {
    // Envía {usuario, contrasena} y recibe {token, rol}
    const res = await axios.post("http://localhost:8080/admin/login", {
      usuario: usuario.value,
      contrasena: contrasena.value,
    });
    // Guardar token y rol en localStorage
    localStorage.setItem("token", res.data.token);
    localStorage.setItem("rol", res.data.rol);
    // Redirigir al dashboard del admin
    router.push("/admin");
  } catch (e) {
    error.value = "Usuario o contraseña incorrectos";
  }
};
</script>