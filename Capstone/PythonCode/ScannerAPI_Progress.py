# ScanerStudio Interface 1.7
# Programmed by S. Demmel -- version 2.0
import os, sys, socket

# required to find the DLL
os.chdir(os.path.abspath('../../../bin/win32/vs2013'))
# need to have scaner.py in the same folder as this code
from scaner import *

PORT = 39501 # should change to static port for QUT1
HOST = '131.181.139.10' #put the address of the computer running this code

CONN_TIMEOUT = 50 # connection timeout in milliseconds for reading input data
INPUT_BYTES = 1024 # number of bytes to read of the input data
NUM_CONN = 1
KMH_TO_MS = (1/3.6) # multiply a km/h variable by this to get m/s
MS_TO_KMH = 3.6 # multiply a m/s variable by this to get km/h

# These have to match up with the data stream sent from the app
STATUS_STRING = "switchstatus"
SPEED_STRING = "changeSpeed"

parser = ScanerApiOption()
(options, args) = parser.parse_args()
Process_InitParams(options.process_name, options.configuration, options.frequency)
# here we register all the messages we want to use
Com_registerEvent('Network/IUser/ExportChannel',-1)
Com_registerEvent('Network/IVehicle/VehicleUpdate',0)
Com_registerEvent('Network/IVehicle/VehicleSetMaxSpeed',0)
Com_registerEvent('Network/IVehicle/VehicleSetAutonomousMode',0)
Com_registerEvent('Network/IVehicle/VehicleLaneLateralShift', 0)
# here we create the custom input & output data interface (note NOT for the export channels) - 0 means for vehicle 0, the one with the human driver
inVehicleData = Com_declareInputData("Network/IVehicle/VehicleUpdate",0)
outMaxSpeed = Com_declareOutputData("Network/IVehicle/VehicleSetMaxSpeed",0)
customAutoMode = Com_declareOutputData("Network/IVehicle/VehicleSetAutonomousMode",0)
CustomVehicleLaneShift = Com_declareOutputData("Network/IVehicle/VehicleLaneLateralShift", 0)

status = PS_DAEMON
isAutoDriving = False


def ChangeStatus():
    global isAutoDriving
    isAutoDriving = not isAutoDriving # toggle between true and false
    print str(isAutoDriving)
    
    # In python 2.X it is not guaranteed that 1 = True and 0 = False
    driveType = 0
    if isAutoDriving: 
        driveType = 1
    else:
        driveType = 0
    print str(driveType)
    
    Com_updateInputs(UT_AllData)
    #Com_setShortData(customAutoMode,'vhlId',0)
    #Com_setLongData(customAutoMode,'control',2)
    #Com_setLongData(customAutoMode,'activation',driveType)
	
	
    Com_setShortData(CustomVehicleLaneShift, 'vhlId', 0)
    Com_setShortData(CustomVehicleLaneShift, 'shiftType', 0)
    Com_setFloatData(CustomVehicleLaneShift, 'shiftValue', 0)
    Com_setFloatData(CustomVehicleLaneShift, 'timeToReachShift', 3)
    Com_setShortData(CustomVehicleLaneShift, 'KeepShift', 0)
	
    Com_updateOutputs(UT_AllData)


def ChangeSpeed(kmSpd):
    #kmSpd = str(round(Com_getFloatData(inVehicleData, 'speed[0]')*MS_TO_KMH),2) # current spd of car in sim??
    msSpd = kmSpd * KMH_TO_MS
    
    Com_updateInputs(UT_AllData)
    Com_setShortData(outMaxSpeed, 'vhlId', 0)
    Com_setFloatData(outMaxSpeed, 'maxSpeed', msSpd)
	
	

	
	
    Com_updateOutputs(UT_AllData)


