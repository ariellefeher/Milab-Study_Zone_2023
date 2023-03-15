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
const User = require('./model/User');

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
      res.json({ success: false, message: "Invalid login credentials" });
    }
   
    const isMatch = await bcrypt.compare(password, user.password);
   
    if (!isMatch) {
      res.json({ success: false, message: "Invalid login credentials" });
    }
   
  // If successful
    const token = await user.generateAuthToken();
   
    res.send({ success: true, user, token });
  
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
    res.status(401).send({ error: 'No authentication' });
  }
};

/*Creating protected route for authentication */
app.get('/profile', auth, (req, res) => {
  res.send(req.user);
});
