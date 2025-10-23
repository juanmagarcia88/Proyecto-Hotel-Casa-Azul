<template>
  <div id="bodyFormulario">
    <header class="headerReserva">
      <!-- Título que redirige al login de admin -->
      <h1 @click="Home" class="tituloReserva">Reservas</h1>
    </header>

    <main>
      <!-- Lista de reservas -->
      <div class="reservas_div">
        <div
          v-for="reserva in reservas"
          :key="reserva.id"
          class="card"
        >
          <p class="text-title">Reserva ID: {{ reserva.id }}</p>
          <p class="text-body">Inicio: {{ new Date(reserva.fecha_inicio).toLocaleDateString() }}</p>
          <p class="text-body">Fin: {{ new Date(reserva.fecha_fin).toLocaleDateString() }}</p>
          <p class="text-body">Total: €{{ reserva.total }}</p>

          <!-- Botón para abrir modal de confirmación de eliminación -->
          <button class="card-button" @click="abrirConfirm(reserva.id)">
            Eliminar
          </button>
        </div>
      </div>

      <h2 v-if="reservas.length === 0" class="no-reservas">
        No hay reservas en pie actualmente en el hotel.
      </h2>

      <!-- Modal de confirmación / mensaje -->
      <div class="modal" :class="{ visible: confirmacionVisible }">
        <div class="modal-content">
          <template v-if="!mensajeModal">
            <p>¿Está seguro de que desea eliminar esta reserva?</p>
            <div class="modal-buttons">
              <button @click="confirmarEliminacion">Sí</button>
              <button @click="cancelarEliminacion">No</button>
            </div>
          </template>
          <template v-else>
            <p :class="mensajeModal.tipo">{{ mensajeModal.texto }}</p>
            <div class="modal-buttons">
              <button @click="cerrarModal">Cerrar</button>
            </div>
          </template>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const reservas = ref([]); // Lista de reservas obtenidas del backend
const confirmacionVisible = ref(false); // Controla la visibilidad del modal
let reservaAEliminar = null; // ID de la reserva que se desea eliminar
const mensajeModal = ref(null); // Mensaje mostrado dentro del modal (error o éxito)
const router = useRouter();

// Redirige al login de admin
const Home = () => router.push("/loginAdmin");

// Función para obtener token de localStorage
const getToken = () => localStorage.getItem("token");

// Configuración de headers con token
const axiosConfig = () => ({
  headers: {
    Authorization: `Bearer ${getToken()}`
  }
});

// Obtiene todas las reservas
const obtenerReservas = async () => {
  try {
    // Devuelve un array de reservas con campos: id, fecha_inicio, fecha_fin, total
    const response = await axios.get(
      "http://localhost:8080/reserva/mostrartodas",
      axiosConfig()
    );
    reservas.value = response.data;
  } catch (err) {
    console.error("Error al obtener las reservas", err);
    abrirModalMensaje("Error al cargar las reservas", "error");
  }
};

// Abre modal de confirmación
const abrirConfirm = (id) => {
  reservaAEliminar = id;
  mensajeModal.value = null;
  confirmacionVisible.value = true;
};

// Cierra modal después de mostrar mensaje
const cerrarModal = () => {
  confirmacionVisible.value = false;
  reservaAEliminar = null;
  mensajeModal.value = null;
};

// Muestra mensaje dentro del modal
const abrirModalMensaje = (texto, tipo = "exito") => {
  mensajeModal.value = { texto, tipo };
};

// Confirma eliminación
const confirmarEliminacion = async () => {
  try {
    await axios.delete(
      `http://localhost:8080/reserva/eliminar/${reservaAEliminar}`,
      axiosConfig()
    );
    // Elimina la reserva localmente
    reservas.value = reservas.value.filter(r => r.id !== reservaAEliminar);
    abrirModalMensaje("Reserva eliminada correctamente", "exito");
  } catch (err) {
    console.error("Error al eliminar la reserva", err);
    abrirModalMensaje("Hubo un error al eliminar la reserva", "error");
  }
};

// Cancela eliminación
const cancelarEliminacion = () => {
  reservaAEliminar = null;
  confirmacionVisible.value = false;
};

// Carga las reservas al montar el componente
onMounted(() => {
  // Si no hay token, redirige al login
  if (!getToken()) {
    router.push("/loginAdmin");
    return;
  }
  obtenerReservas();
});
</script>