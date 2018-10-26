import numpy as np
import cv2

# Capture video from file
cap = cv2.VideoCapture(1)
cap1 = cv2.VideoCapture(0)
outpath1=r"C:\Users\PIYUSH\Desktop\piyush.jpeg"
outpath2=r"C:\Users\PIYUSH\Desktop\piyush2.jpeg"
outpath3=r"C:\Users\PIYUSH\Desktop\piyush3.jpeg"
while True:

    ret, frame = cap.read()
    ret1, frame1 = cap1.read()
    

    if ret == True:

        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        cv2.imshow('frame',frame)
        cv2.imwrite(outpath1,gray)
        cv2.waitKey(1)        
        cap.release()
    if ret1 == True:
        gray1 = cv2.cvtColor(frame1, cv2.COLOR_BGR2GRAY)

        cv2.imshow('frame1',frame1)
        cv2.imwrite(outpath2,frame1)
        cv2.waitKey(1)        
        cap1.release()

        if cv2.waitKey(30) & 0xFF == ord('q'):
            break

    else:
        break
    
cv2.destroyAllWindows()

from Stitcher import stitcher
# import the necessary packages
import argparse
import imutils
import cv2

# construct the argument parse and parse the arguments
'''ap = argparse.ArgumentParser()
ap.add_argument("-f", "--first", required=True,
	help="path to the first image")
ap.add_argument("-s", "--second", required=True,
	help="path to the second image")
args = vars(ap.parse_args())'''

# load the two images and resize them to have a width of 400 pixels
# (for faster processing)
imageA = cv2.imread(r'C:\Users\PIYUSH\Desktop\piyush.jpg')
imageB = cv2.imread(r'C:\Users\PIYUSH\Desktop\piyush2.jpg')
imageA = imutils.resize(imageA, width=400)
imageB = imutils.resize(imageB, width=400)

# stitch the images together to create a panorama
stitcher = stitcher()
(result, vis) = stitcher.stitch([imageA, imageB], showMatches=True)

# show the images
#cv2.imshow("Image A", imageA)
#cv2.imshow("Image B", imageB)
cv2.imshow("Keypoint Matches", vis)
cv2.imwrite(outpath3,vis)
#cv2.imshow("Result", result)
cv2.waitKey(0)
