<template>
  <div>
    <v-row justify="center">
      <v-dialog v-model="show" max-width="600px">
        <v-card class="px-4 py-4">
          <v-card-title>
            <h2 style="color: #4caf50; margin-bottom: 30px; margin-top: 10px">
              My shopping cart
            </h2>
          </v-card-title>
          <v-card-text>
            <v-simple-table fixed-header class="mb-9">
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left">Name</th>
                    <th
                      class="text-left"
                      v-if="currentUser.role === 'ROLE_CHIEF'"
                    >
                      Amount
                    </th>
                    <th
                      class="text-left"
                      v-if="currentUser.role === 'ROLE_GENERAL_SERVICE_WORKER'"
                    >
                      Participant
                    </th>
                    <th class="text-left">Price</th>
                    <th class="text-left"></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item in cartItems" :key="item.name">
                    <td v-if="currentUser.role === 'ROLE_CHIEF'">
                      {{ item.equipment.name }}
                    </td>
                    <td
                      v-if="currentUser.role === 'ROLE_GENERAL_SERVICE_WORKER'"
                    >
                      {{ item.service.name }}
                    </td>
                    <td v-if="currentUser.role === 'ROLE_CHIEF'">
                      {{ item.quantity }}
                    </td>
                    <td
                      v-if="currentUser.role === 'ROLE_GENERAL_SERVICE_WORKER'"
                    >
                      {{ item.person }}
                    </td>
                    <td v-if="currentUser.role === 'ROLE_CHIEF'">
                      ${{ item.equipment.price }}
                    </td>
                    <td
                      v-if="currentUser.role === 'ROLE_GENERAL_SERVICE_WORKER'"
                    >
                      ${{ item.service.price }}
                    </td>
                    <td>
                      <v-btn
                        color="red"
                        style="margin-right: auto"
                        icon
                        @click="remove(item)"
                      >
                        <v-icon>mdi-delete</v-icon>
                      </v-btn>
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            <h2 class="ml-4">Total: ${{ calculateTotal() }}</h2>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="green darken-1"
              text
              @click="checkout()"
              :disabled="this.cartItems.length === 0"
            >
              Go to checkout<v-icon class="ml-2 mb-1">mdi-arrow-right</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Cart",
  data: () => ({
    cartItems: [],
    order: {
      totalPrice: 0,
      items: [],
    },
  }),
  props: {
    value: Boolean,
  },
  computed: {
    show: {
      get() {
        this.getCartItems();
        return this.value;
      },
      set(value) {
        this.$emit("input", value);
      },
    },
    ...mapState({
      currentUser: (state) => state.user,
    }),
  },
  methods: {
    checkout: function () {
      this.order.items = [];
      this.cartItems.forEach((cartItem) => {
        this.order.items.push({
          itemId: cartItem.id,
          itemType:
            this.currentUser.role === "ROLE_CHIEF" ? "EQUIPMENT" : "SERVICE",
        });
      });
      this.order.totalPrice = this.calculateTotal();
      this.$store.dispatch("addOrder", this.order).then((resp) => {
        console.log(resp.data.url);
        window.location.href = resp.data.url;
      });
    },
    remove: function (item) {
      if (this.currentUser.role === "ROLE_CHIEF") {
        this.$store.dispatch("removeEquipmentCartItem", item.id).then(() => {
          this.cartItems = this.cartItems.filter((e) => e.id !== item.id);
        });
      } else {
        this.$store.dispatch("removeServiceCartItem", item.id).then(() => {
          this.cartItems = this.cartItems.filter((e) => e.id !== item.id);
        });
      }
    },
    calculateTotal: function () {
      let sum = 0;
      this.cartItems.forEach((it) => {
        if (it.equipment !== undefined) {
          sum += it.equipment.price * it.quantity;
        } else {
          sum += it.service.price;
        }
      });
      return sum;
    },
    getCartItems: function () {
      if (this.currentUser.role === "ROLE_CHIEF") {
        this.$store.dispatch("getChiefShoppingCart").then((resp) => {
          this.cartItems = resp.data.items;
        });
      } else if (this.currentUser.role === "ROLE_GENERAL_SERVICE_WORKER") {
        this.$store.dispatch("getGeneralServiceShoppingCart").then((resp) => {
          this.cartItems = resp.data.items;
        });
      }
    },
  },
  mounted: function () {
    this.$store.dispatch("getCurrentUser").then(() => {
      this.getCartItems();
    });
  },
};
</script>