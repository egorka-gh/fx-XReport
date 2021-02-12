<template>
  <v-container>
    <v-row>
      <div class="text-h5">{{ report.name }}</div>
    </v-row>

    <v-row class="text-center">
      <v-col cols="12">
        <ParameterRender
          v-for="(param, i) in report.parameters"
          :key="i"
          :parameter="param"
        ></ParameterRender>
      </v-col>
    </v-row>
    <div class="text-right">
      <v-btn color="blue-grey" large class="ma-2 white--text">
        <span style="display: inline-block; margin-right: 10px;"> Html </span>
        <v-icon right large>
          mdi-file-table-outline
        </v-icon>
      </v-btn>
      <v-btn color="blue-grey" class="ma-2 white--text" large>
        <span style="display: inline-block; margin-right: 10px;"> pdf </span>
        <v-icon right large>
          mdi-file-pdf-outline
        </v-icon>
      </v-btn>
      <v-btn color="blue-grey" class="ma-2 white--text" large>
        <span style="display: inline-block; margin-right: 10px;">xls</span>
        <v-icon right large>
          mdi-table-multiple
        </v-icon>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import ParameterRender from "./ParameterRender.vue";
export default {
  name: "ReportRunner",
  components: { ParameterRender },
  computed: {},
  props: {
    report: null
  }
};
</script>
