<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md6>
        <v-card class="elevation-12" style="padding: 40px">
          <v-card-text class="text-center">
            <div class="card mx-5 my-5">
              <img
                class="mx-auto my-5"
                src="../assets/fail.png"
                alt="Card image cap"
                height="300"
                width="300"
              />
              <p class="display-1 font-weight-bolder mx-auto">
                <b>Payment failed!</b>
              </p>
            </div>
            <router-link
              to="/equipment"
              class="ml-5 mt-5"
              style="color: #1976d2"
              v-if="this.currentUser.role === 'ROLE_EQUIPMENT_BUYER'"
              >Go to homepage.</router-link
            >
            <router-link
              to="/courses"
              class="ml-5 mt-5"
              style="color: #1976d2"
              v-if="this.currentUser.role === 'ROLE_SERVICE_BUYER'"
              >Go to homepage.</router-link
            >
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Fail",
  data: () => ({}),
  props: {},
  computed: {
    ...mapState({
      currentUser: (state) => state.user,
    }),
  },
  methods: {},
  mounted: function () {
    this.$store.dispatch("getCurrentUser");
    const transactionId = this.$route.params.transactionId;

    this.$store
      .dispatch("updateTransaction", {
        transactionId: transactionId,
        status: 2,
      })
      .then(() => {});
  },
};
</script>