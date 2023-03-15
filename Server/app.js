const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const app = express();
const port = 3000; 

app.use(bodyParser.json());

/*Establishing Connection with MongoDB */
mongoose.connect('mongodb+srv://arielleandrotem:Milab123@study-zones.wrx9of8.mongodb.net/?retryWrites=true&w=majority', { useNewUrlParser: true })
  .then(() => {
    console.log('Connected to MongoDB');
  })
  .catch((err) => {
    console.error('Error connecting to MongoDB', err);
  });

app.listen(port, () => {
  console.log(`Connected to port ${port} woohoo!`);
});

/*A. User Registration */
const User = require('./user');

app.post('/signup', async (req, res) => {
  const { username, password } = req.body;

  try {
    const existingUser = await User.findOne({ username });
    
    if (existingUser) {
      return res.status(400).send({ error: 'Username already exists' });
    }

    const user = new User({ username, password });

    await user.save();

    const token = await user.generateAuthToken();

    res.status(201).send({ user, token });

  } catch (err) {
     res.status(400).send({ error: err.message });
  }

});

/*B. User Login */
app.post('/login', async (req, res) => {
  
  const { username, password } = req.body;
  
  try {
    const user = await User.findOne({ username });
   
    if (!user) {
      return res.status(400).send({ error: 'Invalid username or password' });
    }
   
    const isMatch = await bcrypt.compare(password, user.password);
   
    if (!isMatch) {
      return res.status(400).send({ error: 'Invalid username or password' });
    }
   
    const token = await user.generateAuthToken();
   
    res.send({ user, token });
  
  } catch (err) {
     res.status(400).send({ error: err.message });
  }

});

/* Adding authentication verification */
const auth = async (req, res, next) => {
  try {
    const token = req.header('Authorization').replace('Bearer ', '');
    const decoded = jwt.verify(token, 'mysecretkey');
    const user = await User.findOne({ _id: decoded._id });
    if (!user) {
      throw new Error();
    }
    req.user = user;
    req.token = token;
    next();
  } catch (err) {
    res.status(401).send({ error: 'Please authenticate' });
  }
};

/*Creating protected route for authentication */
app.get('/profile', auth, (req, res) => {
  res.send(req.user);
});

// const { MongoClient, ServerApiVersion } = require('mongodb');

// const uri = "mongodb+srv://<arielleandrotem>:<Milab123>@study-zones.wrx9of8.mongodb.net/?retryWrites=true&w=majority";

// const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true, serverApi: ServerApiVersion.v1 });

// const User = require("./model/User");
// let app = express();


// client.connect(err => {
//   const collection = client.db("test").collection("devices");
  
//   // perform actions on the collection object

//   client.close();
// });

// /* Logging in an existing user */
// app.post("/login", async (req, res, next) =>{

//   const password = req.body.password;
//   try {
    
//     let user = await User.findOne({ username: req.body.username });
  
//   if(!user){
  
//   return res.status(400).json({
  
//   success: false,
  
//   msg: "User Does Not Exist"
  
//   });
  
//   }
  
//   const isMatch = await bcryptjs.compare(password, user.password);
  
//   if(!isMatch){
  
//   return res.status(400).json({
//   success: false,
//   msg: "Password Doesn't Match!"
  
//   });
  
//   }
  
//   const payload = {
//     user: {
//     id: user.id
//     }
//   }
  
//   jwt.sign(payload, process.env.jwtUserSecret,{
  
//   expiresIn: 360000
//   }, (err, token) =>{
  
//   if(err) throw err; 
//   res.status(200).json({
  
//     success: true,
//     msg: "Login Successfull",
//     token: token,
//     user: user
  
//   });
//   })
  
//   } catch(error){
  
//   console.log(error.message);
  
//   res.status(500).json({
//   success: false,
//   msg: "Server Error"
  
//   })
//   }
  
//   })


// /* Register a New User to the Study Zones App */
// router.post("/signup",async (req, res, next) => {

//     const { username, password } = req.body;
    
//     try{
    
//     let userExists = await User.findOne({ username: req.body.username});
    
//     if(userExists){
//       return res.status(400).json({
//       success: false,
//       msg: "User already exists"
//       });

//     }
    
//       let user = new User();
      
//       user.username = username;
      
//       const salt = await bcryptjs.genSalt(10);
      
//       user.password = await bcryptjs.hash(password, salt);
    
//       await user.save();
      
//       const payload = {
      
//       user: {
//         id: user.id
//       }
//     }
    
//     jwt.sign(payload, process.env.jwtUserSecret, {
//     expiresIn: 360000
//     }, (err, token) =>{
    
//     if(err) throw err;
//       res.status(200).json({
//       success: true,
//       token: token
//       })
//     })
    
//     } catch(err) {
//         console.log(err);
//     }
//     });

// // const port = 8080;

// // app.listen(port, () => {
// //   console.log(`Listening on port ${port} woohoo!`);
// // });