<template>
    <div class="mb-3">
        <div>
            <h3 v-show="!currentVariant">
                Price: {{ variantPriceRange[0] }}
                {{ variantPriceRange.length === 2 ? ' - ' + variantPriceRange[1] : '' }}
            </h3>
            <h3 v-if="currentVariant">Price: {{ currentVariant.unitPrice }}</h3>
        </div>

        <div class="mb-3" v-for="(names, label) in variantsMap">
            <div>
                <h5>{{ label }}</h5>
            </div>
            <div>
                <span class="p-2" v-for="name in names">
                    <button :class="isSelected(label, name) ? 'btn btn-dark' : 'btn btn-outline-dark'" type="button"
                        @click="selectVariantOption(label, name)"
                        :disabled="!isContained(label, name, possibleVariantsMap)">{{ name }}</button>
                </span>
            </div>
        </div>

        <div v-if="currentVariant">
            <div class="d-flex">
                <QuantityButtons :productData="productData" />

                <span>Available quantity: {{ currentVariant.quantity }}</span>
            </div>
        </div>

        <!-- <p v-show="!isValidQuantity">Please input a valid quantity</p> -->
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
import { endpoints, productApi } from '../config/Api.js'
import QuantityButtons from '../components/QuantityButtons.vue'

export default {
    name: 'ProductVariantList',
    components: {
        QuantityButtons
    },
    data() {
        return {
            productVariants: [],
            variantsMap: {},
            selectedVariantsMap: {},
            possibleVariantsMap: {},
            currentVariant: null,
            variantPriceRange: [],
            currentQuantity: 1,
            productData: null,
        }
    },
    async mounted() {
        await this.getProductVariants()
    },
    methods: {
        isContained(key, value, hashMap) {
            return hashMap[key].includes(value)
        },
        updateHashMap(key, value, hashMap) {
            if (hashMap.hasOwnProperty(key)) {
                if (!this.isContained(key, value, hashMap)) {
                    hashMap[key].push(value)
                }
            } else {
                hashMap[key] = [value]
            }
        },
        async getProductVariants() {
            try {
                const res = await productApi.get(endpoints['productVariants'](this.$route.params.productId))

                this.productVariants = res.data

                this.$store.dispatch('updateCurrentQuantity', 1)
                this.initVariantsMap()
                this.initPossibleVariantsMap()
            } catch (error) {

            }
        },
        selectVariantOption(label, name) {
            if (this.selectedVariantsMap[label] !== name) {
                this.selectedVariantsMap[label] = name
            }
            else {
                delete this.selectedVariantsMap[label]
            }

            this.updatePossibleVariantsMap()
            this.currentVariant = this.getCurrentVariant()

            if (this.currentVariant !== null) {
                this.currentQuantity = 1
                this.$store.dispatch('updateCurrentQuantity', this.currentQuantity)
                this.$store.dispatch('updateValidVariantStatus', true)
                this.$store.dispatch('updateCurrentVariant', this.currentVariant)

                const data = {
                    'productId': this.$route.params.productId,
                    'variantData': this.currentVariant,
                    'quantity': this.currentQuantity
                }
                this.productData = data
            } else {
                this.$store.dispatch('updateValidVariantStatus', false)
                this.$store.dispatch('updateCurrentVariant', null)

                this.productData = null
            }
        },
        isSelected(label, name) {
            return this.selectedVariantsMap[label] === name
        },
        getCurrentVariant() {
            let check = false

            for (const variant of this.productVariants) {
                for (const variantData of variant.listVariantValues) {
                    const label = variantData.variantLabel
                    const name = variantData.name

                    if (this.selectedVariantsMap[label] === undefined || this.selectedVariantsMap[label] === null) {
                        return null
                    }

                    if (this.selectedVariantsMap[label] !== name) {
                        check = false
                        break
                    }
                    else {
                        check = true
                    }
                }

                if (check) {
                    return variant
                }
            }

            return null
        },
        initVariantsMap() {
            let price = 0

            if (this.productVariants.length === 1) {
                this.currentVariant = this.productVariants[0]
                this.$store.dispatch('updateCurrentVariant', this.currentVariant)
                this.$store.dispatch('updateValidVariantStatus', this.currentVariant.quantity > 0)

                const data = {
                    'productId': this.$route.params.productId,
                    'variantData': this.currentVariant,
                    'quantity': this.currentQuantity
                }
                this.productData = data
            } else {
                for (const variant of this.productVariants) {
                    price = variant.unitPrice

                    if (this.variantPriceRange.length <= 0) {
                        this.variantPriceRange[0] = price
                        this.variantPriceRange[1] = price
                    } else {
                        if (price > this.variantPriceRange[1]) {
                            this.variantPriceRange[1] = price
                        }
                        else if (price < this.variantPriceRange[1]) {
                            this.variantPriceRange[0] = price
                        }
                    }

                    for (const variantData of variant.listVariantValues) {
                        this.updateHashMap(variantData.variantLabel, variantData.name, this.variantsMap)
                    }
                }

                this.currentVariant = null
                this.$store.dispatch('updateCurrentVariant', null)
                this.$store.dispatch('updateValidVariantStatus', false)
            }

            if (this.variantPriceRange[0] === this.variantPriceRange[1]) {
                this.variantPriceRange.splice(1, 2);
            }
        },
        initPossibleVariantsMap() {
            for (const variant of this.productVariants) {
                if (variant.quantity > 0) {
                    for (const variantData of variant.listVariantValues) {
                        this.updateHashMap(variantData.variantLabel, variantData.name, this.possibleVariantsMap)
                    }
                }
            }
        },
        updatePossibleVariantsMap() {
            let isPossibleVariant = true
            this.possibleVariantsMap = []

            for (const variant of this.productVariants) {
                isPossibleVariant = true

                if (variant.quantity > 0) {
                    for (const variantData of variant.listVariantValues) {
                        const label = variantData.variantLabel
                        const name = variantData.name

                        if (this.selectedVariantsMap[label] !== undefined) {
                            if (this.selectedVariantsMap[label] !== name) {
                                isPossibleVariant = false
                                break
                            }
                        }
                    }
                }
                else {
                    isPossibleVariant = false
                }

                if (isPossibleVariant) {
                    for (const variantData of variant.listVariantValues) {
                        this.updateHashMap(variantData.variantLabel, variantData.name, this.possibleVariantsMap)
                    }
                }
            }
        },
        // increaseCurrentQuantity() {
        //     if (this.currentQuantity < this.currentVariant.quantity) {
        //         this.$store.dispatch('updateCurrentQuantity', ++this.currentQuantity)
        //         this.$store.dispatch('updateValidQuantityStatus', true)
        //     }
        // },
        // decreaseCurrentQuantity() {
        //     if (this.currentQuantity > 1) {
        //         this.$store.dispatch('updateCurrentQuantity', --this.currentQuantity)
        //         this.$store.dispatch('updateValidQuantityStatus', true)
        //     }
        // },
        // updateCurrentQuantity(event) {
        //     const newValue = parseInt(event.target.value)

        //     if (!isNaN(newValue) && newValue >= 1 && newValue <= this.currentVariant.quantity) {
        //         this.currentQuantity = newValue
        //         this.$store.dispatch('updateCurrentQuantity', this.currentQuantity)
        //         this.$store.dispatch('updateValidQuantityStatus', true)
        //     } else {
        //         this.currentQuantity = 1
        //         this.$store.dispatch('updateCurrentQuantity', this.currentQuantity)
        //         this.$store.dispatch('updateValidQuantityStatus', false)
        //     }
        // }
    },
    // computed: {
    //     isValidQuantity() {
    //         return this.$store.state.isValidQuantity
    //     }
    // },
}
</script>