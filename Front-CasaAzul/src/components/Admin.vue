<template>
  <div id="bodyFormulario">
    <header class="headerReserva">
      <h1 @click="Home" class="tituloReserva">Reservas</h1>
    </header>

    <main>
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

          <!-- Botón eliminar -->
          <button class="card-button" @click="abrirConfirm(reserva.id)">
            Eliminar
          </button>
        </div>
      </div>

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

const reservas = ref([]);
const router = useRouter();
const confirmacionVisible = ref(false);
let reservaAEliminar = null;
const mensajeModal = ref(null); // Contenido del mensaje dentro del modal

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

// Abrir modal de confirmación
const abrirConfirm = (id) => {
  reservaAEliminar = id;
  mensajeModal.value = null;
  confirmacionVisible.value = true;
};

// Cerrar modal después de mostrar mensaje
const cerrarModal = () => {
  confirmacionVisible.value = false;
  reservaAEliminar = null;
  mensajeModal.value = null;
};

// Mostrar mensaje dentro del modal
const abrirModalMensaje = (texto, tipo = "exito") => {
  mensajeModal.value = { texto, tipo };
};

// Confirmar eliminación
const confirmarEliminacion = async () => {
  try {
    await axios.delete(
      `http://localhost:8080/reserva/eliminar/${reservaAEliminar}`,
      axiosConfig()
    );
    reservas.value = reservas.value.filter(r => r.id !== reservaAEliminar);
    abrirModalMensaje("Reserva eliminada correctamente", "exito");
  } catch (err) {
    console.error("Error al eliminar la reserva", err);
    abrirModalMensaje("Hubo un error al eliminar la reserva", "error");
  }
};

// Cancelar eliminación
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