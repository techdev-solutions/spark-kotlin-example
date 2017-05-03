function loadPeople() {
    $ajax.get('/person')
        .then(people => {
            document.getElementById('result').textContent = people;
        });
}

let $ajax = {
    get(url) {
        return this.call('GET', url);
    },

    call(method, url) {
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
            xhr.send();
        });
    }
};