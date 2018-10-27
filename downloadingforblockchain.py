import pyrebase
#import cv2
import winsound
import requests


i=1

config = {
  "apiKey": "apiKey",
  "authDomain": "",
  "databaseURL": "",
  "storageBucket": "",
  #"serviceAccount": "path/to/serviceAccountCredentials.json"
}

def stream_handler(message):
    global i
    if i != 1:
        #print(message)
        #users = db.child("users").get()
        #print(users.val())
        users=message["data"]
        print(users)
        if type(users)is list:
            dlink=str(users[1])
        elif str(users) is 'None':
            return
        elif type(users) is dict:
            dlink=str(list(users.values())[0])
        else:
            dlink=str(users)
        print(dlink)
        storage.child(dlink).download("downloaded.jpeg")
        resp = requests.get('https://todolist.example.com/tasks/')
        #print(img)
        #mat = cv2.imread('downloaded.jpeg',0)
        #print(mat)
        #cv2.imshow('downloaded.jpeg',mat)
     
    else:
        i=2

firebase = pyrebase.initialize_app(config)
storage = firebase.storage()
db = firebase.database()
my_stream = db.child("users").stream(stream_handler)


