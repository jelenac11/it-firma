import Vue from "vue";
import VueRouter from "vue-router";
import Methods from "../components/Methods.vue";
import Homepage from "../components/Homepage.vue";
import Checkout from "../components/Checkout.vue";
import SignIn from "../components/SignIn.vue";
import SignUp from "../components/SignUp.vue";
import AccountActivation from "../components/AccountActivation.vue";
import store from "../store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Homepage",
    component: Homepage
  },
  {
    path: "/methods",
    name: "Methods",
    component: Methods
  },
  {
    path: "/checkout/:orderDataId",
    name: "Checkout",
    component: Checkout
  },
  {
    path: "/account-activation/:token",
    name: "AccountActivation",
    component: AccountActivation
  },
  {
    path: "/sign-in",
    name: "SignIn",
    component: SignIn
  },
  {
    path: "/sign-up",
    name: "SignUp",
    component: SignUp
  },
];

const router = new VueRouter({
  routes
});

router.beforeEach((to, from, next) => {
  if ((to.name !== 'SignIn' && to.name !== 'SignUp' && to.name !== 'AccountActivation' && to.name !== 'Checkout') && !store.getters.authenticated) next({ name: 'SignIn' })
  else next()

  if ((to.name === 'SignIn' || to.name === 'SignUp' || to.name === 'AccountActivation') && store.getters.authenticated) next({ name: from.name })
  else next()
})

export default router;
