<template>
  <div class="my-subscriptions-container">
    <Navbar></Navbar>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md11>
        <v-card
          class="elevation-12"
          style="padding: 20px; margin-top: 40px; margin-bottom: 40px"
        >
          <v-card-text>
            <v-container fluid>
              <v-data-iterator
                :items="history"
                :search="search"
                :sort-by="sortBy.toLowerCase()"
                :sort-desc="sortDesc"
                :items-per-page.sync="itemsPerPage"
                :page.sync="page"
                hide-default-footer
              >
                <template v-slot:header>
                  <v-toolbar dark color="green" class="mb-6">
                    <v-text-field
                      v-model="search"
                      clearable
                      flat
                      solo-inverted
                      hide-details
                      prepend-inner-icon="mdi-magnify"
                      label="Search"
                    ></v-text-field>
                    <template v-if="$vuetify.breakpoint.mdAndUp">
                      <v-spacer></v-spacer>
                      <v-btn-toggle v-model="sortDesc" mandatory>
                        <v-btn large depressed color="green" :value="false">
                          <v-icon>mdi-arrow-up</v-icon>
                        </v-btn>
                        <v-btn large depressed color="green" :value="true">
                          <v-icon>mdi-arrow-down</v-icon>
                        </v-btn>
                      </v-btn-toggle>
                    </template>
                  </v-toolbar>
                </template>

                <template v-slot:default="props">
                  <v-row>
                    <v-col
                      v-for="item in props.items"
                      :key="item.id"
                      cols="12"
                      sm="6"
                      md="4"
                      lg="3"
                    >
                      <v-card class="pb-2">
                        <v-card-title
                          class="subheading font-weight-bold green--text"
                        >
                          {{ item.name }}
                        </v-card-title>
                        <v-divider></v-divider>
                        <v-list dense>
                          <v-list-item-content>
                            <span class="ml-4"
                              >Quantity: <b>{{ item.quantity }}</b></span
                            >
                          </v-list-item-content>
                          <v-list-item-content>
                            <span class="ml-4"
                              >Total Price: <b>{{ item.price }}$</b></span
                            >
                          </v-list-item-content>
                        </v-list>
                      </v-card>
                    </v-col>
                  </v-row>
                </template>

                <template v-slot:footer>
                  <v-row class="mt-3" align="center" justify="center">
                    <v-spacer></v-spacer>
                    <span class="mr-4 grey--text">
                      Page {{ page }} of {{ numberOfPages }}
                    </span>
                    <v-btn dark color="green" class="mr-1" @click="formerPage">
                      <v-icon>mdi-chevron-left</v-icon>
                    </v-btn>
                    <v-btn dark color="green" class="ml-1" @click="nextPage">
                      <v-icon>mdi-chevron-right</v-icon>
                    </v-btn>
                  </v-row>
                </template>
              </v-data-iterator>
            </v-container>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>
<script>
import Navbar from "./Navbar.vue";
import { mapState } from "vuex";

export default {
  name: "History",
  components: {
    Navbar,
  },
  data: () => ({
    search: "",
    sortDesc: false,
    sortBy: "name",
    page: 1,
    itemsPerPage: 4,
    historyItems: [],
  }),
  computed: {
    numberOfPages() {
      return Math.ceil(this.history.length / this.itemsPerPage);
    },
    ...mapState({
      currentUser: (state) => state.user,
    }),
    history() {
      return this.historyItems;
    },
  },
  methods: {
    nextPage() {
      if (this.page + 1 <= this.numberOfPages) this.page += 1;
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1;
    },
    getMyHistory: function () {
      this.$store
        .dispatch("getMyHistory")
        .then((resp) => {
          this.historyItems = resp.data;
        })
        .catch((err) => {
          console.log(err.response.data);
        });
    },
  },
  created() {
    this.getMyHistory();
  },
};
</script>

<style scoped>
.my-subscriptions-container {
  background-image: url(../assets/6.png);
  background-repeat: repeat-y;
  height: 100%;
}
</style>