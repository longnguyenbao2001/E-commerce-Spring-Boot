<template>
    <h1 class="text-center">PRODUCTS</h1>

    <div class="row mb-4" v-for="row in Math.ceil(products.length / rowSize)">
        <div class="col" v-for="col in rowSize">
            <ProductItem v-if="isValidIndex(row, col)" :product="products[getIndex(row, col)]" />
        </div>
    </div>

    <Pagination :currPage="currPage" :pageCount="pageCount" @pagechanged="updatePage" />
</template>

<script>
import ProductItem from './ProductItem.vue'
import Pagination from './Pagination.vue'
import { endpoints, productApi } from '../config/Api.js'

export default {
    name: 'ProductList',
    components: {
        ProductItem,
        Pagination,
    },
    data() {
        return {
            rowSize: 4,
            currPage: 1,
            pageCount: 0,
            pageSize: 1,
            products: [],
        }
    },
    async mounted() {
        await this.getProducts()
    },
    watch: {
        currPage: {
            handler: 'getProducts',
            immediate: false
        }
    },
    methods: {
        async getProducts() {
            try {
                const res = await productApi.get(endpoints['listproduct'], {
                    params: {
                        page: this.currPage,
                        pageSize: this.pageSize
                    }
                });

                this.products = res.data.listProducts
                this.pageCount = Math.ceil(res.data.totalCount / this.pageSize)
            } catch (error) {
                // this.success = false;
                // this.err = true;
                // this.errMsg = error.response.data.message || 'INTERNAL SERVER ERROR';
            }
        },
        isValidIndex(row, col) {
            return (row - 1) * this.rowSize + col - 1 < this.products.length
        },
        getIndex(row, col) {
            return (row - 1) * this.rowSize + col - 1
        },
        updatePage(page) {
            this.currPage = page
        }
    }
}
</script>