<template>
  <div id="bodyFormulario">
    <header class="headerReserva">
      <!-- Botón que regresa al login del admin -->
      <h1 @click="Home" class="tituloReserva">Reservas</h1>
    </header>

    <main>
      <div class="reservas_div">
        <!-- Lista de todas las reservas, cada una puede eliminarse al hacer click -->
        <div
          v-for="reserva in reservas"
          :key="reserva.id"
          class="card"
          @click="eliminarReserva(reserva.id)"
        >
          <a class="card1" href="#">
            <p>ID: {{ reserva.id }}</p>
            <p>Fecha de inicio: {{ new Date(reserva.fecha_inicio).toLocaleDateString() }}</p>
            <p>Fecha de fin: {{ new Date(reserva.fecha_fin).toLocaleDateString() }}</p>
            <p>Total: €{{ reserva.total }}</p>
            <div class="go-corner">
              <div class="go-arrow">
                ❌
              </div>
            </div>
          </a>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

// Lista reactiva donde se almacenan las reservas obtenidas del backend
const reservas = ref([]);
const router = useRouter();

// Redirige al login de admin
const Home = () => {
  router.push("/loginAdmin");
};

// Obtiene todas las reservas desde el servidor
const getReservas = async () => {
  try {
    const response = await axios.get("http://localhost:8080/reserva/mostrartodas");
    reservas.value = response.data;
  } catch (err) {
    console.error("Error al obtener las reservas", err);
  }
};

// Elimina una reserva por su ID tras confirmación del usuario
const eliminarReserva = async (id) => {
  if (confirm("¿Está seguro de que desea eliminar esta reserva?")) {
    try {
      await axios.delete(`http://localhost:8080/reserva/eliminar/${id}`);
      // Actualiza la lista eliminando la reserva localmente
      reservas.value = reservas.value.filter(reserva => reserva.id !== id);
      alert("Reserva eliminada correctamente.");
    } catch (err) {
      console.error("Error al eliminar la reserva", err);
      alert("Hubo un error al eliminar la reserva.");
    }
  }
};

// Carga las reservas al montar el componente
onMounted(() => {
  getReservas();
});
</script>