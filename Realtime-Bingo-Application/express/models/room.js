class Room {
    constructor(id, Strike, roomStatus, user) {
        this.id = id;
        this.Strike = Strike;
        this.roomStatus = roomStatus;
        this.user = user;
    }
    Room() {
        super();
    }
    getId() {
        return this.id;
    }
    setId(id) {
        this.id = id
    }
    getStrike() {
        return this.Strike;
    }
    setStrike(Strike) {
        this.Strike = Strike
    }
    getRoomStatus() {
        return this.roomStatus;
    }
    setRoomStatus(roomStatus) {
        this.roomStatus = address
    }
    getUser() {
        return this.user;
    }
    setUser(user) {
        this.user = user
    }
}
module.exports.room = Room;