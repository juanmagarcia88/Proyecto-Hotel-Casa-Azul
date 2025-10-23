<template>
  <div id="bodyFormulario">
    <header class="headerReserva">
      <!-- Encabezado clickable que regresa al home -->
      <h1 @click="Home" class="tituloReserva">Mis reservas</h1>
    </header>

    <main>
      <div class="reservas_div">
        <div
          v-for="reserva in reservas"
          :key="reserva.id"
          class="card"
        >
          <p class="text-title">
            Reserva del {{ new Date(reserva.fecha_inicio).toLocaleDateString() }}
          </p>
          <p class="text-body">
            Fin: {{ new Date(reserva.fecha_fin).toLocaleDateString() }}
          </p>
          <p class="text-body">Habitaciones:</p>
          <p v-for="habitacion in reserva.habitaciones" :key="habitacion.numero">
            nº {{ habitacion.numero }} - {{ habitacion.tipo }}
          </p>
          <p class="text-body">Total: €{{ reserva.total }}</p>

          <!-- Botón para eliminar reserva -->
          <button class="card-button" @click="abrirConfirm(reserva.id)">
            Eliminar
          </button>
        </div>
      </div>

      <h2 v-if="reservas.length === 0" class="no-reservas">
        No tienes reservas realizadas.
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
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

const reservas = ref([]); // Lista de reservas del cliente
const route = useRoute();
const router = useRouter();
const dniCliente = route.query.dniCliente; // DNI del cliente pasado por query

const confirmacionVisible = ref(false); // Controla visibilidad del modal
let reservaAEliminar = null; // Almacena ID de reserva a eliminar
const mensajeModal = ref(null); // Contenido del mensaje dentro del modal

// Redirige al inicio
const Home = () => router.push("/");

// Obtener reservas del usuario desde el backend
const obtenerReservas = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/reserva/buscarporDni/${dniCliente}`);
    reservas.value = response.data;
  } catch (err) {
    console.error("Error al obtener las reservas:", err);
    abrirModalMensaje("Error al cargar las reservas", "error");
  }
};

// Abrir modal de confirmación para eliminar reserva
const abrirConfirm = (id) => {
  reservaAEliminar = id;
  mensajeModal.value = null;
  confirmacionVisible.value = true;
};

// Cancelar eliminación y cerrar modal
const cancelarEliminacion = () => {
  reservaAEliminar = null;
  confirmacionVisible.value = false;
};

// Cerrar modal tras mostrar mensaje
const cerrarModal = () => {
  confirmacionVisible.value = false;
  reservaAEliminar = null;
  mensajeModal.value = null;
};

// Mostrar mensaje en el modal
const abrirModalMensaje = (texto, tipo = "exito") => {
  mensajeModal.value = { texto, tipo };
};

// Confirmar eliminación de reserva
const confirmarEliminacion = async () => {
  try {
    await axios.delete(`http://localhost:8080/reserva/eliminar/${reservaAEliminar}`);
    reservas.value = reservas.value.filter(r => r.id !== reservaAEliminar);
    abrirModalMensaje("Reserva eliminada correctamente", "exito");
  } catch (err) {
    console.error("Error al eliminar la reserva", err);
    abrirModalMensaje("Hubo un error al eliminar la reserva", "error");
  }
};

// Carga reservas al montar el componente
onMounted(() => {
  obtenerReservas();
});
</script>