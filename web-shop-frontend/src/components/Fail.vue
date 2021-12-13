<template>
    <h1>Ooops...</h1>
</template>

<script>
export default {
  name: "Fail",
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
      .dispatch("updateTransaction", { transactionId, status: 2 })
      .then(() => {});
  },
}
</script>