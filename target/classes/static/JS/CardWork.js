var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
var perm;
let max_tek=0;
function preCreate(place) {
    var divplace = document.getElementById(place);
    var button = document.getElementById('Button' + place);
    button.hidden = true;
    var divElem = document.createElement('div');
    var id = divplace.children.length;
    divElem.classList.add('card-elem');
    divElem.id = id + '-' + place;
    var inputtext = document.createElement('input');
    inputtext.type = 'text';
    inputtext.addEventListener("mousedown", function () {
        if (inputtext.value != '') {
            createCard(inputtext.value, place, id);
        }
    });
    divplace.style.height = (360 * (id)) + 'px';
    if(max_tek<(400 * (id))){
        max_tek=(400 * (id))
    }
    document.getElementById('main-sec').style.height= max_tek+'px';
    divElem.appendChild(inputtext);
    divplace.appendChild(divElem);
}
function createCard(text, place, id) {
    var _a;
    var divElem = document.getElementById(id + '-' + place);
    var children = divElem.children;
    divElem.removeChild(children[0]);
    var button = document.getElementById('Button' + place);
    button.hidden = false;
    var ptext = document.createElement('p');
    ptext.textContent = text;
    if(perm.candelete){
        var buttonDelete= document.createElement('button')
        buttonDelete.textContent='Удалить'
        buttonDelete.addEventListener("mousedown", function () {
            deleteCard(text,place,id)
        });
        divElem.appendChild(buttonDelete)
    }
    if(perm.canUStage && place!='Ready'){
        var buttonUpgrade= document.createElement('button')
        buttonDelete.textContent='Изменить'
        buttonUpgrade.addEventListener("mousedown", function () {
            upgradeCard(text,place,id)
        });
        divElem.appendChild(buttonUpgrade)
    }
    if(perm.canedit){

    }
    var check = document.createElement('input');
    check.type = 'checkbox';
    check.id = 'checkbox';
    divElem.appendChild(ptext);
    divElem.appendChild(check);
    fetch("/saveCard", {
        method: 'POST',
        headers: (_a = {
                'Content-Type': 'application/json'
            },
            _a[csrfHeader] = csrfToken,
            _a),
        body: JSON.stringify({
            title: text,
            place: place,
            tags: [],
            completed: false
        })
    });
}
function upgradeCard(title,place,id){
    fetch('/upgradeCard',{
        method: 'PATCH',
        headers: (_a = {
            'Content-Type': 'application/json'
        },
            _a[csrfHeader] = csrfToken,
            _a),
        body: JSON.stringify({
            title: title,
            place: place
        })
    }).then(response => response.json())
        .then(function (card) {
            console.log(card);
            clearSpace()
            loadCard(card);
        });

}
function deleteCard(title,place,id){
    fetch('/deleteCard',{
        method: 'DELETE',
        headers: (_a = {
            'Content-Type': 'application/json'
        },
            _a[csrfHeader] = csrfToken,
            _a),
        body: JSON.stringify({
            title: title,
            place: place
        })
    }).then(response => response.json())
        .then(function (card) {
            console.log(card);
            clearSpace()
            loadCard(card);
        });

}
function clearSpace(){
    const divElement = document.getElementById('Ready');
    const divElement2 = document.getElementById('InProgress');
    const divElement3 = document.getElementById('Starting');
    if (divElement) {
        const children = Array.from(divElement.children);
        const children2 = Array.from(divElement2.children);
        const children3 = Array.from(divElement3.children);


        for (let i = 2; i < children.length; i++) {
            divElement.removeChild(children[i]);
        }
        for (let i = 2; i < children2.length; i++) {
            divElement2.removeChild(children2[i]);
        }
        for (let i = 2; i < children3.length; i++) {
            divElement3.removeChild(children3[i]);
        }
    }
}


function createPCard(Array, place) {
    for (var i = 0; i < Array.length; i++) {
        var project = Array[i];
        var divplace = document.getElementById(place);
        var divElem = document.createElement('div');
        var id = divplace.children.length;
        divElem.classList.add('card-elem');
        divElem.id = id + '-' + place;
        var ptext = document.createElement('p');
        ptext.textContent = project.title;
        var check = document.createElement('input');
        if(perm.candelete){
            var buttonDelete= document.createElement('button')
            buttonDelete.textContent='Удалить'
            buttonDelete.addEventListener("mousedown", function () {
                deleteCard(project.title,place,id)
            });
            divElem.appendChild(buttonDelete)
        }
        if(perm.canUStage && place!='Ready'){
            var buttonUpgrade= document.createElement('button')
            buttonUpgrade.textContent='Изменить'
            buttonUpgrade.addEventListener("mousedown", function () {
                upgradeCard(project.title,place,id)
            });
            divElem.appendChild(buttonUpgrade)
        }
        if(perm.canedit){

        }
        check.type = 'checkbox';
        check.id = 'checkbox';
        check.checked = project.completed;
        divplace.style.height = (360 * (id)) + 'px';
        if(max_tek<(400 * (id))){
            max_tek=(400 * (id))
        }
        document.getElementById('main-sec').style.height= max_tek+'px';
        divElem.appendChild(ptext);
        divElem.appendChild(check);
        divplace.appendChild(divElem);
    }
}
function loadCard(cards) {
    var Starting = cards.Starting;
    var Ready = cards.Ready;
    var InProgress = cards.InProgress;
    createPCard(Starting, 'Starting');
    createPCard(Ready, 'Ready');
    createPCard(InProgress, 'InProgress');
}
document.addEventListener('DOMContentLoaded', function () {
    fetch("/loadproject", {
        method: 'GET'
    }).then(function (response) { return response.json(); })
        .then(function (card) {
            fetch('/getPermitions',{
                method: 'GET'
            }).then(response => response.json())
                .then(permit => {
                    perm=permit
                    console.log(card);
                    loadCard(card);
                });
    });
});
