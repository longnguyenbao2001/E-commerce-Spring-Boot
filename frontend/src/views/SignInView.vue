<template>
    <div class="container">
        <h1 class="text-center">SIGN IN</h1>

        <div class="alert alert-danger" role="alert" v-if="err" id="errMsg">
            {{ errMsg }}
        </div>

        <form>
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" required v-model="formData.username">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" required v-model="formData.password">
            </div>
            <button type="submit" class="btn btn-primary" @click="signIn">Sign In</button>
        </form>
    </div>
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
        async signIn() {
            await userApi.post(endpoints['signin'], this.formData)
                .then(res => {
                    console.log(res)
                })
                .catch(err => {
                    this.err = true
                    this.errMsg = err.response.data.message
                })
        }
    }
}
</script>