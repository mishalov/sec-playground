class List {
    users = [];
    constructor(api) {
        this.api = api;
    }

    async init() {
        this.users = await this.api.getUsers();
        this.render();
    }

    render() {
        const users = document.querySelector('#users');
        if (!users) throw new Error('Users not found');

        const items = this.users.map(user => `
            <div class="userRow">
                <img class="avatarInList" alt="User's avatar" id="avatar" src='${user.avatar}'/>
                <span>${user.name}</span>
            </div>
        `).join('');

        users.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title
                    ">Users</h5>
                    
                    <div>
                        ${items}
                        </div>
                        </div>
                        </div>
                        `;
    }
}

export default List