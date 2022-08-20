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

var image_Name = 'baby_blue'

//seting value to image_name
baby_blue.onclick = function () {
    image_Name = 'baby_blue'
    modal.style.border = "1px solid #2196F3";
}

red.onclick = function () {
    image_Name = 'red'
    modal.style.border = "1px solid #2196F3";
}

green.onclick = function () {
    image_Name = 'green'
    modal.style.border = "1px solid #2196F3";
}

goast_blue.onclick = function () {
    image_Name = 'goast_blue'
    modal.style.border = "1px solid #2196F3";
}

orange.onclick = function () {
    image_Name = 'orange'
    modal.style.border = "1px solid #2196F3";
}

cute_blue.onclick = function () {
    image_Name = 'cute_blue'
    modal.style.border = "1px solid #2196F3";
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

//api calls
function matrix() {
    let unshuffled = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25]

    let shuffled = unshuffled
        .map(value => ({ value, sort: Math.random() }))
        .sort((a, b) => a.sort - b.sort)
        .map(({ value }) => value)
    const newArr = [];
    while (shuffled.length) newArr.push(shuffled.splice(0, 3));
    return newArr;
}

//Join Room
var join_button_submit = document.getElementById("join_button_submit");
var roomId = document.getElementById("-lrommId");
join_button_submit.onclick = function () {
    var userName = document.getElementById("fname");
    if (roomId.value !== "" || roomId !== null) {
        fetch('http://localhost:8080/room/' + roomId.value, {
            method: 'POST',
            body: {
                name: userName.value,
                imageName: image_Name,
                mat: matrix()
            }
        }).then(res => {
            res.json()
        })
            .then(data => {
                console.log(data)
            }).catch(err => {
                console.log("ERROR")
            })
    } else {
        alert("Please create room !")
    }
}