try:
    # Generally the socket connection operations will be located here

    #create socket connection for tablet
    #this is the server
    s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    s.bind((HOST,PORT))
    s.listen(NUM_CONN)
    sc,ipAd = s.accept()
    #sc.setblocking(0)
    print 'Successful connection to socket for ',ipAd


    # Start the loop managing the interface with Scaner
    while status != PS_DEAD:
        # Process manager Run 
        Process_Run()
        Process_Wait()
        #Process manager State
        old_status = status
        status = Process_GetState()
        if status != old_status:
            if old_status == PS_RUNNING and status == PS_DAEMON:
                # this means the modules have been stopped
                print 'modules have been stopped'
                sc.close()
                s.close()
                
            if old_status == PS_LOADED and status == PS_DAEMON:
                # this means the scenario has been stopped after it ran
                print 'scenario has been stopped after running'
                sc.close()
                s.close()
                
            if old_status == PS_READY and status == PS_RUNNING:
                # this means the scenario has started
                print 'scenario has started'
                
        if status == PS_RUNNING:
            if Com_updateInputs(UT_AllData) == False:  # this is only necessary if any Com_declareInputData has been declared
                print "Error updating custom inputs"
            else:
                # here you can read data from your custom inputs
                # note this can also be done in separate functions (e.g. something triggered by a message received on a socket)
                # as long as you have Com_updateInputs(UT_AllData) first then Com_updateOutputs(UT_AllData) at the end
                
                #sc.settimeout(CONN_TIMEOUT)
                data = sc.recv(INPUT_BYTES)

                if not data:
                    break

                if STATUS_STRING in data:
                    ChangeStatus()
                    print "status has been changed"

                elif SPEED_STRING in data:
                    spd = data.split('+') # spd becomes a list?
                    ChangeSpeed(spd[1])
#print "Socket timed out"




##            # Event dequeuing - used for reading the export channels
##            event = Com_getNextEvent()
##            data_msg = "DATA;"
##            while event:
##                evtType = Com_getTypeEvent(event)
##                if evtType == ET_message:
##                    dEventDataInterf = Com_getMessageEventDataInterface(event)
##                    msg_name = Com_getMessageEventDataStringId(event)
##                    if 'Network/IUser/ExportChannel' in msg_name:
##                        # get the export channel ID
##                        exChanId = Com_getShortData(dEventDataInterf,'no')
##                        # here you read the info contained in the export channel and for example send it to the mobile device via a socket
##
##                        # I don't know how to do this
##                        # 0 = frame
##                        # 1 = task
##                        # 2 = brake pedal
##                        # 3 = gas pedal
##                        # 4 = headway to next vehicle in lane
##                        # 5 = speed
##                        # 6 = distance to closest light (or light of interest defined by script)
##                        # 7 = scenerio code
##
##                        if exChanId == 0:
##                            # respond to channel 0 with TIME message
##                            exportTime = Com_getFloatData(dEventDataInterf, 'val')
##                            time_msg = "TIME;" + str(exportTime) + "\r\n"
##
##                            sc.sendall(time_msg)
##
##                        elif exChanId < 8:
##                            data_msg += str(Com_getFloatData(dEventDataInterf, 'val'))
##                            data_msg += str(";")
##
##                    # get the next message
##                    event = Com_getNextEvent()
##
##            data_msg += "\r\n"
##            sc.sendall(data_msg) # send data message after event

            # Update the custmo output interface(s) - no need to do anything if it returns true
            if Com_updateOutputs(UT_AllData) == False:
                print "Error updating custom outputs"          
    print 'Status is ', state_string(status)
    # here if status is = to PS_DEAD
    # Need to close any open sockets and do other cleaning here
    print 'Process status DEAD, closing socket'
    sc.close()
    s.close()
    


except KeyboardInterrupt:
    print 'Keyboard interrupt'
    Process_Close()
    # Need to close any open sockets and do other cleaning here
    sc.close()
    s.close()
    
except:
    print "Unexpected error:", sys.exc_info()[0]
    Process_Close()
    raise
    # Need to close any open sockets and do other cleaning here
    sc.close()
    s.close()


# Used these files as references
# C:\OKTAL\SCANeRstudio_1.6\APIs\samples\ScanerAPI\python\OperatorGUI-ORIGINALWORKING.py
# C:\OKTAL\SCANeRstudio_1.6\APIs\samples\ScanerAPI\python\ecodriving-withLED.py
# C:\OKTAL\SCANeRstudio_1.6\APIs\samples\ScanerAPI\python\csvLogger.py
# C:\OKTAL\SCANeRstudio_1.6\APIs\samples\ScanerAPI\python\selfdrivingSIMULATION.py
