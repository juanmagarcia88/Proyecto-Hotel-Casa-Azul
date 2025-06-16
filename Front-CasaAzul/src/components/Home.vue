<template>
  <header id="header">
    <img id="logo" src="../assets/logoCasaAzul.jpg" alt="logoCasaAzul" />

    <button class="hamburguesa" @click="toggleMenu">
      ☰
    </button>

    <nav :class="{ abierto: menuAbierto }">
      <a id="botonHeader" href="#habitaciones">Habitaciones</a>
      <a id="botonHeader" href="#gastronomia">Gastronomía</a>
      <a id="botonHeader" href="#galeria">Galería</a>
      <a id="botonHeader" href="#acercaDe">Acerca de...</a>
    </nav>
  </header>

  <main>
    <section id="inicio">
      <div id="divFormulario">
        <form id="formularioFechas" @submit.prevent="reservar">
          <label for="start">Fecha de inicio:</label>
          <input v-model="fecIni" type="date" id="start" name="start-date" />

          <label for="end">Fecha de fin:</label>
          <input v-model="fecFin" type="date" id="end" name="end-date" />

          <!-- From Uiverse.io by PriyanshuGupta28 -->
          <button @click="reservar" class="pushable">
            <span class="shadow"></span>
            <span class="edge"></span>
            <span class="front"> Reservar </span>
          </button>

          <!-- From Uiverse.io by PriyanshuGupta28 -->
          <button @click="misReservas" class="pushable">
            <span class="shadow"></span>
            <span class="edge"></span>
            <span class="front"> Mis reservas </span>
          </button>

        </form>
      </div>

    </section>

    <section id="habitaciones">
      <h1>Habitaciones</h1>

      <div class="habitaciones">
        <div id="individual">
          <img id="fotoIndividual" src="../assets/casaAzulFoto15.jpg" alt="habitación individual" />

          <div id="textoIndividual">
            <h2>Habitación Individual</h2>

            <h3>
              20 m2
              <img id="imagenCapacidad" src="../assets/capacidad.png" alt="capacidad" />1
            </h3>

            <p>
              Habitaciones cómodas y silenciosas con una cuidada distribución,
              en un elegante edificio histórico totalmente renovado.
            </p>
          </div>
        </div>

        <div id="doble">
          <img id="fotoDoble" src="../assets/casaAzulFoto2.jpg" alt="Habitación Doble" />

          <div id="textoDoble">
            <h2>Habitación Doble</h2>

            <h3>
              30 m2
              <img id="imagenCapacidad" src="../assets/capacidad.png" alt="capacidad" />2
            </h3>

            <p>
              Descubre el encanto del sur en un espacio inspirado en la
              tradición andaluza, con detalles en cerámica artesanal y tonos
              cálidos.
            </p>
          </div>
        </div>

        <div id="suite">
          <img id="fotoSuite" src="../assets/casaAzulFoto3.jpg" alt="suite" />

          <div id="textoSuite">
            <h2>Habitación Suite</h2>

            <h3>
              60 m2
              <img id="imagenCapacidad" src="../assets/capacidad.png" alt="capacidad" />4
            </h3>

            <p>
              Disfruta del lujo y la elegancia en nuestra suite, un refugio sofisticado con toques de diseño clásico,
              materiales nobles y una atmósfera cálida que invita al descanso.
            </p>
          </div>
        </div>
      </div>
    </section>

    <section id="gastronomia">
      <h1>Gastronomía</h1>

      <div class="gastronomia">
        <p class="gastronomiaP">
          Durante las próximas semanas, nuestra propuesta de comidas y cenas se
          ofrecerá de manera temporal en nuestro encantador restaurante , ubicado
          en el interior de nuestro hotel.
        </p>

        <div class="contenedorfoto">
          <img class="fotogastronomia" src="../assets/casaAzulfoto4.png" alt="Gastronomia1" />
          <img class="fotogastronomia" src="../assets/casaAzulfoto5.png" alt="Gastronomia2" />
        </div>
      </div>
    </section>

    <section id="galeria">
      <h1>Galería</h1>

      <center>
        <div class="galeria">
          <div class="contenedorGaleria">
            <img class="fotoGaleria" src="../assets/casaAzulFoto6.jpg" alt="Galeria1" />
            <img class="fotoGaleria" src="../assets/casaAzulfoto7.jpg" alt="Galeria2" />
          </div>
          <div class="contenedorGaleria">
            <img class="fotoGaleria" src="../assets/casaAzulfoto13.jpg" alt="Galeria3" />
            <img class="fotoGaleria" src="../assets/casaAzulfoto14.jpg" alt="Galeria4" />
          </div>
          <div class="contenedorGaleria">
            <img class="fotoGaleria" src="../assets/casaAzulfoto10.jpg" alt="Galeria5" />
            <img class="fotoGaleria" src="../assets/casaAzulfoto11.jpg" alt="Galeria6" />
          </div>
          <div class="contenedorGaleria">
            <img class="fotoGaleria" src="../assets/casaAzulfoto12.jpg" alt="Galeria3" />
            <img class="fotoGaleria" src="../assets/casaAzulfoto9.jpg" alt="Galeria4" />
          </div>
        </div>
      </center>
    </section>
  </main>

  <section id="acercaDe">
    <footer>
      <div class="footer-container">
        <div class="footer-logo">
          <a href="#header">
            <img src="../assets/logoCasaAzul.jpg" alt="logoCasaAzul" class="footer-logo-img" />
          </a>
        </div>
        <div class="footer-social">
          <a href="https://www.instagram.com" target="_blank" class="footer-social-icon">
            <img src="../assets/instagram.jpg" alt="Instagram" />
          </a>
          <a href="https://www.facebook.com" target="_blank" class="footer-social-icon">
            <img src="../assets/facebook.jpg" alt="Facebook" />
          </a>
          <a href="https://www.twitter.com" target="_blank" class="footer-social-icon">
            <img src="../assets/x.png" alt="X" />
          </a>
        </div>
      </div>
      <div class="footer-bottom">
        <p>&copy; 2025 Casa Azul. Todos los derechos reservados.</p>
        <a id="admin" @click="admin">Login para admin</a>
      </div>
    </footer>
  </section>
