<template>
  <div id="bodyFormulario">
    <header class="headerReserva">
      <!-- Título clickable que lleva a la página de inicio -->
      <h1 @click="Home" class="tituloReserva">Iniciar sesión como cliente</h1>
    </header>

    <main>
      <!-- Formulario de login del cliente -->
      <form id="formularioReserva" @submit.prevent="login">
        <label class="label" for="usuario">DNI:</label>
        <input class="inputSelect" type="text" id="usuario" v-model="usuario" required />

        <label class="label" for="contraseña">Contraseña:</label>
        <input class="inputSelect" type="password" id="contraseña" v-model="contraseña" required />

        <button class="inputSelect" type="submit">Iniciar sesión</button>

        <!-- Mensaje de error si las credenciales son inválidas o falla la conexión -->
        <p v-if="error" class="error">{{ error }}</p>
      </form>
    </main>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();
const usuario = ref("");
const contraseña = ref("");
const error = ref("");

// Función que redirige al inicio
const Home = () => {
  router.push("/");
};

// Función para autenticar al cliente con su DNI y contraseña
const login = async () => {
  try {
    const response = await axios.post("http://localhost:8080/cliente/login", {
      dni: usuario.value,
      contrasena: contraseña.value,
    });

    const cliente = response.data;
    if (cliente) {
      // Redirige a la página de reservas del cliente, pasando el DNI por query
      router.push({
        path: "/MisReservas",
        query: { dniCliente: cliente.dni },
      });
    } else {
      error.value = "DNI o contraseña incorrectos.";
    }
  } catch (err) {
    console.error("Error de inicio de sesión:", err);
    error.value = "Hubo un problema al verificar las credenciales. Inténtalo de nuevo.";
  }
};
</script>
