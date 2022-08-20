require('dotenv').config({ path: "./config.env" })
const express = require('express');
const path = require('path');
const app = express();
app.use(express.json());
app.use('/', require('./routes/route'));
const PORT = process.env.PORT || 5000

app.use('/static', express.static(__dirname + '/static'));
app.use(express.static(__dirname + '/public'));


app.listen(PORT, () => console.log(`server is running on port ${PORT}`));

