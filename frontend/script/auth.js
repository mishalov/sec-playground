import API from "./api";
import { saveToDocument } from "./utils/globalStorage";

class Authentication {
    /**
     * 
     * @param { API } api 
     */
    constructor(api) {
        this.api = api;
    }

    addEventListener(event, callback) {
        this[event] = callback;
    }

    redirectIfNotAuthenticated() {
        if (!window.location.pathname.includes('/login')) {
            window.location.href = '/login/';
        }
    }

    redirectIfAuthenticated() {
        if (window.location.pathname.includes('/login')) {
            window.location.href = '/';
        }
    }

    async fetchMe() {
        const token = localStorage.getItem('token');
        if (!token) {
            this.redirectIfNotAuthenticated();
            return
        }

        let me;

        try {
            me = await this.api.fetchMe();
            if (!me?.user) {
                this.onNotAuthenticated?.()
                return this.redirectIfNotAuthenticated();
            }
        } catch (error) {
            console.log('error: ', error);
            this.onNotAuthenticated?.()
            return this.redirectIfNotAuthenticated();

        }
        this.onAuthenticated?.(me)
        this.redirectIfAuthenticated?.()
    }


    async logIn(username, password) {
        const { token } = await this.api.logIn(username, password);
        this.saveToken(token);
        this.fetchMe();
    }

    getToken() {
        return localStorage.getItem('token');
    }

    saveToken(token) {
        localStorage
            .setItem('token', token);
    }
}

export default Authentication;