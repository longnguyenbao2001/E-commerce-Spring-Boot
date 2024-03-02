<template>
    <div class="card" style="width: 16rem;">
        <div class="card-header">
            <img :src="product.imageUrl" class="card-img-top" alt="img"
                onerror="this.src='https://picsum.photos/id/2/5000/3333';">
        </div>

        <div class="card-body">
            <h5 class="card-title">{{ product.name }}</h5>
            <p class="card-text">{{ product.unitPrice }}</p>
        </div>

        <div class="card-footer">
            <div class="btn btn-primary">
                <router-link :to="{ path: `/products/${product.id}` }" class="nav-link">Detail</router-link>
            </div>
        </div>
    </div>
</template>

<style scoped>
img {
    width: 14rem;
    height: 10rem;
    margin: auto;
    margin-top: 1rem;
}
</style>

<script>
export default {
    name: 'ProductItem',
    props: ['product']
}
</script>