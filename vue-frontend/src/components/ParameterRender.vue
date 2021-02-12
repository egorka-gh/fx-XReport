<template>
  <component
    :is="parameterComponent"
    :parameter="parameter"
    :key="parameter.id"
  ></component>
</template>

<script>
import ParameterSimple from "./ParameterSimple.vue";
import ParameterSelect from "./ParameterSelect.vue";
import ParameterDate from "./ParameterDate.vue";
import ParameterPeriod from "./ParameterPeriod.vue";
export default {
  name: "ParameterRender",
  props: {
    parameter: null
  },
  components: {
    ParameterSimple,
    ParameterSelect,
    ParameterDate,
    ParameterPeriod
  },
  computed: {
    parameterComponent() {
      /*
      if (!this.user) return Guest;
      if (!this.user.subscription) return User;
      */
      if (this.parameter.listType > 0) return ParameterSelect;
      if (this.parameter.id == "pdate") return ParameterDate;
      if (this.parameter.id == "period") return ParameterPeriod;

      return ParameterSimple;
    }
  }
};
</script>
