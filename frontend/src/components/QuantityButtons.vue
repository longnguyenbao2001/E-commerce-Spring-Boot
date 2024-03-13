<template>
    <div class="input-group me-2" style="width: 100px;">
        <div class="input-group-btn">
            <button class="btn btn-sm btn-primary btn-minus" @click="updateQuantity(-1)">
                <i class="fa fa-minus"></i>
            </button>
        </div>
        <input type="number" class="form-control form-control-sm bg-light text-center" disabled
            :value="currentQuantity" />
        <div class="input-group-btn">
            <button class="btn btn-sm btn-primary btn-plus" @click="updateQuantity(1)">
                <i class="fa fa-plus"></i>
            </button>
        </div>
    </div>
</template>

<style scoped>
input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
</style>

<script>
export default {
    name: 'QuantityButtons',
    props: ['productData'],
    data() {
        return {
            currentQuantity: this.productData.quantity,
        }
    },
    methods: {
        updateQuantity(delta) {
            const newQuantity = this.currentQuantity + delta
            if (newQuantity >= 1 && newQuantity <= this.productData.variantData.quantity) {
                this.currentQuantity = newQuantity

                const routeName = this.$route.name
                if (routeName == 'myCart') {
                    const data = {
                        'productId': this.productData.productId,
                        'variantId': this.productData.variantData.id,
                        'quantity': this.currentQuantity
                    }

                    this.$store.dispatch('updateCartItemQuantity', data)

                    this.$emit('quantityUpdated', true)
                }

                else if (routeName == 'productDetail') {
                    this.$store.dispatch('updateCurrentQuantity', this.currentQuantity)
                }
            }
        },
        // increaseQuantity() {
        //     if (this.currentQuantity < this.productData.variantData.quantity) {
        //         this.currentQuantity += 1

        //         const routeName = this.$route.name
        //         if (routeName == 'myCart') {
        //             const data = {
        //                 'productId': this.productData.productId,
        //                 'variantId': this.productData.variantData.id,
        //                 'quantity': this.currentQuantity
        //             }

        //             this.$store.dispatch('updateCartItemQuantity', data)
        //         }

        //         else if (routeName == 'productDetail') {
        //             this.$store.dispatch('updateCurrentQuantity', this.currentQuantity)
        //         }
        //     }
        // },
    }
}
</script>