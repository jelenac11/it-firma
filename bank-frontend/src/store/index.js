import Vue from 'vue'
import Vuex from "vuex";
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(Vuex)
Vue.use(VueAxios, axios)

Vue.prototype.$axios = axios;

export default new Vuex.Store({
    state: {
        allGroceries: [],
    },
    getters: {

    },
    mutations: {
        setGroceries(state, groceries) {
            state.allGroceries = groceries;
        },
    },
    actions: {
        pay({ commit }, data) {
            return new Promise((resolve, reject) => {
                axios({ url: 'https://localhost:9002/api/payment/confirm/' + data.id, data: data.paymentData, method: 'POST' })
                    .then(resp => {
                        commit('setGroceries', resp.data)
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },

        payQr({ commit }, data) {
            return new Promise((resolve, reject) => {
                axios({ url: 'https://localhost:9002/api/payment/qr/pay/' + data, method: 'GET' })
                    .then(resp => {
                        commit('setGroceries', resp.data)
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },
    },
    modules: {}
});
