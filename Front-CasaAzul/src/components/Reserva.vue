<template>
  <!-- Encabezado con botón para volver -->
  <div id="bodyFormulario">
    <header class="headerReserva">
      <!-- Encabezado clickable que regresa al home -->
      <h1 @click="Home" class="tituloReserva">Último paso para reservar</h1>
    </header>

    <main>
      <!-- Formulario de pago -->
      <form v-if="!mensaje" id="formularioReserva" @submit.prevent="enviarFormulario">
        <label class="label" for="metodoPago">Método de Pago:</label>
        <select class="inputSelect" v-model="metodoPago" required>
          <option value="tarjeta">Tarjeta</option>
          <option value="paypal">PayPal</option>
          <option value="paysafecard">PaySafeCard</option>
        </select>

        <button class="inputSelect" type="submit">Finalizar Reserva</button>
      </form>

      <!-- Mensaje luego de enviar la reserva -->
      <h3 class="mensajeReserva" v-else>{{ mensaje }}</h3>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

const route = useRoute();
const router = useRouter();

const metodoPago = ref("tarjeta"); // Método de pago seleccionado
const habitaciones = ref([]); // Habitaciones seleccionada
const dniCliente = ref(""); // DNI del cliente
const mensaje = ref(null); // Mensaje de éxito o error

// Carga los datos de la reserva al montar el componente
onMounted(() => {
  habitaciones.value = JSON.parse(route.query.habitaciones ?? "[]");
  dniCliente.value = route.query.dniCliente ?? "";
});

// Función que envía la reserva al backend
const enviarFormulario = async () => {
  try {
    // Calcula el monto total sumando los precios de las habitaciones
    const montoTotal = habitaciones.value.reduce(
      (total, habitacion) => total + habitacion.precio,
      0
    );

    const reservaData = {
      fechaInicio: route.query.fecIni,
      fechaFin: route.query.fecFin,
      dniCliente: dniCliente.value,
      numerosHabitaciones: habitaciones.value.map((h) => h.numero),
      montoTotal,
    };

    const response = await axios.post("http://localhost:8080/reserva/crear", reservaData);
    if (response.status === 200) {
      mensaje.value = "Reserva realizada correctamente";
      setTimeout(() => router.push("/"), 2500); // Redirige después de 2.5s
    }
  } catch (error) {
    console.error("Error al crear la reserva", error);
    mensaje.value = "Ha salido mal la reserva. Inténtalo de nuevo.";
  }
};

// Función para volver a la página principal
const Home = () => {
  router.push("/");
};
</script>