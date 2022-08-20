const path = require('path');

exports.home = (req, res, next) => {
    res.sendFile(path.join(__dirname, '../html/index.html'));
}

exports.gameTemplate = (req, res, next) => {
    console.log(req.body.token);
    res.sendFile(path.join(__dirname, '../html/gameTemplate.html'));
}