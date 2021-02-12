<template>
  <v-container>
    <v-text-field
      dense
      :label="parameter.name"
      outlined
      v-model="parameter.valString"
    >
    </v-text-field>
  </v-container>
</template>

<script>
export default {
  name: "ParameterSimple",
  props: {
    parameter: null
  }
};
</script>
