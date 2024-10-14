function groupsShow(){
    const pen = document.getElementById('pen')
    const group = document.getElementById('group-form')
    const infos=document.getElementsByName('opis')
    const projects = document.getElementById('projects')
    if(pen.classList.contains('hidden')){
        pen.classList.remove('hidden')
        group.classList.add('hidden')
        group.classList.add('form-home-none')
        projects.classList.remove('hidden')
        group.classList.remove('form-home-flex')
        for (let i = 0; i < infos.length; i++) {
            infos[i].classList.remove('hidden')
        }
    }else{
        pen.classList.add('hidden')
        group.classList.remove('hidden')
        projects.classList.add('hidden')
        group.classList.remove('form-home-none')
        group.classList.add('form-home-flex')
        for (let i = 0; i < infos.length; i++) {
            infos[i].classList.add('hidden')
        }
    }
}
function penshow(){
    const pen = document.getElementById('groups')
    const group = document.getElementById('pen-form')
    const infos=document.getElementsByName('opis')
    const projects = document.getElementById('projects')
    if(pen.classList.contains('hidden')){
        pen.classList.remove('hidden')
        group.classList.add('hidden')
        group.classList.add('form-home-none')
        group.classList.remove('form-home-flex')
        projects.classList.remove('hidden')
        for (let i = 0; i < infos.length; i++) {
            infos[i].classList.remove('hidden')
        }
    }else{
        pen.classList.add('hidden')
        group.classList.remove('hidden')
        group.classList.remove('form-home-none')
        group.classList.add('form-home-flex')
        projects.classList.add('hidden')
        for (let i = 0; i < infos.length; i++) {
            infos[i].classList.add('hidden')
        }
    }
}
function projectshow(){
    const pen = document.getElementById('pen')
    const groups = document.getElementById('groups')
    const project = document.getElementById('projects-form')
    const pens = document.getElementById('pen-form')
    const group = document.getElementById('group-form')
    const infos=document.getElementsByName('opis')
    if(pen.classList.contains('hidden')){
        groups.classList.remove('hidden')
        pen.classList.remove('hidden')
        project.classList.add('hidden')
        pens.classList.remove('hidden')
        group.classList.remove('hidden')
        for (let i = 0; i < infos.length; i++) {
            infos[i].classList.remove('hidden')
        }
    }else{
        groups.classList.add('hidden')
        pen.classList.add('hidden')
        project.classList.remove('hidden')
        pens.classList.add('hidden')
        group.classList.add('hidden')
        for (let i = 0; i < infos.length; i++) {
            infos[i].classList.add('hidden')
        }
    }
}