import Vue from "vue";
import VueRouter from "vue-router";
import PaymentData from "../components/PaymentData.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/payment/:paymentId",
    name: "PaymentData",
    component: PaymentData
  }
];

const router = new VueRouter({
  routes
});

export default router;
