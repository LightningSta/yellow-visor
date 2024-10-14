var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
function createProject() {
    var _a;
    var project_name = document.getElementById("project_name").value;
    fetch("/createProject", {
        method: 'POST',
        headers: (_a = {
                'Content-Type': 'application/json'
            },
            _a[csrfHeader] = csrfToken,
            _a),
        body: JSON.stringify({
            project_name: project_name
        })
    }).then(function (response) {
        if (response.redirected) {
            window.location.href = response.url;
        }
    });
}
