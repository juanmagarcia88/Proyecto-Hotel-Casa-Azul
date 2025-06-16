import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/Home.vue'
import Reserva from '../components/Reserva.vue'
import LoginAdmin from '../components/LoginAdmin.vue'
import Admin from '../components/Admin.vue'
import MostrarHab from '../components/MostrarHab.vue'
import LoginMisReservas from '../components/LoginMisReservas.vue'
import RegistroHotel from '../components/RegistroHotel.vue'
import LoginHotel from '../components/LoginHotel.vue'
import MisReservas from '../components/MisReservas.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/reserva',
      name: 'reserva',
      component: Reserva,
    },
    {
      path: '/loginAdmin',
      name: 'loginAdmin',
      component: LoginAdmin,
    },
    {
      path: '/admin',
      name: 'admin',
      component: Admin,
    },
    {
      path: '/mostrarHab',
      name: 'mostrarHab',
      component: MostrarHab,
    },
    {
      path: '/loginMisReservas',
      name: 'loginMisReservas',
      component: LoginMisReservas,
    },
    {
      path: '/registroHotel',
      name: 'registroHotel',
      component: RegistroHotel,
    },
    {
      path: '/loginHotel',
      name: 'loginHotel',
      component: LoginHotel,
    },
    {
      path: '/misReservas',
      name: 'misReservas',
      component: MisReservas,
    }
  ],
})

export default router
