const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const userSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true,
    unique: true,
    trim: true,
    lowercase: true
  },
  password: {
    type: String,
    required: true,
    trim: true
  }
});

userSchema.pre('save', async function(next) {
  const user = this;
  if (user.isModified('password')) {
    user.password = await bcrypt.hash(user.password, 8);
  }
  next();
});

userSchema.methods.generateAuthToken = async function() {
  const user = this;
  const token = jwt.sign({ _id: user._id.toString() }, 'mysecretkey');
  return token;
};

const User = mongoose.model('User', userSchema);

module.exports = User;


// const mongoose = require('mongoose');
// const bcrypt = require('bcryptjs');
// const jwt = require('jsonwebtoken');

// const Schema = mongoose.Schema
// const passportLocalMongoose = require('passport-local-mongoose');
// var User = new Schema({
//     username: {
//         type: String
//     },
//     password: {
//         type: String
//     }
// })
  
// User.plugin(passportLocalMongoose);
  

// module.exports = mongoose.model('User', User)