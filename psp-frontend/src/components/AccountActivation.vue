<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md6>
        <v-card class="elevation-12" style="padding: 40px">
          <v-card-text class="text-center">
            <div class="card mx-5 my-5">
              <img
                class="mx-auto my-5"
                src="../assets/check.png"
                alt="Card image cap"
                height="300"
                width="300"
              />
              <p class="display-1 font-weight-bolder mx-auto">
                Your account is activated successfully!
              </p>
              <div class="card-body mx-auto">
                <h1 class="font-weight-bold mx-auto">
                  <a class="text-primary" @click="goToSignIn()"
                    >Sign in here.</a
                  >
                </h1>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "AccountActivation",
  data: () => ({}),
  methods: {
    goToSignIn: function () {
      this.$router.push("/sign-in");
    },
  },
  created() {
    this.$store.dispatch("verify", this.$route.params.token).then((resp) => {
      console.log(resp.data);
    });
  },
};
</script>
