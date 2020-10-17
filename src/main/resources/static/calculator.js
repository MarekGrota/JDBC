function add(){
    const a = document.getElementById("numberA");
    const b = document.getElementById("numberB");
    const result = document.getElementById("result");

    result.value = Number(a.value) + Number(b.value);
}

function mul(){
    const a = document.getElementById("numberA");
    const b = document.getElementById("numberB");
    const result = document.getElementById("result");

    result.value = Number(a.value) * Number(b.value);
}

function div(){
    const a = document.getElementById("numberA");
    const b = document.getElementById("numberB");
    const result = document.getElementById("result");

    result.value = Number(a.value) / Number(b.value);
}

function power(){
    const a = document.getElementById("numberA");
    const b = document.getElementById("numberB");
    const result = document.getElementById("result");

    result.value = Number(a.value) ** Number(b.value);
}