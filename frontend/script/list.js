import debounce from "./utils/debounce";

class List {
    users = [];

    getSearchValue() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('search') || '';
    }

    constructor(api) {
        this.api = api;
        this.searchValue = this.getSearchValue();
    }

    async getUsers() {
        this.users = await this.api.getUsers(this.searchValue);
    }

    async init() {
        await this.getUsers();
        this.render();

        search.addEventListener('input', debounce(async () => {
            this.searchValue = encodeURIComponent(search.value);
            const urlParams = new URLSearchParams(window.location.search);
            urlParams.set('search', this.searchValue);


            window.history.replaceState(null, document.title, "/?" + urlParams.toString());
            await this.getUsers()
            this.renderList();
        }, 500))
    }

    async handleSearch() {
        const search = document.querySelector('#search');
        const searchLine = document.querySelector('#searchLine');
        if (!search) throw new Error('Search not found');
    }

    renderList() {
        const list = document.querySelector('#list');
        if (!list) throw new Error('List not found');

        const items = this.users.map(user => `
            <div class="userRow">
                <img class="avatarInList" alt="User's avatar" id="avatar" src='${user.avatar}'/>
                <span>${user.name}</span>
            </div>
        `).join('');

        list.innerHTML = items;
    }

    render() {
        const users = document.querySelector('#users');
        if (!users) throw new Error('Users not found');

        users.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title
                    ">Users</h5>
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="Search" aria-label="Recipient's username" id="search"
                                aria-describedby="basic-addon2">
                        </div>
                    <div id="list">
                    </div>
                </div>
            </div>
        `;

        this.renderList();
        document.querySelector('#search')?.addEventListener('input', this.handleSearch.bind(this));
    }
}

export default List