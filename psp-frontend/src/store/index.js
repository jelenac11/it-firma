import Vue from 'vue'
import Vuex from "vuex";
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(Vuex)
Vue.use(VueAxios, axios)

Vue.prototype.$axios = axios;
const token = localStorage.getItem('token')
if (token) {
    Vue.prototype.$axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
}

export default new Vuex.Store({
    state: {
        token: localStorage.getItem('token') || '',
        user: {},
        setSupportedPaymentMethods: false,
        allMethods: [],
        allGroceries: [],
    },
    getters: {
        authenticated: state => !!state.token,
        setSupportedPaymentMethods: state => state.setSupportedPaymentMethods,
        currentUser: state => state.user,
    },
    mutations: {
        setMethods(state, methods) {
            state.allMethods = methods;
        },

        auth_success_token(state, token) {
            state.token = token;
        },

        setSetSupportedPaymentMethods(state, setSupportedPaymentMethods) {
            state.setSupportedPaymentMethods = setSupportedPaymentMethods;
        },

        setCurrentUser(state, user) {
            state.user = user;
        },

        logout(state) {
            localStorage.removeItem('token');
            state.token = '';
            state.user = {};
        },

        setGroceries(state, groceries) {
            state.allGroceries = groceries;
        },
    },
    actions: {
        getAllMethods({ commit }) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/api/payment-methods', method: 'GET' })
                    .then(resp => {
                        commit('setMethods', resp.data);
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },

        getSupportedMethods({ commit }) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/api/payment-methods/merchant', method: 'GET' })
                    .then(resp => {
                        commit('setMethods', resp.data);
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },

        getSupportedMethodsForMerchant({ commit }, orderDataId) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/api/merchants/supported-methods/' + orderDataId, method: 'GET' })
                    .then(resp => {
                        commit('setMethods', resp.data);
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },

        chosenMethods({ commit }, chosen) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/api/payment-methods/chosen', data: chosen, method: 'POST' })
                    .then(resp => {
                        commit('setGroceries', resp.data);
                        commit('setSetSupportedPaymentMethods', true);
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },

        login({ commit }, user) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/auth/login', data: user, method: 'POST' })
                    .then(resp => {
                        const token = resp.data.accessToken;
                        localStorage.setItem('token', token);
                        axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
                        commit('auth_success_token', token);
                        commit('setSetSupportedPaymentMethods', resp.data.setSupportedPaymentMethods);
                        resolve(resp);
                    })
                    .catch(err => {
                        localStorage.removeItem('token');
                        reject(err);
                    });
            });
        },

        register({ commit }, user) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/auth/sign-up', data: user, method: 'POST' })
                    .then(resp => resolve(resp))
                    .catch(err => {
                        reject(err);
                        commit('logout');
                    });
            });
        },

        verify({ commit }, token) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/auth/verify/' + token, method: 'GET' })
                    .then(resp => {
                        commit('setGroceries', resp.data);
                        resolve(resp);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },

        logout({ commit }) {
            return new Promise((resolve) => {
                commit('logout');
                localStorage.removeItem('token');
                delete Vue.prototype.$axios.defaults.headers.common['Authorization'];
                resolve();
            });
        },

        getCurrentUser({ commit }) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/auth/current-user', method: 'GET' })
                    .then(resp => {
                        const user = resp.data;
                        commit('setCurrentUser', user);
                        commit('setSetSupportedPaymentMethods', user.setSupportedPaymentMethods);
                        resolve(resp);
                    })
                    .catch(err => {
                        commit('logout');
                        reject(err);
                    });
            });
        },

        getPaymentUrl({ commit }, data) {
            return new Promise((resolve, reject) => {
                axios({ url: 'http://localhost:8095/api/payment-methods/get-payment-url', method: 'POST', data: data })
                    .then((res) => {
                        commit('setGroceries', res.data);
                        window.location.href = res.data;
                        resolve(res);
                    })
                    .catch(err => {
                        console.log(err.response);
                        reject(err);
                    });
            });
        },
    },
    modules: {}
});
