function saveCommit(){
    if(perm.canCommit){
        fetch("/commit", {
            method: 'POST',
            headers: (_a = {
                'Content-Type': 'application/json'
            },
                _a[csrfHeader] = csrfToken,
                _a),
        });
    }
}
function addOptions(commitList){
    const select = document.getElementById('commitSelect')
    for (let i = 0; i < commitList.length; i++) {
        const newOption = document.createElement('option');
        newOption.text = commitList[i];
        newOption.value = i+1;
        select.add(newOption);
    }
}
function removeOptions(){
    const select = document.getElementById('commitSelect')
    console.log(select)
    for (let i = 1; i < select.options.length; i++) {
        select.remove(i)
    }
}
function UseCommit(){
    if(perm.canCommit){
        const select = document.getElementById('commitSelect')
        const selectedOption = select.options[select.selectedIndex]
        console.log(selectedOption.text)
        if(select.options.length==1 && selectedOption.text==''){
            select.hidden=false;
            fetch("/commit", {
                method: 'GET',
                headers: (_a = {
                    'Content-Type': 'application/json'
                },
                    _a[csrfHeader] = csrfToken,
                    _a),
            }).then(response => response.json())
                .then(function (commitList) {
                    addOptions(commitList.commits);
                });;
        }else{
            select.hidden=true;
            fetch("/commits", {
                method: 'POST',
                headers: (_a = {
                    'Content-Type': 'application/json'
                },
                    _a[csrfHeader] = csrfToken,
                    _a),
                body: JSON.stringify({
                    commit: selectedOption.text
                })
            }).then(response => response.json())
                .then(function (card) {
                    clearSpace()
                    loadCard(card);
                    removeOptions()
                });;
        }
    }
}