import pyrebase
config = {
  "apiKey": "apiKey",
  "authDomain": ,
  "databaseURL": ,
  "storageBucket":,
  #"serviceAccount": "path/to/serviceAccountCredentials.json"
}

firebase = pyrebase.initialize_app(config)
storage = firebase.storage()
#storage.child("example.jpeg").put("thumbDiv.jpeg")
db = firebase.database()
#db.child("users").set({1:"example.jpeg"})
users = db.child("users").get()
dlink=users.val()
print(dlink[1])
storage.child(dlink[1]).download("downloaded.jpg")
