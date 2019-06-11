let h = document.createElement('div');
document.body.appendChild(h);
h.style.textAlign = "center";

// window.onload = function() {   

// }

// function redirect(){
//     let xhr = new XMLHttpRequest();
//     xhr.open("GET", "http://localhost:8080/reg", false);
//     xhr.send();
//     console.log("redirecting");
// }
// let loginForm = document.getElementById('loginForm');
// loginForm.onsubmit = (e) => {
//     e.preventDefault();

//     let login = document.getElementById('login').value;
//     let password = document.getElementById('pass').value;

//     // let url = "players.json";

//     let url = `http://localhost:8080/login?`;
//     if (login != null) url += `&login=${login}`;
//     if (password != null) url += `&password=${password}`;

//     let xhr = new XMLHttpRequest();
//     xhr.open("GET", url, false);
//     xhr.send();

//     let result = JSON.parse(xhr.responseText);
//     console.log(result);

//     if (xhr.status == 200) {
//         // h.innerHTML = "Login Successfull";
//         location.href = 'http://localhost:8080/flowers'
//     }
//     else {
//         h.innerHTML = "Login or Password Wrong!";
//     }

//     // var results = [];

//     // var toSearch = login;

//     // for (var i = 0; i < result.players.length; i++) {
//     //     for (key in result.players[i]) {
//     //         if (result.players[i][key].indexOf(toSearch) != -1) {
//     //             results.push(result.players[i]);
//     //         }
//     //     }
//     // }

//     // console.log(results);
// }

let regForm = document.getElementById('registerForm');
regForm.onsubmit = (e) => {
    e.preventDefault();

    let login = document.getElementById('login1').value;
    let password = document.getElementById('pass1').value;

    // let url = `http://localhost:8080/register?`;
    // if (login != null) url += `&login=${login}`;
    // if (password != null) url += `&password=${password}`;

    // let xhr = new XMLHttpRequest();
    // xhr.open("POST", url, false);
    // xhr.send();
    console.log(login);
    console.log(password);
    
    

    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/register', false);

    var body = "login=" + login +
      "&password=" + password;
    
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    
    xhr.onreadystatechange = function() {
        //Вызывает функцию при смене состояния.
        if(xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            // Запрос завершен. Здесь можно обрабатывать результат.
        }
    }
    
    xhr.send(body);



    // let result = JSON.parse(xhr.responseText);
    // console.log(result);

    if (xhr.status == 200) {
        location.href = 'http://localhost:8080/'
    }
    else {
        h.innerHTML = "You cant use this login!";
    }
}
