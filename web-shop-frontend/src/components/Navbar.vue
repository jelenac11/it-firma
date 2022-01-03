<template>
  <div>
    <v-app-bar color="green darken-2" dark>
      <v-toolbar-title class="mx-4">WebShop</v-toolbar-title>
      <v-btn
        class="mx-4"
        outlined
        @click="goToEquipment()"
        v-if="
          currentUser.role === 'ROLE_EQUIPMENT_BUYER' ||
          currentUser.role === 'ROLE_MERCHANT'
        "
      >
        Equipment
      </v-btn>
      <v-btn
        class="mr-4"
        outlined
        @click="goToCourses()"
        v-if="
          currentUser.role === 'ROLE_SERVICE_BUYER' ||
          currentUser.role === 'ROLE_MERCHANT'
        "
      >
        Courses
      </v-btn>
      <v-btn
        class="mr-4"
        outlined
        @click="goToPlans()"
        v-if="
          currentUser.role === 'ROLE_SERVICE_BUYER' ||
          currentUser.role === 'ROLE_MERCHANT'
        "
      >
        Plans
      </v-btn>
      <v-btn
        class="mr-4"
        outlined
        @click="goToConferences()"
        v-if="
          currentUser.role === 'ROLE_SERVICE_BUYER' ||
          currentUser.role === 'ROLE_MERCHANT'
        "
      >
        Conferences
      </v-btn>
      <v-spacer></v-spacer>
      <h2 class="mr-4" style="font-weight: 400">
        {{ currentUser.email }}
      </h2>
      <v-btn
        style="margin-right: 1px"
        icon
        @click="openCart()"
        v-if="currentUser.role !== 'ROLE_MERCHANT'"
      >
        <v-icon>mdi-cart</v-icon>
      </v-btn>
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
            <v-subheader class="log-out mr-2" @click="openMySubscriptions()">
              <b>My Subscriptions</b>
            </v-subheader>
            <v-subheader class="log-out mr-2" @click="logOut()"
              ><v-icon class="mr-2">mdi-exit-to-app</v-icon
              ><b>Log out</b></v-subheader
            >
          </v-list>
        </v-card>
      </v-menu>
    </v-app-bar>
    <Cart v-model="showCart"></Cart>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Cart from "./Cart.vue";

export default {
  name: "Navbar",
  components: {
    Cart,
  },
  data: () => ({
    showCart: false,
  }),
  methods: {
    logOut: function () {
      this.$store.dispatch("logout").then(() => {
        this.$router.push("/login");
      });
    },
    goToEquipment: function () {
      let path = "/equipment";
      if (this.$route.path !== path) this.$router.push(path);
    },
    goToCourses: function () {
      let path = "/courses";
      if (this.$route.path !== path) this.$router.push(path);
    },
    goToPlans: function () {
      let path = "/plans";
      if (this.$route.path !== path) this.$router.push(path);
    },
    goToConferences: function () {
      let path = "/conferences";
      if (this.$route.path !== path) this.$router.push(path);
    },
    openCart: function () {
      this.showCart = true;
    },
    openMySubscriptions: function () {
      let path = "/my-subscriptions";
      if (this.$route.path !== path) this.$router.push(path);
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