const express = require('express');
const bodyParser = require('body-parser');
let app = express();
const server = require('http').createServer(app);
const io = require('socket.io')(server);
const alpha = require('alphavantage')({ key: 'Z6EEXPXFLN7F6Q1Z' });
const admin = require('firebase-admin');
const serviceAccount = require("./serviceAccountKey.json");
const port = 3000;
app.use(bodyParser.json());
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://stockapp-316d8.firebaseio.com"
});
let tokens = {};
let pushIntervals = {};


// LISTEN
server.listen(port, function () {
	console.log('Server listening at port %d', port);
});

// HANDLE TOKEN UPDATES
app.post('/:user/token', (req, res, next) => {
    let token = req.body.token;
    console.log(`Received save token request from ${req.params.user} for token=${token}`);
    if (!token) return res.status(400).json({err: "missing token"});
    tokens[req.params.user] = token;
    res.status(200).json({msg: "saved ok"});
});

// SCOKET IO
io.on('connection', (socket) => {
  console.log('client connected');
  let stockName, userId, dataUpdate;
  let sendNotifications = false;

  socket.on('stock price request', function(stockNameReq, userIdReq){
    stockName = stockNameReq;
    userId = userIdReq;
    sendNotifications = true;

    // clear push notification for a user in FG
    if (pushIntervals[userId] != null){
      clearInterval(pushIntervals[userId]);
      pushIntervals[userId] = null;
    }

    // clear prev fg interval if defined
    if (dataUpdate != null){
      clearInterval(dataUpdate);
    }

    // send current price 
    sendStockPrice(stockName, socket);

    // set interval for socket io stock price updates
    dataUpdate = setInterval(()=>{
    sendStockPrice(stockName, socket);
    console.log('socket-io: updated was sent');
    }, 30000);

  });

  socket.on('stop stock price updates', function(){
    clearInterval(dataUpdate);
    sendNotifications = false;
  });

  socket.on('disconnect', () => {
    // send push notificatitons if client requested a stock price
    if(sendNotifications){
      clearInterval(dataUpdate);
      registrationToken = tokens[userId];
      pushIntervals[userId] = setInterval(function() {
        sendPushUpdates(stockName, registrationToken);
      }, 30000);
      console.log(`socket io was disconnected ${userId}, ${stockName}`);
    }
  });
});

function sendStockPrice(stockName, socket){
  alpha.data.batch(`${stockName}`).then(data => {
  let stockValue = parseFloat(data['Stock Quotes'][0]['2. price']);
  socket.emit('stock price reply', { price: `${stockValue}`});
  })
  .catch(error=>{
    console.error(`Error: ${error}`);
  });
}

function sendPushUpdates(stockName, registrationToken){
  alpha.data.batch(`${stockName}`).then(data => {
    let stockValue = parseFloat(data['Stock Quotes'][0]['2. price']);
    let message = {
      notification: {
        title: `${stockName} price`,
        body: `${stockValue}`,
      },
      android: {
        ttl: 3600 * 1000,
        notification: {
        icon: 'stock_ticker_update',
        color: '#f45342',
        },
      },
      data: {
        stock: `${stockName}`,
        price: `${stockValue}`
      },
      token: registrationToken
    };

    // send message
    admin.messaging().send(message)
    .then((response) => {
      console.log('Successfully sent message:', response);
    })
    .catch((error) => {
      console.log('Error sending message:', error);
    });
  })
  .catch(error=>{
    console.error("alphavantage error :" + error);
  });
}