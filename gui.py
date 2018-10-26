'''
from tkinter import *  
from PIL import ImageTk,Image  
root = Tk()  
canvas = Canvas(root, width = 500, height = 500)  
canvas.pack()  
img = ImageTk.PhotoImage(Image.open("downloaded.jpg"))
img.subsample(2,2)
def helloCallBack():
   print( "Hello Python", "Hello World")

B = Button(root, text ="Hello", command = helloCallBack)
B.pack()
canvas.create_image(50,50 , anchor=NW, image=img)  
root.mainloop()  
'''
from tkinter import *
import tkinter
import tkinter.messagebox
from PIL import Image
from PIL import ImageTk

master = Tk()

def callback():
    print("click!")

width = 500
height = 300
canvas = Canvas(master, width = 500, height = 500)  
canvas.pack() 
img = Image.open("downloaded.jpg")
img = img.resize((width,height), Image.ANTIALIAS)
photoImg =  ImageTk.PhotoImage(img)
canvas.create_image(50,50 , anchor=NW, image=photoImg)
b = Button(master,text='Click', command=callback, width=50)
b.pack()
mainloop()
