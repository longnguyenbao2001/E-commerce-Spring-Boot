<template>
    <nav aria-label="Page navigation example" v-if="pageCount > 1">
        <ul class="pagination">
            <li class="page-item" v-if="currPage > 1">
                <a class="page-link" href="#" @click="changePage(1)">First</a>
            </li>
            <li :class="currPage == page ? 'page-item active' : 'page-item'" v-for="page in pageRange">
                <a class="page-link" v-if="currPage == page">{{ page }}</a>
                <a class="page-link" href="#" v-else @click="changePage(page)">{{ page }}</a>
            </li>
            <li class="page-item" v-if="currPage < pageCount">
                <a class="page-link" href="#" @click="changePage(pageCount)">Last</a>
            </li>
        </ul>
    </nav>
</template>

<script>
export default {
    name: 'Pagination',
    props: ['currPage', 'pageCount'],
    data() {
        return {
            pageRange: [],
        }
    },
    methods: {
        changePage(page) {
            this.$emit('pagechanged', page)
        },
        paginate() {
            let size = 0
            this.pageRange = []
            for (let i = this.currPage - 2; i <= this.currPage + 2; i++) {
                if (i >= 1 && i <= this.pageCount) {
                    this.pageRange[size++] = i
                }
            }
        }
    },
    watch: {
        currPage() {
            this.paginate()
        },
        pageCount() {
            this.paginate()
        }
    },
    mounted() {
        this.paginate()
    }
}
</script>