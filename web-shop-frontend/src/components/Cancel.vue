<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md6>
        <v-card class="elevation-12" style="padding: 40px">
          <v-card-text class="text-center">
            <div class="card mx-5 my-5">
              <img
                class="mx-auto my-5"
                src="../assets/cancel.png"
                alt="Card image cap"
                height="300"
                width="300"
              />
              <p class="display-1 font-weight-bolder mx-auto">
                <b>Canceled payment!</b>
              </p>
            </div>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "Cancel",
  data: () => ({}),
  props: {},
  computed: {},
  methods: {},
  mounted: function () {
    const transactionId = this.$route.params.transactionId;

    this.$store
      .dispatch("updateTransaction", {
        transactionId: transactionId,
        status: 3,
      })
      .then(() => {});
  },
};
</script>