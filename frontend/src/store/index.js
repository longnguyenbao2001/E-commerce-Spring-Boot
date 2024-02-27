import { createStore } from 'vuex'

const store = createStore({
    state() {
        return {
            isAuthenticated: false,
        }
    },
    mutations: {
        SET_AUTHENTICATION_STATUS(state, status) {
            state.isAuthenticated = status;
        },
    },
    actions: {
        updateAuthenticationStatus({ commit }, status) {
            commit('SET_AUTHENTICATION_STATUS', status);
        },
    },
});

export default store;
