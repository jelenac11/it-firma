import Vue from "vue";
import VueRouter from "vue-router";
import PaymentData from "../components/PaymentData.vue";
import PaymentQR from "../components/PaymentQR.vue";
import ChoosePayment from "../components/ChoosePayment.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/payment/:paymentId",
    name: "PaymentData",
    component: PaymentData
  },

  {
    path: "/payment-qr/:paymentId",
    name: "PaymentQR",
    component: PaymentQR
  },

  {
    path: "/choose-payment/:paymentId",
    name: "ChoosePayment",
    component: ChoosePayment
  }
];

const router = new VueRouter({
  routes
});

export default router;
