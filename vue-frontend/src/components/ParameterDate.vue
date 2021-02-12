<template>
  <v-container>
    <v-menu
      v-model="menu2"
      :close-on-content-click="false"
      :nudge-right="40"
      transition="scale-transition"
      offset-y
      min-width="auto"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-text-field
          v-model="computedDateFormatted"
          :label="parameter.name"
          prepend-icon="mdi-calendar"
          readonly
          dense
          outlined
          v-bind="attrs"
          v-on="on"
        ></v-text-field>
      </template>
      <v-date-picker
        v-model="parameter.valDate"
        :first-day-of-week="1"
        locale="ru-ru"
        @input="menu2 = false"
      ></v-date-picker>
    </v-menu>
  </v-container>
</template>

<script>
export default {
  name: "ParameterDate",
  props: {
    parameter: null
  },
  computed: {
    computedDateFormatted() {
      return this.formatDate(this.parameter.valDate);
    }
  },
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    menu2: false
  }),
  methods: {
    formatDate(date) {
      if (!date) return null;

      const [year, month, day] = date.split("-");
      return `${day}.${month}.${year}`;
    }
  }
};
</script>
