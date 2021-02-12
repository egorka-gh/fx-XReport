<template>
  <v-container>
    <v-row>
      <div class="text-subtitle-1">{{ parameter.name }}</div>
    </v-row>
    <v-row>
      <v-col cols="6">
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
              v-model="formattedFrom"
              label="Начало периода"
              prepend-icon="mdi-calendar"
              readonly
              dense
              outlined
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="parameter.valFrom"
            :first-day-of-week="1"
            locale="ru-ru"
            @input="menu2 = false"
          ></v-date-picker>
        </v-menu>
      </v-col>
      <v-col cols="6">
        <v-menu
          v-model="menu1"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          offset-y
          min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="formattedTo"
              label="Конец периода"
              prepend-icon="mdi-calendar"
              readonly
              dense
              outlined
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="parameter.valTo"
            :first-day-of-week="1"
            locale="ru-ru"
            @input="menu1 = false"
          ></v-date-picker>
        </v-menu>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "ParameterPeriod",
  props: {
    parameter: null
  },
  computed: {
    formattedFrom() {
      return this.formatDate(this.parameter.valFrom);
    },
    formattedTo() {
      return this.formatDate(this.parameter.valTo);
    }
  },
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    menu1: false,
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
