if (sessionStorage.getItem('orderList') == null) {
    sessionStorage.setItem('orderList', []);
}

window.onload = function() {
    if (sessionStorage.getItem('orderList') != []) {
        drawOrdersTable(JSON.parse(sessionStorage.getItem('orderList')));
    }
}

let payOrder = () => {

}

let checkQuantity = () => {
    let ordList;
    if (sessionStorage.getItem('orderList') != []) {
        ordList = JSON.parse(sessionStorage.getItem('orderList'));
    }
    else {
        ordList = [];
    }

    console.log(ordList);
    
    for (let i = 0; i < ordList.length; i++) {

        // let name = ordList[i].name;
        // let url = `http://localhost:8080/flowerbyname?`;
        // if (name != null) url += `&name=${name}`;
        // let xhr = new XMLHttpRequest();
        // xhr.open("GET", url, false);
        // xhr.send();
        // let res;
        // res = JSON.parse(xhr.responseText);

    }
}

let drawTable = (list) => {
    let table = document.getElementById("tbody");

    while (table.firstChild) {
        table.removeChild(table.firstChild);
    }

    for (let i = 0; i < list.length; i++) {
        let tr1 = document.createElement('tr');
        if (i == 0) table.appendChild(tr1);
        let tr = document.createElement('tr');
        table.appendChild(tr);

        for (key in list[i]) {
            if (i == 0) {
                let td1 = document.createElement('td');
                tr1.appendChild(td1);
                td1.innerHTML = key.charAt(0).toUpperCase() + key.slice(1);
            }
            let td = document.createElement('td');
            tr.appendChild(td);
            td.innerHTML = list[i][key];
        }

        for(let g = 0; g < 2; g++) {
            if (g == 0) {
                let td = document.createElement('td');
                let inpt = document.createElement('input');
                inpt.style.width = "40px";
                inpt.type = "number";
                inpt.value = "1";
                inpt.min = "1";
                inpt.max = tr.getElementsByTagName('td')[2].innerHTML;
                td.appendChild(inpt);
                tr.appendChild(td);
            }
            else {
                let td = document.createElement('td');
                tr.appendChild(td);
                let btn = document.createElement('button');
                btn.innerHTML = "Add"
                td.appendChild(btn);
            }
        }
    }
}

let drawOrdersTable = (list) => {
    let table = document.getElementById("otbody");
    let tableDiv = document.getElementById('ordersTable');

    while (table.firstChild) {
        table.removeChild(table.firstChild);
    }
    
    if (tableDiv.firstElementChild.tagName != 'SPAN') {
        let span = document.createElement('span');
        span.innerHTML = "Basket Table";
        table.parentNode.parentNode.insertBefore(span,table.parentNode);
    }

    for (let i = 0; i < list.length; i++) {
        let tr1 = document.createElement('tr');
        if (i == 0) table.appendChild(tr1);
        let tr = document.createElement('tr');
        table.appendChild(tr);

        for (key in list[i]) {
            if (i == 0) {
                let td1 = document.createElement('td');
                tr1.appendChild(td1);
                td1.innerHTML = key.charAt(0).toUpperCase() + key.slice(1);
            }
            let td = document.createElement('td');
            tr.appendChild(td);
            td.innerHTML = list[i][key];
        }

        for (let g = 0; g < 1; g++) {
            let td = document.createElement('td');
            tr.appendChild(td);
            let btn = document.createElement('button');
            btn.innerHTML = "Del";
            td.appendChild(btn);
        }
    }

    for (let i = 0; i < 1; i++) {
        let tr = document.createElement('tr');
        table.appendChild(tr);

        for (let g = 0; g < 4; g++) {
            if (g == 3) {
                let td = document.createElement('td');
                let temp = 0;

                for (let k = 0; k < list.length; k++) {
                    temp += list[k].sum;
                }
                
                let discount = JSON.parse(sessionStorage.getItem('clientInfo')).discount / 100;
                temp = temp - ( temp * discount);
                td.innerHTML = temp;
                tr.appendChild(td);
            }
            else {
                let td = document.createElement('td');
                td.style.visibility = "hidden";
                tr.appendChild(td);
            }
        }
    }
}

let toOrders = () => {
    let table = document.getElementById("otbody");
    let tableDiv = document.getElementById('ordersTable');

    while (table.firstChild) {
        table.removeChild(table.firstChild);
    }

    sessionStorage.orderList = [];
    document.getElementById("curItems").innerHTML = 0;

    while (tableDiv.firstElementChild.tagName == 'SPAN') {
        tableDiv.removeChild(tableDiv.firstChild);
    }
}

if (document.getElementById('otbody') != null) {
    let otbody = document.getElementById("otbody");
    otbody.onsubmit = (e) => {
        e.preventDefault();
            // ЗДЕСЬ ДОЛЖЕН БЫТЬ КОД УДАЛЕНИЯ СТРОКИ ИЗ КОРЗИНЫ
    }
}

