var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
function createGroup() {
    var _a;
    var group_name = document.getElementById('group_name').value;
    var group_member = document.getElementById('group_member').value;
    fetch("/createGroup", {
        method: 'POST',
        headers: (_a = {
                'Content-Type': 'application/json'
            },
            _a[csrfHeader] = csrfToken,
            _a),
        body: JSON.stringify({
            group_member: group_member,
            group_name: group_name
        })
    }).then(function (response) {
        if (response.redirected) {
            window.location.href = response.url;
        }
    });
}
