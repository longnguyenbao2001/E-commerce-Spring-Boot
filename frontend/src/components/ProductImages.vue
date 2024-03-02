<template>
    <div class="carousel slide" id="carouselDemo" data-bs-wrap="true" data-bs-ride="carousel">

        <div class="carousel-inner">

            <div class="carousel-item active">
                <img src="https://picsum.photos/id/1/5000/3333" class="w-100">
                <div class="carousel-caption">
                    <h5>Title Slide 0</h5>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Modi, nemo?
                    </p>
                </div>
            </div>

            <div class="carousel-item " data-bs-interval="2000">
                <img src="https://picsum.photos/id/2/5000/3333" class="w-100">
                <div class="carousel-caption">
                    <h5>Title Slide 1</h5>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Modi, nemo?
                    </p>
                </div>
            </div>

            <div class="carousel-item ">
                <img src="https://picsum.photos/id/3/5000/3333" class="w-100">
                <div class="carousel-caption">
                    <h5>Title Slide 2</h5>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Modi, nemo?
                    </p>
                </div>
            </div>

            <div class="carousel-item ">
                <img src="https://picsum.photos/id/4/5000/3333" class="w-100">
                <div class="carousel-caption">
                    <h5>Title Slide 2</h5>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Modi, nemo?
                    </p>
                </div>
            </div>
        </div>

        <button class="carousel-control-prev" type="button" data-bs-target="#carouselDemo" data-bs-slide="prev">
            <span class="carousel-control-prev-icon"></span>
        </button>

        <button class="carousel-control-next" type="button" data-bs-target="#carouselDemo" data-bs-slide="next">
            <span class="carousel-control-next-icon"></span>
        </button>

        <div class="carousel-indicators">
            <button type="button" class="active" data-bs-target="#carouselDemo" data-bs-slide-to="0">
                <img src="https://picsum.photos/id/1/5000/3333" />
            </button>

            <button type="button" data-bs-target="#carouselDemo" data-bs-slide-to="1">
                <img src="https://picsum.photos/id/2/5000/3333" />
            </button>

            <button type="button" data-bs-target="#carouselDemo" data-bs-slide-to="2">
                <img src="https://picsum.photos/id/3/5000/3333" />
            </button>

            <button type="button" data-bs-target="#carouselDemo" data-bs-slide-to="3">
                <img src="https://picsum.photos/id/4/5000/3333" />
            </button>

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
}
</script>