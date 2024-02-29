<template>
    <div class="card" style="width: 18rem;">
        <img :src="product.imageUrl" class="card-img-top" alt="img">
        <div class="card-body">
            <h5 class="card-title">{{ product.name }}</h5>
            <p class="card-text">{{ product.unitPrice }}</p>
            <div class="btn btn-primary">
                <router-link :to="{ path: `/products/${product.id}` }" class="nav-link">Detail</router-link>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ProductItem',
    props: ['product']
}
</script>