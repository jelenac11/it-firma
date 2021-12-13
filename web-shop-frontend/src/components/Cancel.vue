<template>
    <h1>CANCELLED PAYMENT</h1>
</template>

<script>
export default {
  name: "Cancel",
  data: () => ({
  }),
  props: {
  },
  computed: {
    
  },
  methods: {
  },
  mounted: function () {
    const transactionId = this.$route.query.transactionId;
    
    this.$store
      .dispatch("updateTransaction", { transactionId, status: 3 })
      .then(() => {});
  },
}
</script>