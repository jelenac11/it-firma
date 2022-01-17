<template>
  <div class="container">
    <img class="image-qr" :src="'https://localhost:9002' + this.QRImageUrl" />
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
    getPng: function () {
      const paymentId = this.$route.params.paymentId;
      let ii = null;
    this.$store
      .dispatch("payQr", paymentId)
      .then((res) => {
        console.log(res.data);
        ii = res.data;
      })
      .catch((err) => {
        console.log(err.response);
      });
      return ii;
    }
  },
  created() {
    const paymentId = this.$route.params.paymentId;

    this.$store
      .dispatch("payQr", paymentId)
      .then((res) => {
        console.log(res.data);
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