<template>
  <v-app>
    <!-- Sizes your content based upon application components -->
    <v-main>
      <!-- Provides the application the proper gutter -->
      <v-container fluid class="mt-5">
        <!-- If using vue-router -->
        <v-row justify="center">
          <v-card elevation="16" width="400px" height="100%" class="pa-2">
            <v-select
              class="mx-2"
              v-model="source"
              :items="sources"
              item-text="name"
              item-value="id"
              label="Источник"
            ></v-select>
            <div class="text-h6">Отчеты</div>
            <v-virtual-scroll
              :bench="benched"
              :items="reports"
              item-height="64"
              fill-height
              height="600"
            >
              <template v-slot:default="{ item }">
                <v-sheet :color="item.id == reportid ? 'grey lighten-3' : ''">
                  <v-list-item :key="item.id" v-on:click="report = item">
                    <v-list-item-content>
                      <v-list-item-title>
                        {{ item.name }}
                      </v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-sheet>

                <v-divider></v-divider>
              </template>
            </v-virtual-scroll>
          </v-card>
          <v-card elevation="16" width="700px" class="pa-2">
            <ReportRunner
              v-if="report"
              v-bind:report="report"
              v-on:run="runReport"
            ></ReportRunner>
          </v-card>
          <v-overlay :value="busy">
            <p>{{ reptype }}</p>
            <v-progress-circular indeterminate size="64"></v-progress-circular>
          </v-overlay>
        </v-row>
      </v-container>
      <v-snackbar v-model="hasError">
        {{ errorText }}

        <template v-slot:action="{ attrs }">
          <v-btn color="pink" text v-bind="attrs" @click="hasError = false">
            Закрыть
          </v-btn>
        </template>
      </v-snackbar>
    </v-main>
  </v-app>
</template>

<script>
import ReportRunner from "./components/ReportRunner.vue";
import { repApi, baseURL } from "./api-common";

