function loadPeople() {
    $ajax.get('/person')
        .then(people => {
            document.getElementById('result').textContent = people;
        });
}

function createPerson(event) {
    event.preventDefault();
    event.stopPropagation();
    $ajax.post('/person', {firstName: event.target.first_name.value, lastName: event.target.last_name.value});
}

let $ajax = {
    get(url) {
        return this.call('GET', url);
    },

    post(url, data) {
        return this.call('POST', url, data)
    },

    call(method, url, data) {
        return new Promise((resolve, reject) => {
            let xhr = new XMLHttpRequest();
            xhr.open(method, url);
            xhr.onload = () => {
                if (xhr.status < 400) {
                    resolve(xhr.responseText)
                } else {
                    reject(new Error(xhr.responseText))
                }
            };
            xhr.onerror = () => reject(new Error("Network error"));
            if(data) {
                xhr.send(JSON.stringify(data));
            } else {
                xhr.send()
            }
        });
    }
};