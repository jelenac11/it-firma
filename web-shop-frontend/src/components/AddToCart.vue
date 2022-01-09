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
              <v-select
                v-if="!item.online"
                :items="transports"
                v-model="selectTr"
                label="Transport"
                :item-text="item => item.type +' ($' + item.price + ')'"
                required
                return-object
                single-line
              ></v-select>
              <v-select
                v-if="!item.online"
                :items="accommodations"
                v-model="selectAc"
                label="Accommodation"
                :item-text="item => item.type +' ($' + item.price + ')'"
                required
                return-object
                single-line
              ></v-select>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="green darken-1" text @click="close()"> Close </v-btn>
            <v-spacer></v-spacer>
            <v-btn
              color="green darken-1"
              text
              :disabled="!isValid"
              @click="add()"
              >Add<v-icon class="ml-2 mb-1">mdi-plus</v-icon></v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
    <v-snackbar v-model="wrong" top color="red darken-3">
      You can't add to cart items from multiple merchants.
    </v-snackbar>
  </div>
</template>

<script>
export default {
  name: "AddToCart",
  data: () => ({
    name: "",
    isValid: true,
    wrong: false,
    transports: [],
    accommodations: [],
    showAddToCart: false,
    selectTr: {
      type: '',
      price: 0
    },
    selectAc: {
      type: '',
      price: 0
    }
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
  watch: {
    value(visible) {
      if (visible) {
        if (this.item.teacher === undefined && !this.item.online) {
          this.$store.dispatch("getTransports", this.item.id).then((resp) => {
            this.transports = resp.data;
            this.selectTr = this.transports[0];
          });
          this.$store.dispatch("getAccommodations", this.item.id).then((resp) => {
            this.accommodations = resp.data;
            this.selectAc = this.accommodations[0];
          });
        }
      }
    }
  },
  methods: {
    add: function () {
      const toAdd = {
        id: this.item.id,
        name: this.item.name,
        price: this.item.price,
      };
      this.$store
        .dispatch("addServiceToCart", {
          id: null,
          person: this.name,
          service: toAdd,
          additionalCosts: this.selectTr.price + this.selectAc.price
        })
        .then(() => {
          this.$refs.formAddToCart.reset();
          this.name = "";
          this.show = false;
        })
        .catch(() => {
          this.wrong = true;
        });
    },
    close: function () {
      this.$refs.formAddToCart.reset();
      this.name = "";
      this.show = false;
    },
  },
};
</script>