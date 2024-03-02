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
                <td>{{ cartData[i - 1].quantity }}</td>
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
</template>

<script>
export default {
    name: 'CartView',
    data() {
        return {
            cartData: [],
            variantsData: [],
        }
    },
    mounted() {
        this.getCartData()
    },
    methods: {
        getCartData() {
            this.cartData = this.$store.state.cart
            let label = '', name = ''
            let variantDetail = ''

            for (const item of this.cartData) {
                variantDetail = ''
                for (const variant of item.variantData.listVariantValues) {
                    label = variant.variantLabel
                    name = variant.name

                    variantDetail += `${label}: ${name}, `
                }

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
}
</script>