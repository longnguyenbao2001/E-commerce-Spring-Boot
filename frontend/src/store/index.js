import { createStore } from 'vuex'

const store = createStore({
    state() {
        return {
            isAuthenticated: localStorage.getItem('accessToken') && localStorage.getItem('userData'),
            isValidVariant: false,
            currentVariant: null,
            currentQuantity: 1,
            isValidQuantity: false,
            cart: localStorage.getItem('cart') ? JSON.parse(localStorage.getItem('cart')) : [],
        }
    },
    mutations: {
        SET_AUTHENTICATION_STATUS(state, status) {
            state.isAuthenticated = status
        },
        SET_VALIDVARIANT_STATUS(state, status) {
            state.isValidVariant = status
        },
        SET_CURRENTVARIANT_DATA(state, data) {
            state.currentVariant = data
        },
        PUSH_CART_DATA(state, data) {
            for (const item of state.cart) {
                if (item.productId == data.productId) {
                    if (item.variantData.id === data.variantData.id) {
                        item.quantity += data.quantity
                        if (item.variantData.quantity >= item.quantity) {
                            localStorage.setItem('cart', JSON.stringify(state.cart))
                        }
                        else {
                            item.quantity -= data.quantity
                        }
                        return
                    }
                }
            }
            state.cart.push(data)
            localStorage.setItem('cart', JSON.stringify(state.cart))
        },
        REMOVEFROM_CART(state, data) {
            state.cart = state.cart.filter((item) =>
                !(item.productId == data.productId
                    && item.variantData.id === data.variantId))

            localStorage.setItem('cart', JSON.stringify(state.cart))
        },
        SET_CURRENTQUANTITY_VALUE(state, value) {
            state.currentQuantity = value
        },
        SET_VALIDQUANTITY_STATUS(state, status) {
            state.isValidQuantity = status
        },
    },
    actions: {
        updateAuthenticationStatus({ commit }, status) {
            commit('SET_AUTHENTICATION_STATUS', status)
        },
        updateValidVariantStatus({ commit }, status) {
            commit('SET_VALIDVARIANT_STATUS', status)
        },
        updateCurrentVariant({ commit }, data) {
            commit('SET_CURRENTVARIANT_DATA', data)
        },
        addToCart({ commit }, data) {
            commit('PUSH_CART_DATA', data)
        },
        removeFromCart({ commit }, data) {
            commit('REMOVEFROM_CART', data)
        },
        updateCurrentQuantity({ commit }, value) {
            commit('SET_CURRENTQUANTITY_VALUE', value)
        },
        updateValidQuantityStatus({ commit }, status) {
            commit('SET_VALIDQUANTITY_STATUS', status)
        },
    },
});

export default store;
