<template>
  <v-snackbar color="red" top v-model="show" :timeout="timeout">
    {{ text }}

    <template v-slot:action="{ attrs }">
      <v-btn text v-bind="attrs" @click="show = false">
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>
<script>
export default {
  data: () => ({
    timeout: 5000,
  }),
  props: {
    value: Boolean,
    text: String,
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
};
</script>