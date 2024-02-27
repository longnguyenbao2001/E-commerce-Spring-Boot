<!-- Sign In component (SignIn.vue) -->
<template>
    <h1 class="text-center">SIGN IN</h1>

    <div class="alert alert-danger" role="alert" v-if="err" id="errMsg">
        {{ errMsg }}
    </div>

    <form v-on:submit="signIn($event)">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" required v-model="formData.username">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" required v-model="formData.password">
        </div>
        <button type="submit" class="btn btn-primary">Sign In</button>
    </form>
</template>

<script>
import { endpoints, userApi } from '../config/Api.js';

export default {
    data() {
        return {
            formData: {
                username: '',
                password: ''
            },
            err: false,
            errMsg: ''
        }
    },
    methods: {
        async signIn(event) {
            event.preventDefault();

            await userApi.post(endpoints['signin'], this.formData)
                .then(res => {
                    localStorage.setItem('accessToken', res.data.jwtAccessToken);
                    localStorage.setItem('userData', JSON.stringify(res.data.userData));

                    this.$store.dispatch('updateAuthenticationStatus', true);

                    console.log(JSON.parse(localStorage.userData));

                    this.$router.push({ name: 'home' });
                })
                .catch(err => {
                    this.err = true;
                    this.errMsg = err.response.data.message;
                });
        }
    }
}
</script>
  