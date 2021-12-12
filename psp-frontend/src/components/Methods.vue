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
            <h2 class="mb-10">Choose payment methods to support</h2>
            <v-spacer></v-spacer>
          </v-card-title>
          <v-card-text v-if="!this.change">
            <div v-for="meth in methodsItems" :key="meth.id">
              <v-checkbox
                v-model="selected"
                :label="meth.name"
                :value="meth.id"
              ></v-checkbox>
              <v-form v-model="meth.valid" v-if="selected.includes(meth.id)">
                <v-text-field
                  v-for="field in meth.attributes"
                  :key="field.id"
                  v-model="field.value"
                  prepend-icon="mdi-pencil"
                  :label="field.name"
                  :type="field.type"
                  :rules="[(v) => !!v || 'This field is required']"
                  required
                ></v-text-field>
              </v-form>
            </div>
          </v-card-text>
          <v-card-text v-else>
            <div v-for="meth in methodsItems" :key="meth.id">
              <v-checkbox
                v-model="selected"
                :label="meth.name"
                :value="meth.id"
              ></v-checkbox>
              <v-form v-model="meth.valid" v-if="selected.includes(meth.id)">
                <v-text-field
                  v-for="field in meth.values"
                  :key="field.id"
                  v-model="field.value"
                  prepend-icon="mdi-pencil"
                  :label="field.name"
                  :type="field.type"
                  :rules="[(v) => !!v || 'This field is required']"
                  required
                ></v-text-field>
              </v-form>
            </div>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn
              color="blue darken-2"
              class="my-2"
              style="width: 95%; color: white"
              :disabled="notValid()"
              v-on:click="finish"
              >Finish</v-btn
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
  name: "Methods",
  components: { Navbar },
  data: () => ({
    chosen: {
      chosenMethods: [],
    },
    methodsItems: [],
    selected: [],
    change: false,
  }),
  methods: {
    notValid: function () {
      if (this.selected.length === 0) return true;
      let valid = true;
      this.methodsItems.forEach((meth) => {
        if (this.selected.includes(meth.id)) {
          if (!meth.valid) {
            valid = false;
          }
        }
      });
      return !valid;
    },
    finish: function () {
      this.chosen = {
        chosenMethods: [],
      };
      this.methodsItems.forEach((method) => {
        if (this.selected.includes(method.id)) {
          let chosenMethod = {
            id: method.id,
            values: [],
          };
          if (this.change) {
            method.values.forEach((val) => {
              chosenMethod.values.push({
                id: val.id,
                value: val.value,
              });
            });
          } else {
            method.attributes.forEach((attribute) => {
              chosenMethod.values.push({
                id: attribute.id,
                value: attribute.value,
              });
            });
          }
          this.chosen.chosenMethods.push(chosenMethod);
        }
      });
      this.$store
        .dispatch("chosenMethods", this.chosen)
        .then(() => this.$router.push("/"))
        .catch((err) => {
          console.log(err.response);
        });
    },
  },
  computed: {
    ...mapState({
      methodsState: (state) => state.allMethods,
      currentUser: (state) => state.user,
    }),
  },
  created() {
    this.$store.dispatch("getCurrentUser").then((resp) => {
      if (resp.data.setSupportedPaymentMethods) {
        this.$store
          .dispatch("getSupportedMethods")
          .then((resp) => {
            this.methodsItems = resp.data;
            this.change = true;
            this.methodsItems.forEach((m) => {
              if (m.checked) {
                this.selected.push(m.id);
              }
            });
          })
          .catch((err) => {
            console.log(err.response);
          });
      } else {
        this.$store.dispatch("getAllMethods").then((resp) => {
          this.methodsItems = resp.data;
          this.change = false;
        });
      }
    });
  },
};
</script>