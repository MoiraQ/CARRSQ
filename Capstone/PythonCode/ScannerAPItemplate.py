# ScanerStudio Interface 1.7
# Programmed by S. Demmel -- version 2.0
import os, sys

# required to find the DLL
os.chdir(os.path.abspath('../../../bin/win32/vs2013'))
# need to have scaner.py in the same folder as this code
from scaner import *

parser = ScanerApiOption()
(options, args) = parser.parse_args()
Process_InitParams(options.process_name, options.configuration, options.frequency)
# here we register all the messages we want to use
Com_registerEvent('Network/IUser/ExportChannel',-1)
Com_registerEvent('Network/IVehicle/VehicleUpdate',0)
Com_registerEvent('Network/IVehicle/VehicleSetMaxSpeed',0)
# here we create the custom input & output data interface (note NOT for the export channels) - 0 means for vehicle 0, the one with the human driver
inVehicleData = Com_declareInputData("Network/IVehicle/VehicleUpdate",0)
outMaxSpeed = Com_declareOutputData("Network/IVehicle/VehicleSetMaxSpeed",0)

status = PS_DAEMON
try:
    # Generally the socket connection operations will be located here

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
                
            if old_status == PS_LOADED and status == PS_DAEMON:
                # this means the scenario has been stopped after it ran
                
            if old_status == PS_READY and status == PS_RUNNING:
                # this means the scenario has started
                
        if status == PS_RUNNING:
            if Com_updateInputs(UT_AllData) == False:  # this is only necessary if any Com_declareInputData has been declared
                print "Error updating custom inputs"
            else:
                # here you can read data from your custom inputs
                # note this can also be done in separate functions (e.g. something triggered by a message received on a socket)
                # as long as you have Com_updateInputs(UT_AllData) first then Com_updateOutputs(UT_AllData) at the end





            # Event dequeuing - used for reading the export channels
            event = Com_getNextEvent()
            while event:
                evtType = Com_getTypeEvent(event)
                if evtType == ET_message:
                    dEventDataInterf = Com_getMessageEventDataInterface(event)
                    msg_name = Com_getMessageEventDataStringId(event)
                    if 'Network/IUser/ExportChannel' in msg_name:
                        # get the export channel ID
                        exChanId = Com_getShortData(dEventDataInterf,'no')
                        # here you read the info contained in the export channel and for example send it to the mobile device via a socket

                    # get the next message
                    event = Com_getNextEvent()

            # Update the custmo output interface(s) - no need to do anything if it returns true
            if Com_updateOutputs(UT_AllData) == False:
                print "Error updating custom outputs"          
    print 'Status is ', state_string(status)
    # here if status is = to PS_DEAD
    # Need to close any open sockets and do other cleaning here
    


except KeyboardInterrupt:
    print 'Keyboard interrupt'
    Process_Close()
    # Need to close any open sockets and do other cleaning here 
    
except:
    print "Unexpected error:", sys.exc_info()[0]
    Process_Close()
    raise
    # Need to close any open sockets and do other cleaning here
