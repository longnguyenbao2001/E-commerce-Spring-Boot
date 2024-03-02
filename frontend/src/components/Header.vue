<template>
  <nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
      <div class="collapse navbar-collapse" id="navbarLeftContent">
        <img src="../assets/logo.png" alt="" width="3%">

        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link :to="{ path: '/' }" class="nav-link active" aria-current="page">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{ path: '/about' }" class="nav-link">About</router-link>
          </li>
        </ul>
      </div>

      <div class="collapse navbar-collapse justify-content-end" id="navbarRightContent">
        <ul class="navbar-nav">
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link :to="{ path: '/signin' }" class="nav-link">Sign In</router-link>
          </li>
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link :to="{ path: '/signup' }" class="nav-link">Sign Up</router-link>
          </li>
          <li class="nav-item" v-else>
            <a class="nav-link" href="#" @click="signOut">Sign out</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
      <div class="collapse navbar-collapse" id="navbarLeftContent">
        <form class="d-flex">
          <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
      </div>

      <div class="collapse navbar-collapse justify-content-end" id="navbarRightContent">
        <ul class="navbar-nav">
          <li class="nav-item me-2">
            <router-link :to="{ path: '/cart' }" class="nav-link">
              <i class="fa fa-fw fa-cart-arrow-down text-dark mr-1"></i>
              <span class="position-relative top-0 left-100 translate-middle badge rounded-pill bg-light text-dark">
                {{ getCartCount() }}
              </span>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Header',
  computed: {
    isAuthenticated() {
      return this.$store.state.isAuthenticated
    }
  },
  methods: {
    signOut() {
      localStorage.removeItem('accessToken')
      localStorage.removeItem('userData')

      this.$store.dispatch('updateAuthenticationStatus', false)
    },
    getCartCount() {
      let res = 0

      for (const item of this.$store.state.cart) {
        res += item.quantity
      }

      return res
    }
  }
}
</script>
