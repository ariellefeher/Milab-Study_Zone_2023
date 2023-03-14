
const request = require('request');

const express = require("express");

var API_KEY = '1H5HDTPZUQVU2E3Z';

function getStock(stockName, cb) {

    var url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${stockName}&apikey=1H5HDTPZUQVU2E3Z`;

    request.get({
        url: url,
        json: true,
        headers: {'User-Agent': 'request'}
    }, (err, res, data) => {
        if (err) {
            return cb(err, null);
        }

        else {
            
            let stockName = data["Global Quote"]["01. symbol"];
            let stockPrice =  data["Global Quote"]["05. price"];

            return cb(null, {stockName: stockName, stockPrice: stockPrice});
        }
    });
}


const app = express();

//recieving a request for the stocks
app.get("/stock", (req, res) => {
    let stockName = req.query.stockName;
	console.log("Requested Stock:", stockName);

    if (stockName == null) {
        res.send("Stock doesn't exist ");
        return;
    }
    
    getStock(stockName, (err, data) => {
        if (err){ 
        return res.status(500).json({err: err.message}); }

        console.log("requested data:", data);

        return res.json(data);
    });
});


const port = 8080;

app.listen(port, () => {
  console.log(`Listening on port ${port} woohoo!`);
});