//import Vue from 'vue';

const app = new Vue({
    el: '#app',

    data: {
        firstName: 'Raul',
        lastName: 'S',
        email: '',
        message: 'GrowFast 1.0: A new era of room-scale farming'


    },

    computed: {
        fullName() {
            return this.firstName + ' ' + this.lastName;
        }
    },

    methods: {

    }
});
