<template>
  <div>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card
          class="elevation-12"
          style="padding: 40px; margin-top: 40px; margin-bottom: 20px"
        >
          <v-card-title>
            <h2 class="mb-10">Choose payment option</h2>
            <v-spacer></v-spacer>
          </v-card-title>
          <v-card-text>
            <v-data-table
              :headers="headers"
              :items="methods"
              hide-default-header
              hide-default-footer
              class="elevation-1"
              @click:row="choose"
            ></v-data-table>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>
<script>
export default {
  name: "ChoosePayment",
  data: () => ({
    methods: [
      { name: "Paying with credit card", url: "/payment/" },
      { name: "Paying with QR", url: "/payment-qr/" },
    ],
    headers: [
      {
        text: "Name",
        align: "start",
        value: "name",
      },
    ],
  }),
  methods: {
    choose: function (method) {
      const paymentId = this.$route.params.paymentId;

      const path = method.url + paymentId;

      this.$router.push(path);
    },
  },
};
</script>