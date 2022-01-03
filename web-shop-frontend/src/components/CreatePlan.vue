<template>
  <div>
    <v-row justify="center">
      <v-dialog v-model="show" persistent max-width="400px">
        <v-card class="px-4 py-4">
          <v-card-title>
            <h3 style="color: #4caf50; margin-bottom: 30px; margin-top: 10px">
              Create Plan
            </h3>
          </v-card-title>
          <v-card-text>
            <v-form v-model="isValid" ref="formAddToCart">
              <v-text-field
                color="green"
                name="name"
                label="Name"
                type="text"
                :rules="[(v) => !!v || 'Name is required']"
                v-model="name"
                required
              ></v-text-field>
              <v-text-field
                color="green"
                name="description"
                label="Description"
                type="text"
                v-model="description"
              ></v-text-field>
              <v-select
                :items="courses"
                label="Course"
                v-model="course"
                name="course"
                required
                item-text="name"
                return-object
                :rules="[(v) => !!v || 'Course is required']"
              ></v-select>
              <v-select
                :items="type"
                label="Type of plan"
                v-model="typeOfPlan"
                :rules="[(v) => !!v || 'Type of plan is required']"
                required
              ></v-select>
              <v-text-field
                color="green"
                name="price"
                label="Price"
                type="number"
                v-model="price"
                :rules="rules"
              ></v-text-field>
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
    <SnackBarError v-model="showError" :text="textError"></SnackBarError>
  </div>
</template>

<script>
import SnackBarError from "./SnackBarError.vue";

export default {
  name: "CreatePlan",
  components: {
    SnackBarError,
  },
  data: () => ({
    name: "",
    description: "",
    course: "",
    typeOfPlan: "",
    price: 0,
    type: ["MONTH", "YEAR"],
    courses: [],
    isValid: true,
    rules: [
      (v) => !!v || "Price is required",
      (v) => (v && v >= 0) || "Price must be a positive number",
    ],
    showError: false,
    textError: "",
  }),
  props: {
    value: Boolean,
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
    getAllCourses: function () {
      this.$store
        .dispatch("getAllCoursesByMerchant")
        .then((resp) => {
          this.courses = resp.data;
        })
        .catch((err) => {
          this.showError = true;
          this.textError = err.response.data;
        });
    },
    close: function () {
      this.closeDialogAndResetData();
    },
    add: function () {
      const plan = {
        name: this.name,
        description: this.description,
        course: this.course,
        typeOfPlan: this.typeOfPlan,
        price: this.price,
      };

      this.$store
        .dispatch("createPlan", plan)
        .then(() => {
          this.$router.go();
        })
        .catch((err) => {
          this.showError = true;
          this.textError = err.response.data;
        });
    },
    closeDialogAndResetData: function () {
      this.$refs.formAddToCart.reset();
      this.name = "";
      this.description = "";
      this.course = "";
      this.typeOfPlan = "";
      this.price = 0;
      this.show = false;
    },
  },
  created() {
    this.getAllCourses();
  },
};
</script>