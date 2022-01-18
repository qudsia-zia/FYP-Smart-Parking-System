# importing everything we need
import cv2
import csv
import pyrebase
config = {

   "apiKey": "AIzaSyB2zU61VJoRTxMGdx8xFdvsKhCv-LSgl_k",

  "authDomain": "parkinglot-afbaf.firebaseapp.com",

  "databaseURL": "https://parkinglot-afbaf-default-rtdb.firebaseio.com",

  "storageBucket": "parkinglot-afbaf.appspot.com",

}
firebase = pyrebase.initialize_app(config)
db = firebase.database()
id = 1

# select the video source; 0 - integrated webcam; 1 - external webcam;
VIDEO_SOURCE = 1

# start the recording
cap = cv2.VideoCapture(VIDEO_SOURCE)
suc, image = cap.read()

# save first frame as JPEG file
cv2.imwrite("frame0.jpg", image)
img = cv2.imread("frame0.jpg")

# get the regions of interest/parking spots
r = cv2.selectROIs('ROI Selector', img, showCrosshair=False, fromCenter=False)

# convert the result to a list
print(r)
rlist = r.tolist()
print(rlist)


for i in rlist:
    data={"Id":id,"Status":"Available","Entry_time": "99:99:99", "Exit_time": "99:99:99", "Date": 0}
    index = 'p'+ str(id)
    db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child(index).child(1).set(data)
    id= id + 1
    
print(id)
   
# write the list into a csv file
with open('data/rois.csv', 'w', newline='') as outf:
  csvw = csv.writer(outf)
  csvw.writerows(rlist)

# when everything is done, release the capture
cap.release()
cv2.destroyAllWindows()
