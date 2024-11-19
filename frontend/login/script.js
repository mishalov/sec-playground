import '../main'
import { getFromDocument } from '../script/utils/globalStorage'

const applyHandlers = () => {
    const form = document.querySelector('form')
    const auth = getFromDocument('auth')

    if (!form) throw new Error('Form not found')

    form.addEventListener('submit', async (e) => {
        e.preventDefault()
        const username = document.getElementById('username')?.value;
        const password = document.getElementById('password')?.value;

        auth.logIn(username, password)
    })
}

applyHandlers()