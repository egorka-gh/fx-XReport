<template>
  <v-container>
    <v-select
      v-model="parameter.valString"
      :items="parameter.itemsInt"
      item-text="label"
      item-value="id"
      :label="parameter.name"
      outlined
      dense
    ></v-select>
  </v-container>
</template>

<script>
export default {
  name: "ParameterSimple",
  props: {
    parameter: null
  },
  data() {
    return {};
  }
};
</script>
