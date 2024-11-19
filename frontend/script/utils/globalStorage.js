const globalObjects = ['auth', 'api']

const saveToDocument = (key, value) => {
    if (!globalObjects.includes(key)) throw new Error('You can only store global objects in the document');
    document[key] = value;
}

const getFromDocument = (key) => {
    if (!globalObjects.includes(key)) throw new Error('You can only store global objects in the document');
    return document[key];
}

export { saveToDocument, getFromDocument }