export default {
  name: "App",

  components: { ReportRunner },
  created: function() {
    //create client uid
    if (!this.uid) {
      this.generateUUID();
    }
    //call remote api
    //this.sources = this.fakeSources;
    this.loadSorces();
    if (this.sources == null) {
      this.sources = [];
      return;
    }
    this.source = this.sources[0];
  },
  data: () => ({
    uid: "",
    benched: 0,
    busy: false,
    hasError: false,
    errorText: "",
    report: null,
    reports: [],
    source: "",
    sources: [],
    reptype: ""
  }),
  methods: {
    generateUUID() {
      var d = new Date().getTime(); //Timestamp
      var d2 =
        (performance && performance.now && performance.now() * 1000) || 0; //Time in microseconds since page-load or 0 if unsupported
      this.uid = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(
        /[xy]/g,
        function(c) {
          var r = Math.random() * 16; //random number between 0 and 16
          if (d > 0) {
            //Use timestamp until depleted
            r = (d + r) % 16 | 0;
            d = Math.floor(d / 16);
          } else {
            //Use microseconds since page-load if supported
            r = (d2 + r) % 16 | 0;
            d2 = Math.floor(d2 / 16);
          }
          return (c === "x" ? r : (r & 0x3) | 0x8).toString(16);
        }
      );
    },
    loadSorces() {
      this.source = "";
      this.sources = [];
      this.hasError = false;
      //this.busy = true;
      repApi
        .get("source")
        .then(response => {
          if (response != null && response.data != null) {
            this.sources = response.data;
          }
          if (this.sources.length) this.source = this.sources[0];
        })
        .catch(error => {
          this.hasError = true;
          this.errorText = "Ошибка сервиса или сервис не доступен";
          console.log(error);
        });
      //.finally(() => (this.busy = false));
    },
    loadReports() {
      this.report = null;
      this.reports = [];
      this.hasError = false;
      if (this.source == null) return;
      repApi
        .get(`report/${this.source.type}/${this.source.id}`)
        .then(response => {
          if (response != null && response.data != null) {
            this.reports = response.data;
          }
        })
        .catch(error => {
          this.hasError = true;
          this.errorText = "Ошибка сервиса или сервис не доступен";
          console.log(error);
        });
    },
    runReport(outputType) {
      if (this.report == null) return;
      if (this.source == null) return;

      //check params
      let paramErr = false;
      for (const p of this.report.parameters) {
        //check dates
        switch (p.id) {
          case "period":
          case "periodt":
            paramErr = p.valFrom == null || p.valTo == null;
            break;
          case "pdate":
            paramErr = p.valDate == null;
            break;
          default:
            paramErr = p.id != "pkasir" && !p.valString;
        }
        if (paramErr) {
          this.hasError = true;
          this.errorText = "Указаны не все параметры отчета";
          return;
        }
      }

      this.hasError = false;
      /*
      this.reptype = outputType;
      //emulate api call
      this.busy = true;
      setTimeout(() => {
        this.busy = false;
      }, 3000);
      */
      this.busy = true;
      repApi
        .post(`report/build`, {
          report: this.report,
          source: this.source.id,
          format: outputType
        })
        .then(response => {
          if (response != null && response.data != null) {
            const res = response.data;
            if (res.hasError) {
              this.hasError = true;
              this.errorText = "Ошибка выполнения отчета";
              console.log(res.error);
              return;
            }
            //open report in new window
            window.open(baseURL + res.url, "_blank");
          }
        })
        .catch(error => {
          this.hasError = true;
          this.errorText = "Ошибка сервиса или сервис не доступен";
          console.log(error);
        })
        .finally(() => (this.busy = false));
    }
  },
  watch: {
    source: function() {
      /*
      this.reports = [];
      this.report = null;
      //call remote api
      this.reports = this.fakeReports;
      */
      this.loadReports();
    }
  },
  computed: {
    reportid() {
      return this.report ? this.report.id : "";
    },
    fakeSources() {
      return [
        { persistState: 1, id: "ukmUKM4", type: 1, name: "UKM4" },
        { persistState: 1, id: "smSMGAMA", type: 2, name: "С1" },
        { persistState: 1, id: "smSKONTCO", type: 2, name: "ЦО" },
        { persistState: 1, id: "smSKONT08", type: 2, name: "Г1" },
        { persistState: 1, id: "smS6SM", type: 2, name: "C6" },
        { persistState: 1, id: "smS5SM", type: 2, name: "C5" },
        { persistState: 1, id: "smS4SM", type: 2, name: "C4" },
        { persistState: 1, id: "smS2SM", type: 2, name: "C2" },
        { persistState: 1, id: "smRC1SM", type: 2, name: "РЦ1" },
        { persistState: 1, id: "smM1SM", type: 2, name: "М1" },
        { persistState: 1, id: "smK4SM", type: 2, name: "K4" },
        { persistState: 1, id: "smK1SM", type: 2, name: "К1" },
        { persistState: 1, id: "smG2SM", type: 2, name: "K3" }
      ];
    },

    fakeReports() {
      return [
        {
          persistState: 1,
          id: "articleTotalUKM",
          src_type: 1,
          name: "Итоги по артикулу",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "particle",
              src_type: 0,
              name: "Артикул",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "avgCheckUKM",
          src_type: 1,
          name: "Средний чек",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "cafeStaffTotalUKM",
          src_type: 1,
          name: "Кассиры кафе итоги",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "cashMoneyUKM",
          src_type: 1,
          name: "Выручка по кассам",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "discountTotalUKM",
          src_type: 1,
          name: "Скидки",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "operDay",
          src_type: 1,
          name: "Кассовые квитки Стандартные",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "pdate",
              src_type: 0,
              name: "На дату",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pkasir",
              src_type: 1,
              name: "Кассир",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "operDayComp",
          src_type: 1,
          name: "Кассовые квитки Компьютерные",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "pdate",
              src_type: 0,
              name: "На дату",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pkasir",
              src_type: 1,
              name: "Кассир",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "operDayFF",
          src_type: 1,
          name: "Кассовые квитки (без бланка)",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "pdate",
              src_type: 0,
              name: "На дату",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pkasir",
              src_type: 1,
              name: "Кассир",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "operDayL",
          src_type: 1,
          name: "Кассовые квитки Лето",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "pdate",
              src_type: 0,
              name: "На дату",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pkasir",
              src_type: 1,
              name: "Кассир",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmArtQttByTime",
          src_type: 1,
          name: "Продажи за интервал времени",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "partclass",
              src_type: 0,
              name: "Классификатор артикулов",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "periodt",
              src_type: 0,
              name: "Период времени",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmCafeTotal",
          src_type: 1,
          name: "Кафе продажи по артикулам",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "pcafe",
              src_type: 1,
              name: "Кафе",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmCashMoneyTotal",
          src_type: 1,
          name: "Сверка выручки",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmCertNotUsed",
          src_type: 1,
          name: "Не использованные сертификаты",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmCertUsage",
          src_type: 1,
          name: "Использование сертификатов",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmDailyTotal",
          src_type: 1,
          name: "Итоги продаж",
          engine: 1,
          userUID: null,
          parameters: []
        },
        {
          persistState: 1,
          id: "ukmDiscount3050",
          src_type: 1,
          name: "Уценка 20% - 50%",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmDiscountByStaff",
          src_type: 1,
          name: "Скидки сотрудникам",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmHourTotal",
          src_type: 1,
          name: "Почасовые итоги",
          engine: 1,
          userUID: null,
          parameters: []
        },
        {
          persistState: 1,
          id: "ukmMoneyOperation",
          src_type: 1,
          name: "Изъятие выручки по кассам",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "periodt",
              src_type: 0,
              name: "Период времени",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmStaffTotal",
          src_type: 1,
          name: "Итоги по сотрудникам (рабочее время)",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmStaffTotalF",
          src_type: 1,
          name: "Итоги по сотрудникам (полный)",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        },
        {
          persistState: 1,
          id: "ukmStatByTime",
          src_type: 1,
          name: "Почасовая статистика",
          engine: 1,
          userUID: null,
          parameters: [
            {
              persistState: 1,
              id: "period",
              src_type: 0,
              name: "Период",
              listType: 0,
              listSql: null,
              keepTime: false,
              itemsInt: null,
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            },
            {
              persistState: 1,
              id: "pstore",
              src_type: 1,
              name: "Магазин",
              listType: 1,
              listSql: "",
              keepTime: false,
              itemsInt: [
                {
                  persistState: 1,
                  id: 13001,
                  label: ". Гипермаркет1 МКАД"
                },
                {
                  persistState: 1,
                  id: 1117002,
                  label: ". Минимаркет1 Жудро"
                },
                {
                  persistState: 1,
                  id: 1119001,
                  label: ". Супермаркет Копилка1 Аквабел"
                },
                {
                  persistState: 1,
                  id: 1114001,
                  label: ". Супермаркет Копилка2 Жукова"
                },
                {
                  persistState: 1,
                  id: 3001,
                  label: ". Супермаркет Копилка3 Караван"
                },
                {
                  persistState: 1,
                  id: 1120001,
                  label: ". Супермаркет Копилка4 Лукъян"
                },
                {
                  persistState: 1,
                  id: 1117001,
                  label: ". Супермаркет Копилка6 Белинс"
                },
                {
                  persistState: 1,
                  id: 13004,
                  label: ". Супермаркет1 Гамарника"
                },
                {
                  persistState: 1,
                  id: 1115001,
                  label: ". Супермаркет4  Машерова"
                },
                {
                  persistState: 1,
                  id: 1115002,
                  label: ". Супермаркет5 Кальварийская"
                },
                {
                  persistState: 1,
                  id: 13005,
                  label: "LOYA"
                },
                {
                  persistState: 1,
                  id: 1120002,
                  label: "АЛКОМАРКЕТ"
                },
                {
                  persistState: 1,
                  id: 13006,
                  label: "АЛКОМАРКЕТ2"
                },
                {
                  persistState: 1,
                  id: 13003,
                  label: "Гипер1 Экспобел FUSION TEST"
                },
                {
                  persistState: 1,
                  id: 13002,
                  label: "Гипер1 Экспобел Кафе Щедринка"
                },
                {
                  persistState: 1,
                  id: 3002,
                  label: "Гипер2 Караван Фреш"
                },
                {
                  persistState: 1,
                  id: 1122001,
                  label: "Супермаркет7 Дзержинского"
                },
                {
                  persistState: 1,
                  id: 1121001,
                  label: "Супермаркет8 Палаццо"
                }
              ],
              valFrom: null,
              valTo: null,
              valDate: null,
              valInt: 0,
              valString: null
            }
          ]
        }
      ];
    }
  }
};
</script>
