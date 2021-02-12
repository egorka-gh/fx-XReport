<template>
  <v-app>
    <!-- Sizes your content based upon application components -->
    <v-main>
      <!-- Provides the application the proper gutter -->
      <v-container fluid fill-height>
        <!-- If using vue-router -->
        <v-row justify="center">
          <v-card elevation="16" width="400px" height="100%" class="pa-2">
            <v-virtual-scroll
              :bench="benched"
              :items="items"
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
            <ReportRunner v-if="report" v-bind:report="report"></ReportRunner>
          </v-card>
        </v-row>
        {{ report }}
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import ReportRunner from "./components/ReportRunner.vue";
export default {
  name: "App",

  components: { ReportRunner },

  data: () => ({
    benched: 0,
    report: null
  }),
  computed: {
    reportid() {
      return this.report ? this.report.id : "";
    },
    items() {
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
