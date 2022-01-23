import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login.vue";
import Equipment from "../components/Equipment.vue";
import Courses from "../components/Courses.vue";
import Plans from "../components/Plans.vue";
import Conferences from "../components/Conferences.vue";
import Wages from "../components/Wages.vue";
import Success from "../components/Success.vue";
import Cancel from "../components/Cancel.vue";
import Fail from "../components/Fail.vue";
import MySubscriptions from "../components/MySubscriptions.vue";
import History from "../components/History.vue";
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
    path: "/history",
    name: "History",
    component: History
  },
  {
    path: "/courses",
    name: "Courses",
    component: Courses
  },
  {
    path: "/plans",
    name: "Plans",
    component: Plans
  },
  {
    path: "/conferences",
    name: "Conferences",
    component: Conferences
  },
  {
    path: "/wages",
    name: "Wages",
    component: Wages
  },
  {
    path: "/success/:transactionId",
    name: "Success",
    component: Success
  },
  {
    path: "/fail/:transactionId",
    name: "Fail",
    component: Fail
  },
  {
    path: "/cancel/:transactionId",
    name: "Cancel",
    component: Cancel
  },
  {
    path: "/my-subscriptions",
    name: "MySubscriptions",
    component: MySubscriptions
  },
];

const router = new VueRouter({
  mode: 'history',
  routes
});

router.beforeEach((to, from, next) => {
  if (to.path === '/') next({ name: 'Login' })
  if ((to.name !== 'Login') && !store.getters.authenticated) next({ name: 'Login' })
  else next()
  if ((to.name === 'Login') && store.getters.authenticated) next({ name: from.name })
  else next()

  if ((to.name === 'Equipment') && store.state.user.role === 'ROLE_SERVICE_BUYER') next({ name: 'Courses' })
  else next()

  if ((to.name === 'Courses' || to.name === 'Conferences') && store.state.user.role === 'ROLE_EQUIPMENT_BUYER') next({ name: 'Equipment' })
  else next()
})

export default router;
