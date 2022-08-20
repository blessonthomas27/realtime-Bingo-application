const { Router } = require('express');
const express = require('express')
const router = express.Router();

const { home, gameTemplate } = require('../controllers/controler')
router.route("/home").get(home)
router.route("/gameTemplate").get(gameTemplate)

module.exports = router;
