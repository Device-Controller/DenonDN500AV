parseURLId(location.href);
let id;


function parseURLId(url) {
    let startIndex = url.indexOf("?") + 1;
    let endIndex = url.length + 1;
    return parseId(url.slice(startIndex, endIndex - 1));

}

function parseId(id) {
    let startIndex = id.indexOf("=") + 1;
    let endIndex = id.length + 1;
    return id.slice(startIndex, endIndex - 1);
}

document.getElementById("muteList").onchange = function () {
    fetch('api/DenonDN500AV/' + this.value + '?id=' + parseURLId(location.href));
};
document.getElementById("power-list").onchange = function() {
    fetch('api/DenonDN500AV/power' + this.value + '?id=' + parseURLId(location.href));
    console.log(this.value);
};


document.querySelector('#volume-value').addEventListener('keypress', function (e) {
    var key = e.which || e.keyCode;
    if (key === 13) {
        setVolume(document.querySelector('#volume-value'));
        document.querySelector('#volume').value = document.querySelector('#volume-value').value;
    }
});

function setVolume(number) {
    fetch('api/DenonDN500AV/setVolume?id=' + parseURLId(location.href) + '&value=' + number.value).then(response => {
        if (response.ok) {
            console.log(number.value);
            console.log(response);
            response.json().then(e => document.getElementById("volume-value").innerHTML = e);
        }
    });
}

function setValue(element1, element2) {
    element1.value = document.getElementById(element2).value;
    setVolume(element2.value);
}
document.getElementById("source-list").onchange = function() {
    fetch('api/DenonDN500AV/setSource?id=' + parseURLId(location.href) + '&value=' + this.value).then(r=>{
        if(r.ok){
            r.text().then(t=> document.getElementById("get-source").innerHTML = t);
        }
    })
};

function populateDropdown() {
    let dropdown = document.getElementById("source-list");
    fetch('api/DenonDN500AV/getSources?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => {
                for (let i = 0; i < e.length; i++) {
                    let option = document.createElement("option");
                    option.text = e[i];
                    option.value = e[i];
                    dropdown.add(option, 99);
                }
            });
        }
    });
}


function updateData() {
    populateDropdown();
    //GET DEVICE
    fetch('api/DenonDN500AV/getMute?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(p => {
                console.log(p);
                    document.getElementById("mute-state").innerHTML = "" + p.response;

            });
        }
    });

    fetch('api/DenonDN500AV/getPower?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(p => {
                console.log(p);
                    document.getElementById("power-setting").innerHTML = "" + p.response;
            });
        }
    });
    //GET VOLUME

    fetch('api/DenonDN500AV/getVolume?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.text().then(e => {
                console.log(e);
                document.getElementById("volume").value = e;
                document.getElementById("volume-value").value = e;
            });
        }
    });

    fetch('api/DenonDN500AV/getSource?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            console.log(response);
            response.text().then(e => {
                console.log(e);
                document.getElementById("get-source").innerHTML = e;
            });
        }
    });
}

updateData();