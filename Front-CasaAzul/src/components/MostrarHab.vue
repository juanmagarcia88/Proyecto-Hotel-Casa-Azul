<template>
  <!-- Encabezado con botón para volver -->
  <div id="bodyFormulario">
    <header class="headerReserva">
      <h1 @click="goHome" class="tituloReserva">Escoger habitaciones</h1>
    </header>

    <main>
      <!-- Lista de habitaciones disponibles -->
      <div class="habitacionesDisponibles">
        <div
          v-for="habitacion in habitaciones"
          :key="habitacion.numero"
          class="cookieCard"
        >
          <!-- Información de cada habitación -->
          <p class="cookieHeading">Tipo: {{ habitacion.tipo }}</p>
          <p class="cookieDescription">Precio: ${{ habitacion.precio }}</p>
          <p class="cookieDescription">ID de la habitación: {{ habitacion.numero }}</p>
          <!-- Botón para seleccionar o quitar habitación -->
          <button class="acceptButton" @click="toggleSeleccion(habitacion)">
            {{ estaSeleccionada(habitacion) ? "Quitar" : "Seleccionar" }}
          </button>
        </div>
      </div>

      <!-- Botón para continuar con la reserva -->
      <div class="contenedorBoton" @click="finalizarSeleccion">
        <button id="botonHabs">Continuar con la reserva</button>
      </div>
    </main>
  </div>
</template>

<script setup>
// Importaciones de Vue y librerías
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";

const router = useRouter();
const route = useRoute();

const habitaciones = ref([]); // Lista de habitaciones disponibles
const selectedRooms = ref([]); // Lista de habitaciones seleccionadas

// Fechas desde los parámetros de la URL
const fecIni = route.query.fecIni || "";
const fecFin = route.query.fecFin || "";

// Función para obtener habitaciones disponibles desde backend
const getHabitaciones = async () => {
  try {
    const response = await axios.get("http://localhost:8080/habitacion/mostrarDisponibles");
    habitaciones.value = response.data;
  } catch (err) {
    console.error("Error al obtener las habitaciones", err);
  }
};

// Volver a la página anterior
const goHome = () => {
  router.back();
};

// Agrega o quita habitación de la selección
const toggleSeleccion = (habitacion) => {
  const index = selectedRooms.value.findIndex((h) => h.numero === habitacion.numero);
  if (index === -1) {
    selectedRooms.value.push(habitacion);
  } else {
    selectedRooms.value.splice(index, 1);
  }
};

// Verifica si una habitación está seleccionada
const estaSeleccionada = (habitacion) => {
  return selectedRooms.value.some((h) => h.numero === habitacion.numero);
};

// Finaliza la selección de habitaciones y redirige a login
const finalizarSeleccion = () => {
  if (selectedRooms.value.length === 0) {
    alert("Selecciona al menos una habitación.");
    return;
  }

  // Guarda datos temporalmente
  localStorage.setItem("reservaDatos", JSON.stringify({ fecIni, fecFin, habitaciones: selectedRooms.value }));

  // Redirige al login con los datos
  router.push({
    path: "/loginHotel",
    query: {
      fecIni,
      fecFin,
      habitaciones: JSON.stringify(selectedRooms.value),
    },
  });
};

// Carga habitaciones al montar el componente
onMounted(() => {
  getHabitaciones();
});
</script>