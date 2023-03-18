const express = require('express');
const bodyParser = require('body-parser');
const MongoClient = require('mongodb').MongoClient;

/*Creating Server Connection */
const app = express();
const port = 3000; 

/* Creating Connection to MongoDB DataBase */
const mongo_db_url = 'mongodb+srv://arielleandrotem:Milab123@study-zones.wrx9of8.mongodb.net/?retryWrites=true&w=majority';
const client = new MongoClient(mongo_db_url, {useNewUrlParser:true, useUnifiedTopology:true});

const db_name = "Study-Zones";
const user_collection = "Users";
const study_zone_collection = "Available-Areas";

app.use(bodyParser.json());

app.listen(port, () => {
  console.log(`Connected to port ${port} woohoo!`);
});

/*A. User Registration */

app.post("/signup", async (req, res) => {
  let username = req.query.username;
  let password = req.query.password;
  console.log("Input - Username: "+username + ", Password: "+password);

    client.connect().then(async() => {
      const info = client.db(db_name).collection(user_collection);

      const existingUser = await info.findOne({username: username});
      
      if(existingUser != null) {
        console.log("Username Already Exists");
        return res.json({ success: false, error: 'Username already exists' });
      }

      //If No Username Exists in the DB already
      const newUser = await info.insertOne({username: username, password : password,study_reservations: [] });
      
      console.log("User Successfully Registered in DB!" + username + " with Password: "+ password);

      return res.json({username: username, password: password, success: true});

    });

});

/*B. User Login */
app.get("/login", async(req, res) => {
  let username = req.query.username;
  let password = req.query.password;

  console.log("Input - Username: "+username + ", Password: "+password);

    client.connect().then(async() => {
      const info = client.db(db_name).collection(user_collection);
      const user = await info.findOne({username: username});
       
      if (user == null) {
        console.log("User Not Found");
        return res.json({ success: false, message: "Invalid login credentials" });
      }
      console.log("Found user in DB!! " + user.username + " The Password in DB: "+ user.password);
      
      if( password == user.password) {
        console.log("Login Success!!!");
        
        return res.json({ username: username, password: password, success: true}); 
      }
      else {
        console.log("Passwords don't match");

        return res.json({ success: false, message: "Invalid login credentials" });
      }
           
    }); 
});


/*B. Get User Reservations */
app.get("/getreservations", async(req, res) => {
  let username = req.query.username;
  let password = req.query.password;

  console.log("Input - Username: "+ username + ", Password: "+password);

    client.connect().then(async() => {
      const info = client.db(db_name).collection(user_collection);
      const user = await info.findOne({username: username});
      
      if (user == null) {
        console.log("User Not Found");
        return res.json({ success: false, message: "Invalid login credentials" });
      }

        return res.json({ username: username, success: true, study_reservations: user.study_reservations}); 
          
    }); 
    
});