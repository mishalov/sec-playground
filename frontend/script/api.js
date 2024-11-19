class API {
    url = 'http://localhost:8080/api';

    /**
     * 
     * @param { string } url 
     */
    constructor(url) {
        this.url = url;
    }

    setAuthentication(authentication) {
        this.authentication = authentication;
    }

    buildHeaders() {
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${this.authentication.getToken()}`
        }
    }

    async logIn(username, password) {
        const response = await fetch(`${this.url}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        return await response.json();
    }

    async fetchMe() {
        const response = await fetch(`${this.url}/me`, {
            method: "POST",
            headers: this.buildHeaders()
        });

        return await response.json();
    }

    async updateInfo({ secret, name }) {
        const response = await fetch(`${this.url}/user`, {
            method: 'PATCH',
            headers: this.buildHeaders(),
            body: JSON.stringify({ secret, name })
        });

        return await response.json();
    }

    async getUsers() {
        const response = await fetch(`${this.url}/users`, {
            method: 'GET',
            headers: this.buildHeaders()
        });

        return await response.json();
    }
}

export default API