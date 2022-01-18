# importing everything we need
import cv2
import csv
import pyrebase
import datetime as dt
#import pytesseract

#pytesseract.pytesseract.tesseract_cmd = "C:/Program Files/Tesseract-OCR/tesseract.exe"
# getting the spots coordinates into a list

# declaring a static variable
class spots:
    loc = 0
config = {

   "apiKey": "AIzaSyB2zU61VJoRTxMGdx8xFdvsKhCv-LSgl_k",

  "authDomain": "parkinglot-afbaf.firebaseapp.com",

  "databaseURL": "https://parkinglot-afbaf-default-rtdb.firebaseio.com",

  "storageBucket": "parkinglot-afbaf.appspot.com",

}
firebase = pyrebase.initialize_app(config)
db = firebase.database()
id = 1

# function to determine if a spot is free/occupied
# params: image source, individual spot coordinates
def drawRectangle(img, a, b, c, d):
    global id 
    # cutting the image based on the coodrinates
    sub_img = img[b:b + d, a:a + c]
    cv2.imwrite("frame1.jpg", sub_img)
    #configuration of pytesseract
    #custom_config = r'--oem 3 --psm 6'
    
    
    # extracting the edges
    edges = cv2.Canny(sub_img, lowThreshold, highThreshold)
    # counting the white pixels
    pix = cv2.countNonZero(edges)
    # testing if the pixels number is in the given range
    if pix in range(min, max):
        # drawing a green rectangle on the source image using the given coordinates
        # and increasing the number of available spots
        cv2.rectangle(img, (a, b), (a + c, b + d), (0, 255, 0), 3)
        spots.loc += 1
       
      #getting current time and date
        str_datetime = str(dt.datetime.now())
        time= str_datetime[11:]
        date = str_datetime[0:10]
        
     #Fetch data from the firebase and identify the last sub child 
        val1=db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child("p" + str(id)).shallow().get()
        print("if","p" + str(id),"pix: ", pix,val1.val())
        value1=list(val1.val())
        #print(value1)
        int_map= map(int,value1)
        int_list = list(int_map)
        int_list.sort()
        print(int_list)
        sub_id = int_list[-1]
        print(sub_id)

    #Fetch entry time and exit time from the firebase        
        data1 = db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child("p" + str(id)).child(sub_id).get()
        v1=data1.key()


        var=db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child("p" + str(id)).child(v1).get()
        #print(var.val())
        
        
        values=""
        for x in var.each():
            values=values+str(x.val())+","
                       
        print(values)
        
        
        list2=(values.split(','))
        list_2= list2[0:-1]
        print(list_2)
        print(list2)
        entrytime=list2[1]
        exittime=list2[2]
        status=list2[4]
        print('exit', exittime)
        #data_list = list_2[0] + ","+"" + list_2[1] + ","+"" + str(list_2[2]) + ","+"" + str(list_2[3]) + ","+"" + list_2[4] + ","+"" + list_2[5]
        #print(data_list)
       # print(list(data_list))
        print("entry",entrytime," exit ", exittime,"current time",time)
        
        
        
        if exittime < time:
            #create the new sub child in the specific slot where the status change from Available to Not Available
            s_id = int(sub_id)
            s_id = s_id + 1
            with open('data/slots_data.csv', "a", newline='') as csv_file:
                writer = csv.writer(csv_file, delimiter=',')
                writer.writerow(list_2)
                    
            data={"Id":id,"Status":"Available","Entry_time": time, "Exit_time": "99:99:99", "Date": date}
            index = 'p'+ str(id)
            db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child(index).child(str(s_id)).set(data)
        elif status=='Booked':
            data = {"Id": id,"Status":"Booked", "Entry_time": time, "Date": date}
            db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child("p" + str(id)).child(sub_id).update(data)
            
        else: 
            #update the entry time continuesly if the slot staus is available
            data = {"Id": id,"Status":"Available", "Entry_time": time, "Date": date}
            db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child("p" + str(id)).child(sub_id).update(data)
        
        id = id + 1
        if id==7:
            id=1
        
        
        
    else:
        
        
        # drawing a red rectangle on the source image if the pixels number is not in the range
        cv2.rectangle(img, (a, b), (a + c, b + d), (0, 0, 255), 3)
        str_datetime = str(dt.datetime.now())
        time= str_datetime[11:]
        date = str_datetime[0:10]
        #text = pytesseract.image_to_string('frame1.jpg', config=custom_config)
        #print(text)
        
      #Fetch data from the firebase and identify the last sub child 
        val1=db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child("p" + str(id)).shallow().get()
        print("else","p" + str(id),"pix: ", pix, val1.val())
        value1=list(val1.val())
        int_map= map(int,value1)
        int_list = list(int_map)
        int_list.sort()
        print(int_list)
        sub_id = int_list[-1]
        print(sub_id)
        
        
        #updating status & exit time on the last child of the specific slot 
        data = {"Id": id,"Status":"Not Available", "Exit_time": time, "Date": date}
        db.child("Faisalabad").child("Imtiaz Mart").child("ParkingSlots").child("p" + str(id)).child(sub_id).update(data)
        
#print(var1._getitem_(1))
        id = id + 1
        if id==7:
            id=1

# empty callback function for creating trackar
def callback(foo):
    pass


# getting the spots coordinates into a list
with open('data/rois.csv', 'r', newline='') as inf:
    csvr = csv.reader(inf)
    rois = list(csvr)
    print(rois)
# converting the values to integer
rois1 = [[int(float(j)) for j in i] for i in rois]
print(rois1)
# creating the parameters window with trackbars
cv2.namedWindow('parameters')
cv2.createTrackbar('Threshold1', 'parameters', 186, 700, callback)
cv2.createTrackbar('Threshold2', 'parameters', 122, 700, callback)
cv2.createTrackbar('Min pixels', 'parameters', 0, 1500, callback)
cv2.createTrackbar('Max pixels', 'parameters', 323, 1500, callback)

# select the video source; 0 - integrated webcam; 1 - external webcam;
VIDEO_SOURCE = 1
cap = cv2.VideoCapture(VIDEO_SOURCE)

# start the live feed
while True:
    # set the number of spots to 0
    spots.loc = 0

    # set two frames for the feed
    ret, frame = cap.read()
    ret2, frame2 = cap.read()

    # define the range of pixels and the thresholds for Canny function
    min = cv2.getTrackbarPos('Min pixels', 'parameters')
    max = cv2.getTrackbarPos('Max pixels', 'parameters')
    lowThreshold = cv2.getTrackbarPos('Threshold1', 'parameters')
    highThreshold = cv2.getTrackbarPos('Threshold2', 'parameters')

    # apply the function for every list of coordinates
    for i in range(len(rois1)):
        drawRectangle(frame, rois1[i][0], rois1[i][1], rois1[i][2], rois1[i][3])

    # adding the number of available spots on the shown image
    font = cv2.FONT_HERSHEY_SIMPLEX
    cv2.putText(frame, 'Available spots: ' + str(spots.loc), (10, 30), font, 1, (0, 255, 0), 3)
    cv2.imshow('frame', frame)

    # displaying the image with Canny function applied for reference
    canny = cv2.Canny(frame2, lowThreshold, highThreshold)
    cv2.imshow('canny', canny)

    # listen for 'Q' key to stop the stream
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# when everything is done, release the capture
cap.release()
cv2.destroyAllWindows()
