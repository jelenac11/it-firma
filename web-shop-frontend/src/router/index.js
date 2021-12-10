import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Equipment from "../components/Equipment.vue";
import Courses from "../components/Courses.vue";
import Conferences from "../components/Conferences.vue";
import store from "../store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/equipment",
    name: "Equipment",
    component: Equipment
  },
  {
    path: "/courses",
    name: "Courses",
    component: Courses
  },
  {
    path: "/conferences",
    name: "Conferences",
    component: Conferences
  },
];

const router = new VueRouter({
  routes
});

router.beforeEach((to, from, next) => {
  if ((to.name !== 'Login') && !store.getters.authenticated) next({ name: 'Login' })
  else next()
  if ((to.name === 'Login') && store.getters.authenticated) next({ name: from.name })
  else next()

  if ((to.name === 'Equipment') && store.state.user.role === 'ROLE_GENERAL_SERVICE_WORKER') next({ name: 'Courses' })
  else next()

  if ((to.name === 'Courses' || to.name === 'Conferences') && store.state.user.role === 'ROLE_CHIEF') next({ name: 'Equipment' })
  else next()
})

export default router;
