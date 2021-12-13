<template>
    <div>
        <h1>SUCCESSFULLY PAID</h1>
    </div>
</template>

<script>
export default {
  name: "Success",
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
      .dispatch("updateTransaction", { transactionId, status: 1 })
      .then(() => {});
  },
};
</script>