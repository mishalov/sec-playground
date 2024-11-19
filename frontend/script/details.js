import API from "./api";

class Details {
    /**
     * 
     * @param { API } api 
     */
    constructor(api, auth) {
        this.api = api;
        this.auth = auth;
    }

    getDetails() {
        return this.me;
    }

    setDetails(me) {
        this.me = me;
        localStorage.setItem('me', JSON.stringify(me));
    }

    toggleEditMode() {
        const secretText = document.querySelector('#secret-text');
        const secretEdit = document.querySelector('#secret-field');
        const nameField = document.querySelector('#name-field');
        const editButtons = document.querySelector('#save-buttons');
        const editButton = document.querySelector('#edit');
        const name = document.querySelector('#name');


        name?.classList.toggle('d-none');
        nameField?.classList.toggle('d-none');
        editButtons?.classList.toggle('d-none');
        secretEdit?.classList.toggle('d-none');
        secretText?.classList.toggle('d-none');
        editButton?.classList.toggle('d-none');
    }

    async updateInfo() {
        const secret = document.querySelector('#secret-field')?.value;
        const name = document.querySelector('#name-field')?.value;
        await this.api.updateInfo({ secret, name });
        this.auth.fetchMe();
    }

    render() {
        const me = this.getDetails();
        const details = document.querySelector('#details');
        if (!details) throw new Error('Details not found');

        const { user } = this.me;
        console.log('user: ', user);

        details.innerHTML = `
           <div class="card">
                <img class="card-img-top" alt="User's avatar" id="avatar" src='${user.avatar}'/>
                    <div class="card-body">
                    <h5 class="card-title">
                        <span id="name">${user.name}</span>
                        <input class="form-control d-none" id="name-field" value="${user.name}"/>
                    </h5>
                    <p class="card-text">
                        <small class="text-muted" id="address">${user.address}</small> | 
                        <small class="text-muted id="phone">${user.phone}</small>
                    </p>
                    <h6>My secret is...</h6>
                    <div id="secret-text">
                        ${user.secret} 
                    </div>
                    <textarea class="form-control d-none" id="secret-field" rows="3">${user.secret}</textarea>
                </div>
                <div class="card-footer">
                    <button class="btn btn-primary" id="edit">Edit your info</button>
                <div class="btn-group d-none" role="group" aria-label="Basic example" id="save-buttons">
                    <button class="btn btn-primary" id="save">Save</button>
                    <button class="btn btn-secondary" id="cancel">Cancel</button>
                </div>
                </div>
     
            </div>
        `;

        const editButton = document.querySelector('#edit');
        const saveButton = document.querySelector('#save');
        const cancelButton = document.querySelector('#cancel');

        cancelButton?.addEventListener('click', () => {
            this.toggleEditMode();
        })


        saveButton?.addEventListener('click', () => {
            this.updateInfo();
            this.toggleEditMode();
        })
        editButton?.addEventListener('click', this.toggleEditMode.bind(this));
    }


    init(me) {
        this.setDetails(me);
        this.render();
    }
}

export default Details