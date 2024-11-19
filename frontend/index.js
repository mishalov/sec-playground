import "./main"
import Details from "./script/details";
import List from "./script/list";
import { getFromDocument } from "./script/utils/globalStorage";

const auth = getFromDocument('auth');
const api = getFromDocument('api');

const details = new Details(api, auth);
const list = new List(api);

auth.addEventListener('onAuthenticated', (me) => {
    details.init(me)
    list.init();
})