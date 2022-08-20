const allBox = document.querySelectorAll("section span");
var roomId = localStorage.getItem("roomId");
var matrix = localStorage.getItem("matrix").split(",");
var token = localStorage.getItem("token");
var mainCard = document.getElementById("leader-board-card-body")
var play_but = document.getElementById("play-control-area");
var send_butt = document.getElementById("chat-grid-send-button")



const main_section = document.getElementById("main-section");
const pre_processor = document.getElementById("pre_processor");
var UserToken = parseJwt(token);
var Room = {
    id: null,
    roomStatus: null,
    user: [
        {
            id: null,
            name: null,
            imageName: null,
            totalSucess: null,
            playerStatus: null
        }
    ],
    strike: null,
    turnId: null
}
var Message = null
if (roomId !== null && matrix !== null && token !== null) {
    //webSocket connect
    var stompClient = null;
    connect()
    //pass
    function connect() {
        var socket = new SockJS('http://localhost:8080/liveApi');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe(`/topic/${roomId}`, function (room) {
                mainCard.innerHTML = ''
                if (room.body === "failed") {

                } else {
                    var data = JSON.parse(room.body);
                    if (data.id === roomId) {
                        Room = data
                    } else {
                        Room = data.body
                    }
                    if (Room.id !== null && Room.roomStatus !== null && Room.user !== null) {
                        for (let index = 0; index < Room.user.length; index++) {
                            if (Room.user[index].totalSucess >= 5) {
                                for (let indx = 0; indx < Room.user.length; indx++) {
                                    if (Room.user[indx].id === UserToken.iss) {
                                        if (Room.user[indx].totalSucess >= 5) {
                                            showWinner("congratulations you have won the game !!")
                                        } else {
                                            showWinner("try again next time !")
                                        }
                                    }
                                }
                            }
                        }
                        setUi();
                        getRoom()
                    }
                    else {
                        badGateway("Bad Gateway")
                    }

                }
            });
            stompClient.subscribe(`/topic/message/${roomId}`, function (msg) {
                Message = JSON.parse(msg.body);
                showMessage(Message.message)
            });
            getRoom()
        });
    }
    //pass
    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
    }
    //pass
    function getRoom() {
        stompClient.send("/app/room/" + roomId, {}, JSON.stringify({}));
    }
    function setStrike(Strike) {
        stompClient.send("/app/room/strike/" + roomId, {}, JSON.stringify({ 'strike': Strike, 'userId': UserToken.iss }));
    }

    function setControl(str) {
        stompClient.send("/app/room/control/" + roomId, {}, JSON.stringify({ 'status': str, 'userId': UserToken.iss }));
    }

    function sendMessage(msg) {
        stompClient.send("/app/room/message/" + roomId, {}, JSON.stringify({ 'message': msg }));
    }

    function gameResetCheck() {
        var mat = _matrix();
        localStorage.setItem("matrix", mat)
        stompClient.send("/app/room/reset/" + roomId, {}, JSON.stringify({ 'mat': mat }));
    }
}
else {
    redirectBack("Bad Gateway")
}

function setUi() {
    var controlButton = document.getElementById("play-control-area");
    if (Room.roomStatus === 'JOIN') {
        controlButton.setAttribute("style", "display:flex")
        var butText = controlButton.querySelector('.play-control-area-button');
        butText.textContent = 'play'
    }
    for (let index = 0; index < Room.user.length; index++) {
        const status = Room.user[index].playerStatus;
        const _userId = Room.user[index].id;
        if (status === "READY" && _userId === UserToken.iss) {
            controlButton.setAttribute("style", "display:none")
        }

    }
    for (let i = 0; i < matrix.length; i++) {
        allBox[i].setAttribute("onclick", "clickedBox(this)");
        allBox[i].textContent = matrix[i];
        if (Room.strike != null) {
            for (let j = 0; j < Room.strike.length; j++) {
                if (allBox[i].textContent == Room.strike[j]) {
                    allBox[i].setAttribute("style", "background: rgb(255, 222, 222);  border-radius: 25px; font-weight: 100; color: black;")
                }
            }
        }
    }
    pre_processor.style.display = "none";
    main_section.style.display = "grid";
    setLeaderBoard();
}

function setLeaderBoard() {
    var playerCardParent = document.getElementById("leader-board-card-body-playerCard").parentNode;


    let size = Room.user.length;
    for (let i = 0; i <= size; i++) {
        var playerName = playerCardParent.querySelector('.player-name');
        var playerImage = playerCardParent.querySelector('.player-image');
        if (Room.user[i].totalSucess !== 0 && Room.user[i].totalSucess <= 5) {
            var Bingo_status = playerCardParent.querySelector('.BINGO');
            var list = ["B ", "I ", "N ", "G ", "O "]
            if (Room.user[i].totalSucess !== 0) {
                var arr = list.slice(0, Room.user[i].totalSucess)
                Bingo_status.textContent = arr.join('');
            }
        }
        playerName.textContent = "" + Room.user[i].name
        playerImage.src = `../static/images/${Room.user[i].imageName}.jpg`
        mainCard.innerHTML += playerCardParent.innerHTML
    }
}


function clickedBox(element) {
    //check for valid click?
    if (Room.roomStatus === 'INGAME') {
        if (Room.turnId === UserToken.iss) {
            setStrike(parseInt(element.textContent));
        } else {
            alert("not you chance")
        }
    } else {
        alert("game not yet started")
    }
    if (Room.strike !== null) {
        for (let index = 0; index < Room.strike.length; index++) {
            const strikeNo = Room.strike[index];
            if (strikeNo === parseInt(element.textContent)) {
                return alert("invalid move !")
            }
        }
    }
}

function badGateway(msg) {
    alert(msg)
    window.location.href = "http://localhost:5000/home";
}


function parseJwt(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};

function showMessage(msg) {
    var _messageCard = document.getElementById("chat-grid-body-messageCard-display-inner");
    _messageCard.innerHTML += `<div>${msg}</div>`
}

play_but.onclick = function () {
    setControl('READY')
}

send_butt.onclick = function () {
    var msg = document.getElementById("message");
    sendMessage(msg.value);
    msg.value = ''
}

// When the user clicks the button, open the modal 
function showWinner(msg) {
    alert(msg)
    setTimeout(home, 2000);
}
function home() {
    window.location.href = "http://localhost:5000/home";
}
function _matrix() {
    let unshuffled = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25]
    let shuffled = unshuffled
        .map(value => ({ value, sort: Math.random() }))
        .sort((a, b) => a.sort - b.sort)
        .map(({ value }) => value)
    const newArr = [];
    while (shuffled.length) newArr.push(shuffled.splice(0, 5));
    return newArr;
}