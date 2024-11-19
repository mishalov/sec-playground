import API from './script/api';
import Authentication from './script/auth'
import { saveToDocument } from './script/utils/globalStorage';
import './style.css'

const api = new API('http://localhost:8080');
const auth = new Authentication(api);
api.setAuthentication(auth);

auth.fetchMe();

saveToDocument('auth', auth);
saveToDocument('api', api);