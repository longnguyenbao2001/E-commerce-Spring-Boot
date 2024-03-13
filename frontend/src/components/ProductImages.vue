<template>
    <div class="carousel slide" id="product-carousel" data-bs-wrap="true" data-bs-ride="carousel">
        <div class="carousel-inner">
            <div v-for="(image, index) in images" :key="index" class="carousel-item" :class="{ active: index === 0 }">
                <img :src="image" class="d-block w-100" alt="image">
            </div>
        </div>

        <!-- <button class="carousel-control-prev" type="button" data-bs-target="#product-carousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon"></span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#product-carousel" data-bs-slide="next">
            <span class="carousel-control-next-icon"></span>
        </button> -->

        <!-- <div class="carousel-indicators">
            <button v-for="(image, index) in images" :key="index" type="button" data-bs-target="#product-carousel"
                :data-bs-slide-to="index" :class="{ active: index === 0 }">
                <img :src="image" />
            </button>
        </div> -->

        <div class="carousel-indicators">
            <div class="row">
                <div class="col">
                    <button @click="prevImages" :disabled="currentIndex === 0" class="btn btn-primary">
                        <i class="fa-sharp fa-solid fa-caret-left"></i>
                    </button>
                </div>
                <div class="col">
                    <div class="carousel-indicators">
                        <button v-for="(image, index) in visibleImages" :key="index"
                            :class="{ active: index === currentIndex }" data-bs-target="#product-carousel"
                            :data-bs-slide-to="index + 3 * offset">
                            <img :src="image" :alt="'Slider Image ' + index" style="width: 100px;">
                        </button>
                    </div>
                </div>
                <div class="col">
                    <button @click="nextImages" :disabled="currentIndex >= images.length - 3" class="btn btn-primary">
                        <i class="fa-solid fa-caret-right"></i>
                    </button>
                </div>
            </div>
        </div>

    </div>
</template>

<style scoped>
.carousel-indicators img {
    width: 70px;
    display: block;
}

.carousel-indicators button {
    width: max-content !important;
}

.carousel-indicators {
    position: unset;
}
</style>

<script>
export default {
    name: 'ProductImages',
    data() {
        return {
            images: [
                'https://picsum.photos/id/1/5000/3333',
                'https://picsum.photos/id/2/5000/3333',
                'https://picsum.photos/id/3/5000/3333',
                'https://picsum.photos/id/4/5000/3333',
                'https://picsum.photos/id/5/5000/3333',
                'https://picsum.photos/id/6/5000/3333',
                'https://picsum.photos/id/7/5000/3333',
                'https://picsum.photos/id/8/5000/3333',
                'https://picsum.photos/id/9/5000/3333'
            ],
            currentIndex: 0,
            offset: 0,
        }
    },
    computed: {
        visibleImages() {
            const endIndex = Math.min(this.currentIndex + 3, this.images.length)
            return this.images.slice(this.currentIndex, endIndex)
        }
    },
    methods: {
        nextImages() {
            if (this.currentIndex < this.images.length - 3) {
                this.offset += 1
                this.currentIndex += 3
            }
        },
        prevImages() {
            if (this.currentIndex > 0) {
                this.offset -= 1
                this.currentIndex -= 3
            }
        }
    }
};
</script>