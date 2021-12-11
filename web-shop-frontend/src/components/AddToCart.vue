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
              @click="add()"
              >Add<v-icon class="ml-2 mb-1">mdi-plus</v-icon></v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "AddToCart",
  data: () => ({
    name: "",
    isValid: true,
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
        })
        .then(() => {
          this.$refs.formAddToCart.reset();
          this.name = "";
          this.show = false;
        })
        .catch((err) => {
          console.log(err.response);
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