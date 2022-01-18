<template>
  <div>
    <v-row justify="center">
      <v-dialog v-model="show" persistent max-width="400px">
        <v-card class="px-4 py-4">
          <v-card-title>
            <h3 style="color: #4caf50; margin-bottom: 30px; margin-top: 10px">
              Enter receiver's email
            </h3>
          </v-card-title>
          <v-card-text>
            <v-form v-model="isValid" ref="formAddToCart">
              <v-text-field
                color="green"
                prepend-icon="email"
                name="email"
                label="Email"
                type="text"
                :rules="[(v) => !!v || 'Email is required']"
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
              @click="add()"
              >Pay <v-icon class="ml-2 mb-1">mdi-cash</v-icon></v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "AddReceiver",
  data: () => ({
    name: "",
    isValid: true,
    wrong: false,
    transports: [],
    accommodations: [],
    showAddToCart: false,
    selectTr: {
      type: "",
      price: 0,
    },
    selectAc: {
      type: "",
      price: 0,
    },
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
    add: function () {
      const payWage = {
        price: this.item.price,
        wageId: this.item.wageId,
        receiver: this.name,
      };

      this.$store
        .dispatch("payWage", payWage)
        .then((resp) => {
          window.location.href = resp.data.url;
        })
        .catch((err) => {
          console.log(err.response.data);
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