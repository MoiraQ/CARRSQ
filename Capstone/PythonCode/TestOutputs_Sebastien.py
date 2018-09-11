# ScanerStudio Interface 1.7
# Programmed by S. Demmel -- version 2.0
import os, sys, time
# required to find the DLL
os.chdir(os.path.abspath('../../../bin/win32/vs2013'))
# need to have scaner.py in the same folder as this code
from scaner import *

parser = ScanerApiOption()
(options, args) = parser.parse_args()
Process_InitParams(options.process_name, options.configuration, options.frequency)

#Com_registerEvent('Network/IUser/ExportChannel',-1)
Com_registerEvent('Network/IVehicle/VehicleUpdate',0)
Com_registerEvent('Network/IVehicle/VehicleSetSpeedObligatory',0)
inData = Com_declareInputData("Network/IVehicle/VehicleUpdate",0)
outData = Com_declareOutputData("Network/IVehicle/VehicleSetSpeedObligatory",0)

startTime = []
sdone = False
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
                print "Modules have been stopped"                
            if old_status == PS_LOADED and status == PS_DAEMON:
                print "Scenario has stopped"
            if old_status == PS_READY and status == PS_RUNNING:
                print "Scenario has started"
                startTime = time.time()
        if status == PS_RUNNING:
            if Com_updateInputs(UT_AllData) == False:  # this is only necessary if any Com_declareInputData has been declared
                print "Error updating custom inputs"
            else:
                speed = Com_getFloatData(inData,'speed[0]')
                print "Speed = ",speed
                if time.time()-startTime>=5 and not sdone:
                    print "Setting speed obligatory"
                    # work if the vehicle is simple and process is TRAFFIC
                    Com_setShortData(outData,'vhlId', 0)
                    Com_setFloatData(outData,'speed',25.0)
                    Com_setCharData(outData,'state',1) # 1 = ON, 0 = OFF
                    Com_setFloatData(outData,'smoothingTime',1)
                    sdone = True
                    #Com_updateOutputs(UT_AllData)

            if Com_updateOutputs(UT_AllData) == False:
                print "Error updating custom outputs"          
    print 'Status is ', state_string(status)
    # Need to close any open sockets and do other cleaning here
    Process_Close()

except KeyboardInterrupt:
    print 'Keyboard interrupt'
    Process_Close()
    # Need to close any open sockets and do other cleaning here 
    
except:
    print "Unexpected error:", sys.exc_info()[0]
    Process_Close()
    raise
    # Need to close any open sockets and do other cleaning here
