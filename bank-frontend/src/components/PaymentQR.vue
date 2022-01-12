<template>
  <div class="container">
    <img class="image-qr" :src="'https://localhost:9002' + QRImageUrl" />
    <v-btn class="pay-button" color="green darken-1" @click="pay()"
      >Continue with paying</v-btn
    >
  </div>
</template>

<script>
export default {
  name: "PaymentQR",
  data: () => ({
    QRImageUrl: "",
  }),
  methods: {
    pay: function () {
      const paymentId = this.$route.params.paymentId;

      const path = "/payment/" + paymentId;

      this.$router.push(path);
    },
  },
  mounted() {
    const paymentId = this.$route.params.paymentId;

    this.$store
      .dispatch("payQr", paymentId)
      .then((res) => {
        this.QRImageUrl = res.data;
      })
      .catch((err) => {
        console.log(err.response);
      });
  },
};
</script>

<style scoped>
.pay-button {
  width: 220px;
  margin-top: 10px;
}

.image-qr {
  width: 350px;
  height: 350px;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>