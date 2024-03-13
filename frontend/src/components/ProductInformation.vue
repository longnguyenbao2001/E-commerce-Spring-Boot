<template>
    <h1 class="text-left">PRODUCT INFORMATION</h1>

    <div class="row mb-4">
        <div class="col-6">
            <ProductImages />
        </div>
        <div class="col-4">
            <h3 class="text-left">{{ productDetail.name }}</h3>

            <ProductVariantList />

            <button type="button" class="btn btn-primary" @click="addToCart" :disabled="!isValidVariant">
                Add to cart
            </button>

            <p v-show="!isValidVariant">Please choose a valid variant</p>
        </div>
    </div>

    <div>
        {{ productDetail.description }}
    </div>
</template>

<script>
import ProductImages from './ProductImages.vue'
import ProductVariantList from './ProductVariantList.vue'
import { endpoints, productApi } from '../config/Api.js'

export default {
    name: 'ProductInformation',
    components: {
        ProductImages,
        ProductVariantList
    },
    data() {
        return {
            productDetail: {},
        }
    },
    async mounted() {
        await this.getProductDetail()
    },
    methods: {
        async getProductDetail() {
            try {
                const res = await productApi.get(endpoints['productDetail'](this.$route.params.productId));

                this.productDetail = res.data
                console.log(this.productDetail)
            } catch (error) {

            }
        },
        async addToCart() {
            const data = {
                'productId': this.productDetail.id,
                'productName': this.productDetail.name,
                'variantData': this.$store.state.currentVariant,
                'quantity': this.$store.state.currentQuantity
            }

            await this.$store.dispatch('addToCart', data)

            // this.$nextTick(() => {
            //     // Access the updated state after the next tick
            //     console.log(this.$store.state.isValidVariant);
            // });
        }
    },
    computed: {
        isValidVariant() {
            return this.$store.state.isValidVariant
        }
    },
}
</script>