</template>

<script setup>
// Importa herramientas de Vue
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";

// Instancia el enrutador
const router = useRouter();

// Variables reactivas para fechas y estado del menú
const fecIni = ref("");
const fecFin = ref("");
const menuAbierto = ref(false);

// Se ejecuta al montar el componente: añade animaciones a elementos al entrar en pantalla
onMounted(() => {
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('animar');
        observer.unobserve(entry.target); // Detiene la observación tras animar
      }
    });
  });

  // Observa las secciones de habitaciones y fotos de gastronomía
  const individual = document.querySelector('#individual');
  if (individual) observer.observe(individual);

  const doble = document.querySelector('#doble');
  if (doble) observer.observe(doble);

  const suite = document.querySelector('#suite');
  if (suite) observer.observe(suite);

  const fotosGastronomia = document.querySelectorAll('.fotogastronomia');
  fotosGastronomia.forEach(foto => observer.observe(foto));
});

// Navega a la página de reservas si se han seleccionado fechas
const reservar = () => {
  if (!fecIni.value || !fecFin.value) {
    alert("Selecciona una fecha de inicio y fin");
    return;
  }
  router.push({
    path: "/mostrarHab",
    query: { fecIni: fecIni.value, fecFin: fecFin.value },
  });
};

// Redirige al login del administrador
const admin = () => {
  router.push("/loginAdmin");
};

// Redirige al login de usuario para ver sus reservas
const misReservas = () => {
  router.push("/loginMisReservas");
};

// Alterna la visibilidad del menú responsive
const toggleMenu = () => {
  menuAbierto.value = !menuAbierto.value;
};
</script>
