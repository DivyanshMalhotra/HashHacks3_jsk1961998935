import librosa
import pyaudio
import wave
import numpy as np
import matplotlib.pyplot as plt
import librosa.display
import numpy as np
import datetime;
import time
import cv2

# import the necessary packages
import argparse
import imutils
CHUNK = 1024
FORMAT = pyaudio.paInt16
CHANNELS = 2
RATE = 44100
RECORD_SECONDS = 3
WAVE_OUTPUT_FILENAME = "output.wav"



def aaa():
    p = pyaudio.PyAudio()

    stream = p.open(format=FORMAT,
                    channels=CHANNELS,
                    rate=RATE,
                    input=True,
                    frames_per_buffer=CHUNK)

    print("* recording")

    frames = []

    for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
        data = stream.read(CHUNK)
        frames.append(data)

    print("* done recording")

    stream.stop_stream()
    stream.close()
    p.terminate()

    wf = wave.open(WAVE_OUTPUT_FILENAME, 'wb')
    wf.setnchannels(CHANNELS)
    wf.setsampwidth(p.get_sample_size(FORMAT))
    wf.setframerate(RATE)
    wf.writeframes(b''.join(frames))
    wf.close()





    y , sr =librosa.load(r'C:\Users\PIYUSH\Desktop\output.wav',sr=32050)
#import librosa
    s = np.abs(librosa.stft(y))
    pitches, magnitudes = librosa.piptrack(y=y, sr=sr)
    s_mean=(np.mean(s))
    s_pitch=(np.mean(magnitudes))
    result=(s_mean+s_pitch)/2
    return result

import pyrebase

outpath=r"C:\Users\PIYUSH\Desktop\main.jpg"
outpath1=r'C:\Users\PIYUSH\Desktop\cam1.jpg'
outpath2=r'C:\Users\PIYUSH\Desktop\cam2.jpg'
config = {
    "apiKey": "apiKey",
    "authDomain": "bageera2018.firebaseapp.com",
    "databaseURL": "https://bageera2018.firebaseio.com",
    "storageBucket": "bageera2018.appspot.com",
    #"serviceAccount": "path/to/serviceAccountCredentials.json"
}


firebase = pyrebase.initialize_app(config)
storage = firebase.storage()
db = firebase.database()
   
def upload(outpath):
    mydate=(datetime.datetime.now())
    myaslidate=str(mydate.strftime('%b %d %Y'))+" "+str(mydate.hour)+" "+str(mydate.minute)+" "+str(mydate.second)+"-18.5513821,73.8230777.jpeg"
    storage.child(myaslidate).put(outpath)
    db.child("users").child("1").push(myaslidate)  #mydate.microsecond
    
result1=aaa()
print(result1)
    


def karnapadega():
    while(True):
        cap1 = cv2.VideoCapture(1)
        cap2 = cv2.VideoCapture(0)

        ret1,frame1=cap1.read()
        ret2,frame2=cap2.read()
        if ret1== True and ret2==True:
            cv2.imshow("frame",frame1)
            cv2.imshow("frame",frame2)
            print("dududu")
            
            #gray = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            if result1>0.01:
                #gray = cv2.cvtColor(frame)
                cv2.imwrite(outpath1,frame1)
                cv2.waitKey(1)        
                cap1.release()
                cv2.imwrite(outpath2,frame2)
                cv2.waitKey(1)
                cap2.release()
                cv2.destroyAllWindows()
                


                # construct the argument parse and parse the arguments
                '''ap = argparse.ArgumentParser()
                ap.add_argument("-f", "--first", required=True,
                        help="path to the first image")
                ap.add_argument("-s", "--second", required=True,
                        help="path to the second image")
                args = vars(ap.parse_args())'''

                # load the two images and resize them to have a width of 400 pixels
                # (for faster processing)
                imageA = cv2.imread(outpath1)
                imageB = cv2.imread(outpath2)
                imageA = imutils.resize(imageA, width=400)
                imageB = imutils.resize(imageB, width=400)
                from Stitcher import stitcher
                # stitch the images together to create a panorama
                stitcher = stitcher()
                (result, vis) = stitcher.stitch([imageA, imageB], showMatches=True)

                # show the images
                #cv2.imshow("Image A", imageA)
                #cv2.imshow("Image B", imageB)
                #cv2.imshow("Keypoint Matches", vis)
                cv2.imwrite(outpath,vis)
                cv2.waitKey(1)        
                cap1.release()
                cap2.release()
                cv2.destroyAllWindows()

                upload(outpath)

                break
                                         
        else:
                    
            cv2.destroyAllWindows()
            break

                    


while(True):
    time.sleep(5)
    result1=aaa()
    print(result1)
    #cap = cv2.VideoCapture(0)
    karnapadega()
