<template>
  <div>
    <v-row justify="center">
      <v-dialog v-model="show" persistent max-width="400px">
        <v-card class="px-4 py-4">
          <v-card-title>
            <h3 style="color: #4caf50; margin-bottom: 30px; margin-top: 10px">
              Enter amount
            </h3>
          </v-card-title>
          <v-card-text>
            <v-form v-model="isValid" ref="formAddEquipmentToCart">
              <v-row>
                <v-btn
                  color="green"
                  style="margin-right: auto"
                  icon
                  @click="quantity--"
                >
                  <v-icon>mdi-minus</v-icon>
                </v-btn>
                <v-text-field
                  dense
                  color="green"
                  name="name"
                  type="number"
                  class="mx-4"
                  :rules="[(v) => v > 0 || 'Amount must be positive number']"
                  v-model="quantity"
                ></v-text-field>
                <v-btn
                  color="green"
                  style="margin-right: auto"
                  icon
                  @click="quantity++"
                >
                  <v-icon>mdi-plus</v-icon>
                </v-btn>
              </v-row>
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
  name: "AddEquipmentToCart",
  data: () => ({
    quantity: 0,
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
        description: this.item.description,
        price: this.item.price,
        equipmentType: this.item.equipmentType,
      };
      this.$store
        .dispatch("addEquipmentToCart", {
          id: null,
          quantity: this.quantity,
          equipment: toAdd,
        })
        .then(() => {
          this.$refs.formAddEquipmentToCart.reset();
          this.quantity = 0;
          this.show = false;
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    close: function () {
      this.$refs.formAddEquipmentToCart.reset();
      this.quantity = 0;
      this.show = false;
    },
  },
  created() {},
};
</script>