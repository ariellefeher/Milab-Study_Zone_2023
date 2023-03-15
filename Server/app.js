
const { MongoClient, ServerApiVersion } = require('mongodb');

const uri = "mongodb+srv://<arielleandrotem>:<Milab123>@study-zones.wrx9of8.mongodb.net/?retryWrites=true&w=majority";

const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true, serverApi: ServerApiVersion.v1 });

const User = require("./model/User");
let app = express();

client.connect(err => {
  const collection = client.db("test").collection("devices");
  
  // perform actions on the collection object

  client.close();
});

/* Logging in an existing user */
router.post("/login", async (req, res, next) =>{

  const password = req.body.password;
  try {
    
    let user = await User.findOne({ username: req.body.username });
  
  if(!user){
  
  return res.status(400).json({
  
  success: false,
  
  msg: "User Does Not Exist"
  
  });
  
  }
  
  const isMatch = await bcryptjs.compare(password, user.password);
  
  if(!isMatch){
  
  return res.status(400).json({
  success: false,
  msg: "Password Doesn't Match!"
  
  });
  
  }
  
  const payload = {
    user: {
    id: user.id
    }
  }
  
  jwt.sign(payload, process.env.jwtUserSecret,{
  
  expiresIn: 360000
  }, (err, token) =>{
  
  if(err) throw err; 
  res.status(200).json({
  
    success: true,
    msg: "Login Successfull",
    token: token,
    user: user
  
  });
  })
  
  } catch(error){
  
  console.log(error.message);
  
  res.status(500).json({
  success: false,
  msg: "Server Error"
  
  })
  }
  
  })


/* Register a New User to the Study Zones App */
router.post("/signup",async (req, res, next) => {

    const { username, password } = req.body;
    
    try{
    
    let userExists = await User.findOne({ username: req.body.username});
    
    if(userExists){
      return res.status(400).json({
      success: false,
      msg: "User already exists"
      });

    }
    
      let user = new User();
      
      user.username = username;
      
      const salt = await bcryptjs.genSalt(10);
      
      user.password = await bcryptjs.hash(password, salt);
    
      await user.save();
      
      const payload = {
      
      user: {
        id: user.id
      }
    }
    
    jwt.sign(payload, process.env.jwtUserSecret, {
    expiresIn: 360000
    }, (err, token) =>{
    
    if(err) throw err;
      res.status(200).json({
      success: true,
      token: token
      })
    })
    
    } catch(err) {
        console.log(err);
    }
    });

// const port = 8080;

// app.listen(port, () => {
//   console.log(`Listening on port ${port} woohoo!`);
// });