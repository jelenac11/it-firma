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
                  <tr v-if="cartItems.length">
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

                  <tr v-if="wageItems.length">
                    <th class="text-left">Name</th>
                    <th class="text-left">Price</th>
                  </tr>
                  <tr v-if="subscription">
                    <th class="text-left">Name</th>
                    <th class="text-left">Start date</th>
                    <th class="text-left">Type of subscription</th>
                    <th class="text-left">Price</th>
                  </tr>
                </thead>
                <tbody v-if="cartItems.length">
                  <tr v-for="item in cartItems" :key="item.name">
                    <td
                      v-if="currentUser.role === 'ROLE_EQUIPMENT_BUYER'"
                      class="text-left"
                    >
                      {{ item.equipment.name }}
                    </td>
                    <td v-if="currentUser.role === 'ROLE_SERVICE_BUYER'">
                      <p>{{ item.service.name }}<span v-if="!item.service.online"> <b>(Transport and accommodation included)</b></span></p> 
                    </td>
                    <td
                      v-if="currentUser.role === 'ROLE_EQUIPMENT_BUYER'"
                      class="text-left"
                    >
                      {{ item.quantity }}
                    </td>
                    <td
                      v-if="currentUser.role === 'ROLE_SERVICE_BUYER'"
                      class="text-left"
                    >
                      {{ item.person }}
                    </td>
                    <td
                      v-if="currentUser.role === 'ROLE_EQUIPMENT_BUYER'"
                      class="text-left"
                    >
                      ${{ item.equipment.price }}
                    </td>
                    <td v-if="currentUser.role === 'ROLE_SERVICE_BUYER'">
                      ${{ item.service.price + item.additionalCosts }}
                    </td>
                  </tr>
                </tbody>
                <tbody v-if="wageItems.length">
                  <tr v-for="item in wageItems" :key="item.name">
                    <td
                      v-if="currentUser.role === 'ROLE_SERVICE_BUYER'"
                      class="text-left"
                    >
                      Wage
                    </td>
                    <td
                      v-if="currentUser.role === 'ROLE_SERVICE_BUYER'"
                      class="text-left"
                    >
                      {{ item.price }}
                    </td>
                  </tr>
                </tbody>
                <tbody v-if="subscription">
                  <tr>
                    <td class="text-left">
                      {{ subscription.plan.name }}
                    </td>
                    <td class="text-left">
                      {{ getDate(subscription.startDate) }}
                    </td>
                     <td class="text-left">
                      {{ subscription.plan.typeOfPlan }}
                    </td>
                     <td class="text-left">
                      {{ subscription.plan.price }}
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            <h2 v-if="cartItems.length" class="ml-4">
              Total paid: ${{ calculateTotal() }}
            </h2>
            <h2 v-if="wageItems.length" class="ml-4">
              Total paid: $ {{ wageItems[0].price }}
            </h2>
            <h2 v-if="subscription" class="ml-4">
              Total paid: $ {{ subscription.plan.price }}
            </h2>
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
    cartItems: [],
    wageItems: [],
    subscription: null,
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
        .then((res) => {
          console.log(res.data);
          this.subscription = res.data;
        });
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
    getOrderForWage: function () {
      const transactionId = this.$route.params.transactionId;

      this.$store.dispatch("getOrderForWage", transactionId).then((res) => {
        if (!res.data) {
          return;
        }

        this.wageItems.push({ price: res.data });
      });
    },
    getDate(millis) {
      return new Date(millis).toLocaleString();
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
          if (!resp.data.equipmentCart && !resp.data.serviceCart) {
            this.getOrderForWage();

            return;
          }

          if (this.currentUser.role === "ROLE_EQUIPMENT_BUYER") {
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