if (document.getElementById("ftable") != null) {

    let obj = JSON.parse(sessionStorage.clientInfo);
    document.getElementById("hello").innerHTML = "Hello, " + obj.login + "!";
    document.getElementById("balance").innerHTML = "Balance: " + obj.balance + "R";
    document.getElementById("discount").innerHTML = "Discount: " + obj.discount + "%";

    let url = `http://localhost:8080/flower/view?`;
    let xhr = new XMLHttpRequest();
    xhr.open("GET", url, false);
    xhr.send();
    let flowersList = JSON.parse(xhr.responseText);
    sessionStorage.setItem('currentQueue', JSON.stringify(flowersList));
    drawTable(flowersList);

    let finder = document.getElementById("findFlower");
    finder.onsubmit = (e) => {
        e.preventDefault();

        let from = document.getElementById('from').value;
        let to = document.getElementById('to').value;        
        let name = document.getElementById('name').value;

        let url = `http://localhost:8080/flower/view?`;
        if (name != null) url += `&name=${name}`;
        if (from != null) url += `&from=${from}`;
        if (to != null) url += `&to=${to}`;       

        let xhr = new XMLHttpRequest();
        xhr.open("GET", url, false);
        xhr.send();
        let resFlowers = JSON.parse(xhr.responseText);
        sessionStorage.setItem('currentQueue', JSON.stringify(resFlowers));
        drawTable(resFlowers);
    }

    let table1 = document.getElementById('tbody');
    table1.onclick = (e) => {
        if (e.target.tagName == 'BUTTON') {

            if (e.target.parentNode.parentNode.getElementsByTagName('td')[3].firstChild.value < 1) {
                e.target.parentNode.parentNode.getElementsByTagName('td')[3].firstChild.value = 1;
            }

            let name = e.target.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
            let price = e.target.parentNode.parentNode.getElementsByTagName('td')[1].innerHTML;
            let quantityToBuy = e.target.parentNode.parentNode.getElementsByTagName('td')[3].firstChild.value;

            let temp = {
                name: name,
                price: parseInt(price),
                quantity: parseInt(quantityToBuy),
                sum: 0
            };

            let temp1;

            if (sessionStorage.getItem('orderList') != []) {
                temp1 = JSON.parse(sessionStorage.getItem('orderList'));
            }
            else {
                temp1 = [];
            }

            let isFound = false;
            for (let i = 0; i < temp1.length; i++) {
                if (temp1[i].name === temp.name) {

                    let flowerQuant = e.target.parentNode.parentNode.getElementsByTagName('td')[2].innerHTML;

                    if ( (temp1[i].quantity + temp.quantity) <= flowerQuant) {
                        temp1[i].quantity += temp.quantity;
                    } 

                    isFound = true;
                    break;
                }
            }
            if (isFound == false) {
                temp1.push(temp);
            }

            let sum1 = 0;
            for (let i = 0; i < temp1.length; i++) {
                sum1 = temp1[i].quantity * temp1[i].price;
                temp1[i].sum = sum1;
            }

            sessionStorage.setItem('orderList', JSON.stringify(temp1));
            document.getElementById("curItems").innerHTML = temp1.length;
            drawOrdersTable(JSON.parse(sessionStorage.getItem('orderList')));

        }
    };

    let temp1;
    if (sessionStorage.getItem('orderList') != []) {
        temp1 = JSON.parse(sessionStorage.getItem('orderList'));
    }

    if (temp1 != null) {
        document.getElementById("curItems").innerHTML = temp1.length;
    }
}

let logout = () => { 
    location.href = 'http://localhost:8080/'

    sessionStorage.removeItem('clientInfo');
    sessionStorage.removeItem('currentQueue');
    sessionStorage.removeItem('orderList');

    let url = `http://localhost:8080/logout`;
    let xhr = new XMLHttpRequest();
    xhr.open("GET", url, false);
    xhr.send();
}

if (document.getElementById('loginForm') != null) {
    let h = document.createElement('div');
    document.body.appendChild(h);
    h.style.textAlign = "center";
    let loginForm = document.getElementById('loginForm');

    loginForm.onsubmit = (e) => {
        e.preventDefault();

        let login = document.getElementById('login').value;
        let password = document.getElementById('pass').value;

        let url = `http://localhost:8080/login?`;
        if (login != null) url += `&login=${login}`;
        if (password != null) url += `&password=${password}`;

        let xhr = new XMLHttpRequest();
        xhr.open("GET", url, false);
        xhr.send();

        let client = JSON.parse(xhr.responseText);

        sessionStorage.setItem('clientInfo', JSON.stringify(client));

        if (xhr.status == 200) {
            location.href = 'http://localhost:8080/flowers'
        }
        else {
            h.innerHTML = "Login or Password Wrong!";
        }
    }
}
