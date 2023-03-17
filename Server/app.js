const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
//const jwt = require('jsonwebtoken');

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
const User = require("./model/User");

app.post("/signup", async (req, res) => {
  let username = req.query.username;
  let password = req.query.password;
  //const { username, password } = req.body;

  try {
    const existingUser = await User.findOne({ username });
    
    if (existingUser) {
      return res.status(400).send({ error: 'Username already exists' });
    }

    const user = new User({ username, password });

    await user.save();

    // const token = await user.generateAuthToken();

    return res.status(201).send({ user, password });

  } catch (err) {
     return res.status(400).send({ error: err.message });
  }

});

/*B. User Login */
app.get("/login", (req, res) => {
  let username = req.query.username;
  let password = req.query.password;
 // const { username, password } = req.body;
  
  try {
    const user = User.findOne({ username });
   
    if (!user) {
      return res.json({ success: false, message: "Invalid login credentials" });
    }
    
    const isMatch = bcrypt.compare(password, user.password);

    if (!isMatch) {
      return res.json({ success: false, message: "Invalid login credentials" });
    }
   
  // If successful
  //  const token = await user.generateAuthToken();
   
    return res.send({ success: true, message: "Success!" });
  
  } catch (err) {
     return res.status(400).send({ error: err.message });
  }

});

/* Adding authentication verification */
// const auth = async (req, res, next) => {
//   try {
//     const token = req.header('Authorization').replace('Bearer ', '');
//     const decoded = jwt.verify(token, 'mysecretkey');
//     const user = await User.findOne({ _id: decoded._id });
//     if (!user) {
//       throw new Error();
//     }
//     req.user = user;
//     req.token = token;
//     next();
//   } catch (err) {
//     res.status(401).send({ error: 'No authentication' });
//   }
// };

/*Creating protected route for authentication */
// app.get('/profile', auth, (req, res) => {
//   res.send(req.user);
// });
