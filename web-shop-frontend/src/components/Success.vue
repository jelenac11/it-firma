<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md6>
        <v-card class="elevation-12" style="padding: 40px">
          <v-card-text class="text-center">
            <div class="card mx-5 my-5">
              <img
                class="mx-auto my-5"
                src="../assets/check.png"
                alt="Card image cap"
                height="300"
                width="300"
              />
              <p class="display-1 font-weight-bolder mx-auto">
                <b>Successfully paid!</b>
              </p>
            </div>
            <h1 class="my-12">You purchased:</h1>
            <v-simple-table fixed-header class="mb-9">
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left">Name</th>
                    <th
                      class="text-left"
                      v-if="currentUser.role === 'ROLE_EQUIPMENT_BUYER'"
                    >
                      Amount
                    </th>
                    <th
                      class="text-left"
                      v-if="currentUser.role === 'ROLE_SERVICE_BUYER'"
                    >
                      Participant
                    </th>
                    <th class="text-left">Price</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item in cartItems" :key="item.name">
                    <td v-if="currentUser.role === 'ROLE_EQUIPMENT_BUYER'" class="text-left">
                      {{ item.equipment.name }}
                    </td>
                    <td v-if="currentUser.role === 'ROLE_SERVICE_BUYER'">
                      <p>{{ item.service.name }}<span v-if="!item.service.online"> <b>(Transport and accommodation included)</b></span></p> 
                    </td>
                    <td v-if="currentUser.role === 'ROLE_EQUIPMENT_BUYER'" class="text-left">
                      {{ item.quantity }}
                    </td>
                    <td v-if="currentUser.role === 'ROLE_SERVICE_BUYER'" class="text-left">
                      {{ item.person }}
                    </td>
                    <td v-if="currentUser.role === 'ROLE_EQUIPMENT_BUYER'" class="text-left">
                      ${{ item.equipment.price }}
                    </td>
                    <td v-if="currentUser.role === 'ROLE_SERVICE_BUYER'">
                      ${{ item.service.price + item.additionalCosts }}
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            <h2 class="ml-4">Total paid: ${{ calculateTotal() }}</h2>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Success",
  data: () => ({
    cartItems: []
  }),
  props: {},
  computed: {
    ...mapState({
      currentUser: (state) => state.user,
    }),
  },
  methods: {
    createSubscription: function (subscriptionId, transactionId) {
      this.$store
        .dispatch("createSubscription", {
          subscriptionId,
          transactionId,
        })
        .then(() => {});
    },
    calculateTotal: function () {
      let sum = 0;
      this.cartItems.forEach((it) => {
        if (it.equipment !== undefined) {
          sum += it.equipment.price * it.quantity;
        } else {
          sum += it.service.price + it.additionalCosts;
        }
      });
      return sum;
    },
  },
  mounted: function () {
    const transactionId = this.$route.params.transactionId;

    this.$store.dispatch("getCurrentUser").then(() => {
      this.$store
      .dispatch("updateTransaction", {
        transactionId: transactionId,
        status: 1,
      })
      .then((resp) => {
        console.log(resp.data);
        if (this.currentUser.role === 'ROLE_EQUIPMENT_BUYER') {
          this.cartItems = resp.data.equipmentCart.items;
        } else {
          this.cartItems = resp.data.serviceCart.items;
        }
      });
    });

    const subscriptionId = this.$route.query.subscription_id;

    if (!subscriptionId) {
      return;
    }

    this.createSubscription(subscriptionId, transactionId);
  },
};
</script>