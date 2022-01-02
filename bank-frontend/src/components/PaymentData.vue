<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card class="elevation-12 py-10">
          <v-card-text>
            <div class="card-wrapper"></div>
            <v-form class="px-10 mt-7" id="forma" v-model="isValid">
              <v-text-field
                id="number"
                name="number"
                color="blue darken-2"
                prepend-icon="mdi-card-account-details"
                label="Card number"
                type="text"
                v-model="paymentData.pan"
                :rules="panRules"
                required
              ></v-text-field>
              <v-text-field
                id="name"
                name="name"
                color="blue darken-2"
                prepend-icon="mdi-account"
                label="Full name"
                type="text"
                v-model="paymentData.cardHolderName"
                :rules="nameRules"
                required
              ></v-text-field>
              <v-text-field
                id="expiration"
                name="expiration"
                color="blue darken-2"
                prepend-icon="mdi-calendar-range"
                label="Expiration date"
                type="text"
                v-model="paymentData.expires"
                :rules="expiryRules"
                required
              ></v-text-field>
              <v-text-field
                id="cvv"
                name="cvv"
                color="blue darken-2"
                prepend-icon="mdi-account-lock"
                label="CVV"
                type="text"
                v-model="paymentData.securityCode"
                :rules="cvvRules"
                required
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn
              color="blue darken-2"
              class="my-2"
              style="width: 85%; color: white"
              :disabled="!isValid"
              v-on:click="pay"
              >Confirm</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
    <v-snackbar v-model="wrong" top color="red darken-3">
      Invalid credit card.
    </v-snackbar>
  </v-container>
</template>

<script>
import * as Card from "card";

export default {
  name: "PaymentData",
  data: () => ({
    paymentData: {
      pan: null,
      cardHolderName: null,
      securityCode: null,
      expires: null,
    },
    isValid: false,
    wrong: false,
    message: "",
    panRules: [
      (v) => !!v || "Pan is required",
      (v) =>
        (v && /[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}/.test(v)) ||
        "Pan is not valid.",
    ],
    cvvRules: [
      (v) => !!v || "Cvv is required",
      (v) =>
        (v && /[0-9]{3,4}/.test(v)) || "3-digit (or 4) code on back of card",
    ],
    expiryRules: [
      (v) => !!v || "Expiry date is required",
      (v) =>
        (v && /^(0[1-9]|1[0-2]) \/ ([0-9]{4}|[0-9]{2})$/.test(v)) ||
        "Use month/year format",
    ],
    nameRules: [(v) => !!v || "Card holder name is required"],
  }),
  mounted() {
    new Card({
      form: "#forma",
      container: ".card-wrapper",
      formSelectors: {
        numberInput: "#number",
        expiryInput: "#expiration",
        cvcInput: "#cvv",
        nameInput: "#name",
      },
      width: 340,
      formatting: true,
      placeholders: {
        number: "•••• •••• •••• ••••",
        name: "Full Name",
        expiry: "••/••",
        cvc: "•••",
      },
    });
  },
  methods: {
    pay: function () {
      let pan = this.paymentData.pan.replace(/\s/g, "");
      let expirationDate = this.paymentData.expires.replace(/\s/g, "");
      this.$store
        .dispatch("pay", {
          id: this.$route.params.paymentId,
          paymentData: {
            pan: pan,
            cardHolderName: this.paymentData.cardHolderName,
            securityCode: this.paymentData.securityCode,
            expirationDate: expirationDate,
          },
        })
        .then((res) => {
          window.location.href = res.data;
        })
        .catch((err) => {
          this.wrong = true;
          console.log(err.response);
          this.message = err.response.data;
        });
    },
  },
};
</script>