const express = require('express');
const bodyParser = require('body-parser');
const bcrypt = require('bcryptjs');
const MongoClient = require('mongodb').MongoClient;

const app = express();
const port = 3000; 

const mongo_db_url = 'mongodb+srv://arielleandrotem:Milab123@study-zones.wrx9of8.mongodb.net/?retryWrites=true&w=majority';
const client = new MongoClient(mongo_db_url, {useNewUrlParser:true, useUnifiedTopology:true});

const db_name = "Study-Zones";
const user_collection = "Users";

const saltRounds = 10; 

app.use(bodyParser.json());

app.listen(port, () => {
  console.log(`Connected to port ${port} woohoo!`);
});




// /*A. User Registration */
// //const User = require("./model/User");

// app.post("/signup", async (req, res) => {
//   let username = req.query.username;
//   let password = req.query.password;
//   //const { username, password } = req.body;

//   try {
//     const existingUser = await User.findOne({ username });
    
//     if (existingUser) {
//       return res.status(400).send({ error: 'Username already exists' });
//     }

//     const user = new User({ username, password });

//     await user.save();

//     // const token = await user.generateAuthToken();

//     return res.status(201).send({ user, password });

//   } catch (err) {
//      return res.status(400).send({ error: err.message });
//   }

// });

/*B. User Login */
app.get("/login", async(req, res) => {
  let username = req.query.username;
  let password = req.query.password;

  console.log("Username: "+username + ", Password: "+password);

    client.connect().then(async() => {
      const info = client.db(db_name).collection(user_collection);
      const user = await info.findOne({username: username});
      
      
      if (user == null) {
        console.log("User Not Found");
        return res.json({ success: false, message: "Invalid login credentials" });
      }
      console.log("Found user in DB! The Username in DB: " + user.username + "The Password in DB: "+ user.password);
      
      if( password == user.password) {
        console.log("Login Success!!!");
        
        return res.json({ success: true, message: "Success!" }); 
      }
      else {
        console.log("Passwords don't match");

        return res.json({ success: false, message: "Invalid login credentials" });
      }
           
    });   

});
