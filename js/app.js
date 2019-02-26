//import Vue from 'vue';

const app = new Vue({
    el: '#app',

    data: {
        firstName: 'Raul',
        lastName: 'S',
        email: '',
        message: 'Welcome to GrowFast 1.0!'


    },

    computed: {
        fullName() {
            return this.firstName + ' ' + this.lastName;
        }
    },

    methods: {

    }
});
