function getPosts() {
    console.log("start");
    const ajax = new XMLHttpRequest();
    ajax.open("GET", "https://jsonplaceholder.typicode.com/posts");
    ajax.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log("dane odebrane");
            const posts = JSON.parse(this.response);
            let div = document.getElementById("rates");
            for (post of posts) {  // pętka forech
                div.innerHTML += `<p><strong>${post.title}</strong>:<br>${post.body} </p>`;
            }
        }
    }
    ajax.send();
}

function getRates(){
    fetch("http://api.nbp.pl/api/exchangerates/tables/A?format=json")
        .then(response => response.json())
        .then(response => {
            let div = document.getElementById("rates");
            div.innerHTML = "";
            for (rate of response[0].rates){
                div.innerHTML += `<p>${rate.currency}: (${rate.code}) ${rate.mid}</p>`;
            }
        })
        .catch(a => console.log("Odrzucone połączenie" + a));
}