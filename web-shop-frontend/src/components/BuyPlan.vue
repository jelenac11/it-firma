<template>
  <div>
    <v-row justify="center">
      <v-dialog v-model="show" persistent max-width="400px">
        <v-card class="px-4 py-4">
          <v-card-title>
            <h3 style="color: #4caf50; margin-bottom: 30px; margin-top: 10px">
              Enter participant name
            </h3>
          </v-card-title>
          <v-card-text>
            <v-form v-model="isValid" ref="formAddToCart">
              <v-text-field
                color="green"
                prepend-icon="person"
                name="name"
                label="Name"
                type="text"
                :rules="[(v) => !!v || 'Name is required']"
                v-model="name"
                required
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="green darken-1" text @click="close()"> Close </v-btn>
            <v-spacer></v-spacer>
            <v-btn
              color="green darken-1"
              text
              :disabled="!isValid"
              @click="subscribe()"
              >Buy</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
    <SnackBarError v-model="showError" :text="textError"></SnackBarError>
  </div>
</template>

<script>
import SnackBarError from "./SnackBarError.vue";

export default {
  name: "BuyPlan",
  components: {
    SnackBarError,
  },
  data: () => ({
    name: "",
    isValid: true,
    wrong: false,
    showError: false,
    textError: "",
  }),
  props: {
    value: Boolean,
    item: {},
  },
  computed: {
    show: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit("input", value);
      },
    },
  },
  methods: {
    subscribe: function () {
      this.$store
        .dispatch("subscribe", this.item.id)
        .then((resp) => {
          this.$refs.formAddToCart.reset();
          this.name = "";
          this.show = false;

          window.location.href = resp.data;
        })
        .catch((err) => {
          this.wrong = true;
          this.showError = true;
          this.textError = err.response.data;
        });
    },

    close: function () {
      this.$refs.formAddToCart.reset();
      this.name = "";
      this.show = false;
    },
  },
  created() {},
};
</script>