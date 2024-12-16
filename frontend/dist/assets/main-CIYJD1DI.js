var u=Object.defineProperty;var h=(a,e,t)=>e in a?u(a,e,{enumerable:!0,configurable:!0,writable:!0,value:t}):a[e]=t;var c=(a,e,t)=>h(a,typeof e!="symbol"?e+"":e,t);import{g as o}from"./main-BUsHxNkt.js";class m{constructor(e,t){this.api=e,this.auth=t}getDetails(){return this.me}setDetails(e){this.me=e,localStorage.setItem("me",JSON.stringify(e))}toggleEditMode(){const e=document.querySelector("#secret-text"),t=document.querySelector("#secret-field"),s=document.querySelector("#name-field"),r=document.querySelector("#save-buttons"),n=document.querySelector("#edit"),i=document.querySelector("#name");i==null||i.classList.toggle("d-none"),s==null||s.classList.toggle("d-none"),r==null||r.classList.toggle("d-none"),t==null||t.classList.toggle("d-none"),e==null||e.classList.toggle("d-none"),n==null||n.classList.toggle("d-none")}async updateInfo(){var s,r;const e=(s=document.querySelector("#secret-field"))==null?void 0:s.value,t=(r=document.querySelector("#name-field"))==null?void 0:r.value;await this.api.updateInfo({secret:e,name:t}),this.auth.fetchMe()}render(){this.getDetails();const e=document.querySelector("#details");if(!e)throw new Error("Details not found");const{user:t}=this.me;console.log("user: ",t),e.innerHTML=`
           <div class="card">
                <img class="card-img-top" alt="User's avatar" id="avatar" src='${t.avatar}'/>
                    <div class="card-body">
                    <h5 class="card-title">
                        <span id="name">${t.name}</span>
                        <input class="form-control d-none" id="name-field" value="${t.name}"/>
                    </h5>
                    <p class="card-text">
                        <small class="text-muted" id="address">${t.address}</small> | 
                        <small class="text-muted id="phone">${t.phone}</small>
                    </p>
                    <h6>My secret is...</h6>
                    <div id="secret-text">
                        ${t.secret} 
                    </div>
                    <textarea class="form-control d-none" id="secret-field" rows="3">${t.secret}</textarea>
                </div>
                <div class="card-footer">
                    <button class="btn btn-primary" id="edit">Edit your info</button>
                <div class="btn-group d-none" role="group" aria-label="Basic example" id="save-buttons">
                    <button class="btn btn-primary" id="save">Save</button>
                    <button class="btn btn-secondary" id="cancel">Cancel</button>
                </div>
                </div>
     
            </div>
        `;const s=document.querySelector("#edit"),r=document.querySelector("#save"),n=document.querySelector("#cancel");n==null||n.addEventListener("click",()=>{this.toggleEditMode()}),r==null||r.addEventListener("click",()=>{this.updateInfo(),this.toggleEditMode()}),s==null||s.addEventListener("click",this.toggleEditMode.bind(this))}init(e){this.setDetails(e),this.render()}}const g=(a,e)=>{let t=null;return(...s)=>{window.clearTimeout(t),t=window.setTimeout(()=>{a(...s)},e)}};class v{constructor(e){c(this,"users",[]);this.api=e,this.searchValue=this.getSearchValue()}getSearchValue(){return new URLSearchParams(window.location.search).get("search")||""}async getUsers(){this.users=await this.api.getUsers(this.searchValue)}async init(){await this.getUsers(),this.render(),search.addEventListener("input",g(async()=>{this.searchValue=encodeURIComponent(search.value);const e=new URLSearchParams(window.location.search);e.set("search",this.searchValue),window.history.replaceState(null,document.title,"/?"+e.toString()),await this.getUsers(),this.renderList()},500))}async handleSearch(){const e=document.querySelector("#search");if(document.querySelector("#searchLine"),!e)throw new Error("Search not found")}renderList(){const e=document.querySelector("#list");if(!e)throw new Error("List not found");const t=this.users.map(s=>`
            <div class="userRow">
                <img class="avatarInList" alt="User's avatar" id="avatar" src='${s.avatar}'/>
                <span>${s.name}</span>
            </div>
        `).join("");e.innerHTML=t}render(){var t;const e=document.querySelector("#users");if(!e)throw new Error("Users not found");e.innerHTML=`
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
        `,this.renderList(),(t=document.querySelector("#search"))==null||t.addEventListener("input",this.handleSearch.bind(this))}}const d=o("auth"),l=o("api"),p=new m(l,d),y=new v(l);d.addEventListener("onAuthenticated",a=>{p.init(a),y.init()});
