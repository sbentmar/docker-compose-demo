const addItem = (text) => {
    fetch('/todo', {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: text
        }).then(res => res.json())
        .then(_ => fetchTodoList());
}

const renderTodoList = (todoList) => {
    console.log(todoList);
    document.querySelector("#todo-list").innerHTML = todoList.items.map(
        item => `<li class='list-item'><span>${item.value}</span><button class='remove-button' onclick='removeKey("${item.key}")'>x</button></li>`
    ).join("");
}

const fetchTodoList = () => {
    fetch('/todo')
        .then(response => response.json())
        .then(data => {
            renderTodoList(data);
        });
}

document.querySelector("#todo-form").onsubmit = (event) => {
    event.preventDefault();
    event.stopImmediatePropagation();
    addItem(document.querySelector("#new-todo-item-text").value)
    return false;
}

const removeKey = (key) => {
    fetch(`/todo/${key}`, {
            method: 'delete'
        }).then(res => res.json())
        .then(_ => {
            console.log(`deleted key ${key}`);
            fetchTodoList();
        });
}

window.onload = () => {
    fetchTodoList();
}