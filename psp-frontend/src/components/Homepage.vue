<template>
  <div>
    <Navbar></Navbar>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card
          class="elevation-12"
          style="padding: 40px; margin-top: 40px; margin-bottom: 20px"
        >
          <v-card-title>
            <h2 class="mb-10">Choosen payment methods</h2>
            <v-spacer></v-spacer>
          </v-card-title>
          <v-card-text>
            <v-data-table
              :headers="headers"
              :items="methods"
              hide-default-header
              hide-default-footer
              class="elevation-1"
            ></v-data-table>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn
              color="blue darken-2"
              class="my-2"
              style="width: 95%; color: white"
              v-on:click="change"
              >Change payment methods</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
import Navbar from "./Navbar.vue";
import { mapState } from "vuex";

export default {
  name: "Homepage",
  components: { Navbar },
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
    change: function () {
      this.$router.push("/methods");
    },
  },
  computed: {
    ...mapState({
      setSupportedPaymentMethods: (state) => state.setSupportedPaymentMethods,
    }),
  },
  created() {
    if (!this.setSupportedPaymentMethods) {
      this.$router.push("/methods");
    } else {
      this.$store
        .dispatch("getSupportedMethods")
        .then((resp) => {
          this.methods = resp.data.filter((el) => {
            return el.checked;
          });
        })
        .catch((err) => {
          console.log(err.response);
        });
    }
  },
};
</script>