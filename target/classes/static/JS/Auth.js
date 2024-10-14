var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
function registartion() {
    var data = {
        username: document.getElementById('reg_username').value,
        password: document.getElementById('reg_password').value,
        nick: document.getElementById('reg_nick').value,
        email: document.getElementById('reg_mail').value
    };
    fetch("/registration", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(function (response) {
        console.log(response)
        window.location.href = '/login';
    });
}
function sentTologin() {
    window.location.href = "/login";
}
function sentToReg() {
    window.location.href = "/registration";
}
function onTelegramAuth(user) {
    var data = {
        tgid: user.id + ''
    };
    fetch("/findTg", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
        credentials: 'include'
    }).then(function (response) {
        if (response.redirected) {
            window.location.href = response.url;
        }
    });
}
function regTelegr(user){
    fetch("/regTeleg", {
        method: 'POST',
        headers: (_a = {
            'Content-Type': 'application/json'
        },
            _a[csrfHeader] = csrfToken,
            _a),
        body: JSON.stringify({
            tgid: user.id+''
        })
    });
}
