<template>
    <h1 class="text-left">PRODUCT INFORMATION</h1>

    <div class="row mb-4">
        <div class="col">
            <ProductImages />
        </div>
        <div class="col">
            <h3 class="text-left">{{ productDetail.name }}</h3>
        </div>
    </div>

    <div>
        {{ productDetail.description }}
    </div>
</template>

<script>
import ProductImages from './ProductImages.vue'
import { endpoints, productApi } from '../config/Api.js'

export default {
    name: 'ProductInformation',
    components: {
        ProductImages
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
            } catch (error) {

            }
        },
    }
}
</script>