import API from './script/api';
import Authentication from './script/auth'
import { saveToDocument } from './script/utils/globalStorage';
import './style.css'

// if localhost then localhost:8080 else vps-e1b58ab7.vps.ovh.net/api
const backendUrl = window.location.hostname === "localhost" ? 'http://localhost:8080/api' : 'http://vps-e1b58ab7.vps.ovh.net/api';
const api = new API(backendUrl);
const auth = new Authentication(api);
api.setAuthentication(auth);

auth.fetchMe();

saveToDocument('auth', auth);
saveToDocument('api', api);