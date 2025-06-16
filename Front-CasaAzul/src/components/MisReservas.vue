<template>
  <div id="bodyFormulario">
    <header class="headerReserva">
      <!-- Título que redirige a la pantalla principal -->
      <h1 @click="Home" class="tituloReserva">Mis reservas</h1>
    </header>

    <main>
      <div class="reservas_div">
        <!-- Lista de reservas del cliente -->
        <div
          v-for="reserva in reservas"
          :key="reserva.id"
          class="card"
          @click="eliminarReserva(reserva.id)"
        >
          <a class="card1" href="#">
            <p>Fecha de inicio: {{ new Date(reserva.fecha_inicio).toLocaleDateString() }}</p>
            <p>Fecha de fin: {{ new Date(reserva.fecha_fin).toLocaleDateString() }}</p>

            <p>Habitaciones:</p>
            <ul>
              <!-- Lista de habitaciones asociadas a la reserva -->
              <li
                v-for="habitacion in reserva.habitaciones"
                :key="habitacion.numero"
              >
                nº {{ habitacion.numero }} - Tipo: {{ habitacion.tipo }}
              </li>
            </ul>

            <p>Total: €{{ reserva.total }}</p>

            <!-- Ícono visual para eliminar -->
            <div class="go-corner">
              <div class="go-arrow">❌</div>
            </div>
          </a>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

const reservas = ref([]);
const route = useRoute();
const router = useRouter();
const dniCliente = route.query.dniCliente;

// Función para redirigir al inicio
const Home = () => {
  router.push("/");
};

// Función para obtener las reservas del cliente desde la API
const obtenerReservas = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/reserva/buscarporDni/${dniCliente}`
    );
    reservas.value = response.data;
  } catch (err) {
    console.error("Error al obtener las reservas:", err);
  }
};

// Función para eliminar una reserva seleccionada
const eliminarReserva = async (id) => {
  if (confirm("¿Está seguro de que desea eliminar esta reserva?")) {
    try {
      await axios.delete(`http://localhost:8080/reserva/eliminar/${id}`);
      // Elimina la reserva localmente de la lista
      reservas.value = reservas.value.filter(reserva => reserva.id !== id);
      alert("Reserva eliminada correctamente.");
    } catch (err) {
      console.error("Error al eliminar la reserva", err);
      alert("Hubo un error al eliminar la reserva.");
    }
  }
};

// Obtiene las reservas al montar el componente
onMounted(() => {
  obtenerReservas();
});
</script>
