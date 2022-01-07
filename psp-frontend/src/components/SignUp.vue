<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card class="elevation-12" style="padding: 20px">
          <v-card-text>
            <h1 style="color: #1976d2; margin-bottom: 30px; margin-top: 10px">
              Sign up
            </h1>
            <v-form class="mt-4" v-model="isValid" ref="formRegister">
              <v-text-field
                color="red"
                prepend-icon="person"
                name="email"
                label="Email"
                type="text"
                v-model="user.email"
                :rules="emailRules"
                required
              ></v-text-field>
              <v-text-field
                color="blue darken-2"
                prepend-icon="mdi-cart"
                name="shopName"
                label="Shop name"
                type="text"
                v-model="user.shopName"
                :rules="[(v) => !!v || 'Shop name is required']"
                required
              ></v-text-field>
              <v-text-field
                color="blue darken-2"
                id="password"
                prepend-icon="lock"
                name="password"
                label="Password"
                type="password"
                v-model="user.password"
                :rules="passwordRules"
                required
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn
              color="blue darken-2"
              class="my-2"
              style="width: 95%; color: white"
              :disabled="!isValid"
              v-on:click="register"
              >Sign up</v-btn
            >
          </v-card-actions>
          <router-link to="/sign-in" class="ml-5" style="color: #1976d2"
            >Already have an account? Sign in here.</router-link
          >
          <br />
        </v-card>
      </v-flex>
    </v-layout>
    <v-snackbar v-model="conflict" top color="red darken-4">
      {{this.message}}
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  name: "SignUp",
  props: {
    source: String,
  },
  data: () => ({
    user: {
      email: null,
      shopName: null,
      password: null,
    },
    message: '',
    isValid: true,
    conflict: false,
    emailRules: [
      (v) => !!v || "Email is required",
      (v) => /.+@.+/.test(v) || "E-mail must be valid",
    ],
    passwordRules: [
      (v) => !!v || "Password is required",
      (v) =>
        (v &&
          /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_#?!@$%^&*-.,:;]).{8,20}$/.test(
            v
          )) ||
        "Password must be between 8 and 20 characters long and must contain a number, a special character, a lowercase and an uppercase letter.",
    ],
  }),
  methods: {
    register: function () {
      let newUser = this.user;
      this.$store
        .dispatch("register", newUser)
        .then(() => this.$router.push("/sign-in"))
        .catch((err) => {
          console.log(err.response);
          this.conflict = true;
          this.message = err.response.data;
        });
    },
  },
};
</script>
