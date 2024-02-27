<template>
    <h1 class="text-center">SIGN UP</h1>

    <div class="alert alert-danger" role="alert" v-show="err" id="errMsg">
        {{ errMsg }}
    </div>

    <div class="alert alert-success" role="alert" v-show="success" id="errMsg">
        {{ successMsg }}
    </div>

    <form v-on:submit="signUp($event)">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" required v-model="formData.username">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" required v-model="formData.password">
        </div>
        <div class="mb-3">
            <label for="repeatPassword" class="form-label">Repeat password</label>
            <input type="password" class="form-control" id="repeatPassword" required v-model="repeatPassword">
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" aria-describedby="emailHelp" v-model="formData.email">
        </div>
        <div class="mb-3">
            <label for="firstName" class="form-label">First name</label>
            <input type="text" class="form-control" id="firstName" required v-model="formData.firstName">
        </div>
        <div class="mb-3">
            <label for="lastName" class="form-label">Last name</label>
            <input type="text" class="form-control" id="lastName" required v-model="formData.lastName">
        </div>
        <button type="submit" class="btn btn-primary">Sign Up</button>
    </form>
</template>

<script>
import { endpoints, userApi } from '../config/Api.js';

export default {
    data() {
        return {
            formData: {
                username: '',
                password: '',
                email: '',
                firstName: '',
                lastName: ''
            },
            repeatPassword: '',
            err: false,
            errMsg: '',
            success: false,
            successMsg: ''
        }
    },
    methods: {
        async signUp(event) {
            event.preventDefault();

            if (this.formData.password != this.repeatPassword) {
                this.success = false
                this.err = true
                this.errMsg = 'Repeat password does not match password'
                return
            }

            try {
                const res = await userApi.post(endpoints['signup'], this.formData);

                this.err = false;
                this.success = true;
                this.successMsg = res.data.message;
            } catch (error) {
                this.success = false;
                this.err = true;
                this.errMsg = error.response.data.message || 'INTERNAL SERVER ERROR';
            }
        }
    }
}
</script>