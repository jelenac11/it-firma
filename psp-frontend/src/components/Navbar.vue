<template>
  <div>
    <v-app-bar color="blue darken-2" dark>
      <v-toolbar-title class="mx-4">PSP</v-toolbar-title>
      <v-spacer></v-spacer>
      <h2 class="mr-4" style="font-weight: 400">
        {{ currentUser.email }}
      </h2>
      <v-menu
        open-on-click
        transition="slide-y-transition"
        bottom
        left
        offset-y
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            style="margin-right: 1px"
            class="account"
            icon
            v-bind="attrs"
            v-on="on"
          >
            <v-icon>mdi-account</v-icon>
          </v-btn>
        </template>
        <v-card class="mx-auto" max-width="300" tile>
          <v-list dense>
            <v-subheader class="log-out mr-2" @click="logOut()"
              ><v-icon class="mr-2">mdi-exit-to-app</v-icon
              ><b>Log out</b></v-subheader
            >
          </v-list>
        </v-card>
      </v-menu>
    </v-app-bar>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Navbar",
  components: {},
  data: () => ({}),
  methods: {
    logOut: function () {
      this.$store.dispatch("logout").then(() => {
        this.$router.push("/sign-in");
      });
    },
  },
  computed: {
    ...mapState({
      currentUser: (state) => state.user,
    }),
  },
  created: function () {
    this.$store.dispatch("getCurrentUser");
  },
};
</script>

<style scoped>
.log-out {
  cursor: pointer;
}
</style>