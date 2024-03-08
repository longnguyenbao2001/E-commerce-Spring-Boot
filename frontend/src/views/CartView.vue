<template>
    <h1 class="text-center">MY CART</h1>

    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Product name</th>
                <th scope="col">Product variant</th>
                <th scope="col">Quantity</th>
                <th scope="col">Unit price</th>
                <th scope="col">Total price</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="i in cartData.length">
                <th scope="row">{{ i }}</th>
                <td>
                    <router-link :to="{ path: `/products/${cartData[i - 1].productId}` }" class="nav-link">
                        <p class="text-primary">{{ cartData[i - 1].productName }}</p>
                    </router-link>
                </td>
                <td>{{ variantsData[i - 1] }}</td>
                <td>
                    <QuantityButtons :productData="cartData[i - 1]" />
                </td>
                <td>{{ cartData[i - 1].variantData.unitPrice }}</td>
                <td>{{ cartData[i - 1].quantity * cartData[i - 1].variantData.unitPrice }}</td>
                <td>
                    <button type="button" class="btn btn-danger"
                        @click="deleteFromCart(cartData[i - 1].productId, cartData[i - 1].variantData.id)">
                        Remove item
                    </button>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="d-flex justify-content-end">
        <div class="card w-25 p-3">
            <div class="card-header bg-transparent">
                <h4>Cart Summary</h4>
            </div>
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <h6 class="font-weight-medium">Subtotal</h6>
                    <h6 class="font-weight-medium">{{ totalCost }}</h6>
                </div>
                <div class="d-flex justify-content-between">
                    <h6 class="font-weight-medium">Shipping</h6>
                    <h6 class="font-weight-medium">Free</h6>
                </div>
            </div>
            <div class="card-footer border-secondary bg-transparent">
                <div class="d-flex justify-content-between">
                    <h5 class="font-weight-bold">Total</h5>
                    <h5 class="font-weight-bold">{{ totalCost }}</h5>
                </div>
                <button v-if="isAuthenticated" class="btn btn-primary input-group">Proceed to checkout</button>
                <router-link v-else :to="{ path: '/signin' }" class="nav-link text-end fst-italic">
                    Sign in to checkout
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
import QuantityButtons from '../components/QuantityButtons.vue'

export default {
    name: 'CartView',
    components: {
        QuantityButtons
    },
    data() {
        return {
            cartData: [],
            variantsData: [],
            totalCost: 0,
        }
    },
    mounted() {
        this.getCartData()
    },
    methods: {
        getCartData() {
            this.cartData = this.$store.state.cart
            this.totalCost = 0
            let label = '', name = ''
            let variantDetail = ''

            for (const item of this.cartData) {
                variantDetail = ''
                for (const variant of item.variantData.listVariantValues) {
                    label = variant.variantLabel
                    name = variant.name

                    variantDetail += `${label}: ${name}, `
                }

                this.totalCost += item.quantity * item.variantData.unitPrice

                if (item.variantData.listVariantValues.length > 0) {
                    this.variantsData.push(variantDetail.slice(0, -2))
                } else {
                    this.variantsData.push('Default variant')
                }
            }
        },
        deleteFromCart(productId, variantId) {
            const data = {
                'productId': productId,
                'variantId': variantId
            }

            this.$store.dispatch('removeFromCart', data)

            this.getCartData()
        }
    },
    watch: {
        cartData: {
            handler: 'getCartData',
            immediate: false
        }
    },
    computed: {
        isAuthenticated() {
            return this.$store.state.isAuthenticated
        }
    },
}
</script>