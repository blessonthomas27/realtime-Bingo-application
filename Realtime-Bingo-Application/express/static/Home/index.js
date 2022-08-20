// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

//Get the join card and create card
var join_card = document.getElementById("join-card");
var create_card = document.getElementById("create-card");

// checkBox Join or create room
var ckbtn = document.getElementById("check_Box");
// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

//image
var baby_blue = document.getElementById("baby-blue");
var red = document.getElementById("red");
var green = document.getElementById("green");
var goast_blue = document.getElementById("goast-blue");
var orange = document.getElementById("orange");
var cute_blue = document.getElementById("cute-blue");

var image_Name = 'baby-blue'
localStorage.clear();

//seting value to image_name
baby_blue.onclick = function () {
    image_Name = 'baby-blue'
    baby_blue.style.border = "1px solid #2196F3";
}

red.onclick = function () {
    image_Name = 'red'
    red.style.border = "1px solid #2196F3";
}

green.onclick = function () {
    image_Name = 'green'
    green.style.border = "1px solid #2196F3";
}

goast_blue.onclick = function () {
    image_Name = 'goast-blue'
    goast_blue.style.border = "1px solid #2196F3";
}

orange.onclick = function () {
    image_Name = 'orange'
    orange.style.border = "1px solid #2196F3";
}

cute_blue.onclick = function () {
    image_Name = 'cute-blue'
    cute_blue.style.border = "1px solid #2196F3";
}


// When the user clicks the button, open the modal 
btn.onclick = function () {
    modal.style.display = "block";
}
// When the user clicks on <span> (x), close the modal
span.onclick = function () {
    modal.style.display = "none";
}
ckbtn.onclick = function () {
    if (ckbtn.checked === true) {
        create_card.style.display = "flex";
        join_card.style.display = "none";
    } else {
        join_card.style.display = "flex";
        create_card.style.display = "none";
    }
}

function matrix() {
    let unshuffled = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25]

    let shuffled = unshuffled
        .map(value => ({ value, sort: Math.random() }))
        .sort((a, b) => a.sort - b.sort)
        .map(({ value }) => value)
    const newArr = [];
    while (shuffled.length) newArr.push(shuffled.splice(0, 5));
    return newArr;
}

//Join Room
var join_button_submit = document.getElementById("join_button_submit");
var create_button_submit = document.getElementById("create_button_submit");

var roomId = document.getElementById("-lrommId");
roomId.onchange = function () {
    console.log(roomId.value);
}
join_button_submit.onclick = function (e) {
    e.preventDefault();
    var userName = document.getElementById("-fname")
    console.log(userName.value);;
    joinRoom(userName, roomId.value, image_Name)
}
create_button_submit.onclick = function (e) {
    e.preventDefault();
    var userName = document.getElementById("fname");
    const roomId = createRoom(userName)
}

async function createRoom(userName) {
    if (userName.value !== "" && userName.value != null) {
        await fetch('http://localhost:8080/room/', {
            method: 'POST',
            mode: 'cors'
        }).then((res) => {
            return res.json();
        }).then((data) => {
            joinRoom(userName, data.roomId, image_Name)
            return data.roomId
        }).catch(err => {
            alert("Somthing went wrong !!")
        })
    } else {
        alert("Invalid input !")
    }
}

async function joinRoom(userName, roomId, image_Name) {
    if (roomId !== "" && roomId !== null && userName !== "" && userName.value != null) {
        const _mat = matrix()
        const _body = {
            name: userName.value,
            imageName: image_Name,
            mat: _mat
        }
        await fetch('http://localhost:8080/room/' + roomId, {
            headers: {
                "Content-Type": 'application/json'
            },
            method: 'POST',
            body: JSON.stringify(_body),
            mode: 'cors'
        }).then((res) => {
            return res.json();
        }).then((data) => {
            localStorage.clear();
            localStorage.setItem("roomId", roomId)
            localStorage.setItem("matrix", _mat)
            localStorage.setItem("token", data.token)
            console.log(JSON.stringify(data));
            if (data.token) {
                window.location.href = "http://localhost:5000/gameTemplate";
            } else {
                localStorage.clear();
                badGateway("Somthing went Wrong")
            }
        }).catch(err => {
            alert("Somthing went wrong !!")
        })
    } else {
        alert("Invalid input !")
    }
}