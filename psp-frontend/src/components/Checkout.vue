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
  name: "Checkout",
  components: {},
  data: () => ({
    methods: [],
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
      const paymentData = {
        paymentMethodId: method.id,
        orderDataId: this.$route.params.orderDataId,
      };

      this.$store.dispatch("getPaymentUrl", paymentData).then((res) => {
        window.location.href = res.data;
      });
    },
  },
  computed: {},
  created() {
    let orderDataId = parseInt(this.$route.params.orderDataId);
    this.$store
      .dispatch("getSupportedMethodsForMerchant", orderDataId)
      .then((resp) => {
        this.methods = resp.data;
      })
      .catch((err) => {
        console.log(err.response);
      });
  },
};
</script>