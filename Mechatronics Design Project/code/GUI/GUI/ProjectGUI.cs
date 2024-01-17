// Curtin University
// Mechatronics Engineering
// Serial I/O Card - Sample GUI Code

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO.Ports;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Xml.Linq;

namespace SerialGUISample
{

    public partial class Form1 : Form
    {

        // enum of byte for port control
        private enum CART_MODES : byte
        {
            unavailable = 0x00,
            standby = 0x01,
            manual = 0x02,
            auto_line_follow = 0x03,
            tuning = 0x04,
            save_data_to_eeprom = 0x05,
            load_data_from_eeprom = 0x06
        }

        private enum CONTROLLER_MODES : byte
        {
            unavailable = 0x00,
            standby = 0x01,
            manual = 0x02
        }

        private CART_MODES cart_mode = CART_MODES.unavailable;
        private CONTROLLER_MODES controller_mode = CONTROLLER_MODES.unavailable;

        // declare constants
        const byte PID_MESSAGE_LENGTH = 9;    
        const byte SENSOR_MESSAGE_LENGTH = 4;   // change this value as sensor byte array increases in length
        const byte CONTROLLER_IN_MESSAGE_LENGTH = 5;
        const byte CART_STANDARD_MESSAGE_OUT_LENGTH = 4; // this handles all other modes
        const byte CART_MANUAL_MESSAGE_OUT_LENGTH = 6;
        const byte CART_LOAD_MESSAGE_OUT_LENGTH = 5;
        const byte CART_SAVE_MESSAGE_OUT_LENGTH = 8;
        const byte CART_TUNING_MESSAGE_OUT_LENGTH = 9;
        const byte ESTOP_MESSAGE_LENGTH = 1;
        const byte STRING_MESSAGE_LENGTH = 64;  // 
        const byte BYTE_START = 0xFF;           // define byte message start byte
        const byte STRING_START = 0xFE;         // define start byte for string message from micro.
        const byte EMERGENCY_STOP = 0xFD;       // define e stop byte to be sent to micro
        const byte PID_DATA_SAVED = 0xFC;
        const byte PID_DATA_LOADED = 0xFB;
        const byte AUTO_MODE_DATA_REPORT = 0xFA; 
        const byte CHECKSUM_OK = 0xF1;          // return from micro checksum is okay
        const byte CHECKSUM_ERROR = 0xF1;       // checksum error notifiction from micro
        const byte ERROR_REPEAT = 0x0F;         // received error in checksum from micro, repeat last message.
        const byte ZERO = 0;                    // define zero byte


        // Declare variables to store inputs and output
        byte cart_in_message_type = 0;
        byte controller_in_message_type = 0;
        byte cart_output_message_length = 0;
        byte cart_input_message_length = 0;
        byte controller_input_message_length = 0;
        bool new_incoming_cart_message = false;
        bool new_incoming_controller_message = false;
        byte[] Cart_Output_Array = new byte[CART_TUNING_MESSAGE_OUT_LENGTH];
        byte[] Cart_Input_Array = new byte[100];
        byte[] Controller_Output_Array = new byte[100];
        byte[] Controller_Input_Array = new byte[100];

        public Form1()
        {
            // Initialize required for form controls.
            InitializeComponent();
            cartSerialPortSelect.Items.AddRange(SerialPort.GetPortNames()); // get connected port names
            CartSerial.DataReceived += new SerialDataReceivedEventHandler(cartSerial_DataReceived); // add the event handlers to the serial port object
            CartSerial.PinChanged += new SerialPinChangedEventHandler(cartSerial_PinChanged);
            controllerSerialPortSelect.Items.AddRange(SerialPort.GetPortNames()); // get connected port names
            ControllerSerial.DataReceived += new SerialDataReceivedEventHandler(controllerSerial_DataReceived); // add the event handlers to the serial port object
            ControllerSerial.PinChanged += new SerialPinChangedEventHandler(controllerSerial_PinChanged);


            // if any active serial devices
            if (cartSerialPortSelect.Items.Count > 0)
            {
                cartSerialPortSelect.SelectedIndex = 0; // set the first active port to cart port

                if (cartSerialPortSelect.Items.Count > 1)
                {
                    controllerSerialPortSelect.SelectedIndex = 1; // set the second active port to controller port
                }
            }
        }

        // Send a message to the cart via serial.
        private void cartSerialWrite(byte[] inData, byte messageLength)
        {

            if (CartSerial.IsOpen)
            {
                CartSerial.Write(inData, 0, messageLength);         //Send all bytes to the IO card.                      
            }

        }


        /*****************************************************
        *   Section for all cart communication functions     
        *****************************************************/

        // send control bytes to microcontroller in the following format:
        // startByte,msgLength,controlMode,DACLeftValue,DACRightValue,kP,kI,kD,checksum
        // note checkSum is calculated in these message sending functions to avoid
        // confusion on different message lengths and formats.
        private void btnUpdateCart_Click(object sender, EventArgs e)
        {
            byte checkSum = 0;

          
            // switch on what mode we are have selected
            // to construct the message array
            switch (cart_mode)
            {

                case CART_MODES.unavailable:
                case CART_MODES.standby:
                case CART_MODES.auto_line_follow:
                    cart_output_message_length = CART_STANDARD_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[0] = BYTE_START;
                    Cart_Output_Array[1] = CART_STANDARD_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[2] = ((byte)cart_mode);
                    break;

                case CART_MODES.load_data_from_eeprom:
                    cart_output_message_length = CART_LOAD_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[0] = BYTE_START;
                    Cart_Output_Array[1] = CART_LOAD_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[2] = ((byte)cart_mode);
                    Cart_Output_Array[3] = ((byte)comboBoxEEPROMAddress.SelectedIndex);
                break;

                case CART_MODES.save_data_to_eeprom:
                    cart_output_message_length = CART_SAVE_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[0] = BYTE_START;
                    Cart_Output_Array[1] = CART_SAVE_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[2] = ((byte)cart_mode);
                    Cart_Output_Array[3] = ((byte)comboBoxEEPROMAddress.SelectedIndex);
                    Cart_Output_Array[4] = (byte)upDownKP.Value;
                    Cart_Output_Array[5] = (byte)upDownKI.Value;
                    Cart_Output_Array[6] = (byte)upDownKD.Value;
                break;

                case CART_MODES.manual:
                    cart_output_message_length = CART_MANUAL_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[0] = BYTE_START;
                    Cart_Output_Array[1] = CART_MANUAL_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[2] = ((byte)cart_mode);
                    Cart_Output_Array[3] = (byte)upDownLeftDAC.Value;
                    Cart_Output_Array[4] = (byte)upDownRightDAC.Value;
                break;


                case CART_MODES.tuning:
                    cart_output_message_length = CART_TUNING_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[0] = BYTE_START;
                    Cart_Output_Array[1] = CART_TUNING_MESSAGE_OUT_LENGTH;
                    Cart_Output_Array[2] = ((byte)cart_mode);
                    Cart_Output_Array[3] = (byte)upDownLeftDAC.Value;
                    Cart_Output_Array[4] = (byte)upDownRightDAC.Value;
                    Cart_Output_Array[5] = (byte)((upDownKP.Value * 80) - 1);
                    Cart_Output_Array[6] = (byte)((upDownKI.Value * 80) -1);
                    Cart_Output_Array[7] = (byte)((upDownKD.Value * 80) - 1);
                    break;
            }
            
            // calculate checksum
            int ii = 0;
            String output = "";
            for (ii = 0; ii < cart_output_message_length - 1; ii++)
            {
                checkSum += Cart_Output_Array[ii];
                output += Cart_Output_Array[ii].ToString() + ", ";
            }

            output += checkSum + "\r\n";

            outgoingCartSerialDisplay.AppendText(output);
            Cart_Output_Array[cart_output_message_length - 1] = checkSum;  
            cartSerialWrite(Cart_Output_Array, (byte)cart_output_message_length);

        }


        // Send a message to the controller via serial.
        private void controllerSerialWrite(byte[] inData, byte message_length)
        {

            if (ControllerSerial.IsOpen)
            {
                ControllerSerial.Write(inData, 0, message_length);         //Send all bytes to the IO card.                      
            }

        }

        private void getIOtimer_Tick(object sender, EventArgs e) //It is best to continuously check for incoming data as handling the buffer or waiting for event is not practical in C#.
        {

            if (cartSerialPortSelect.Items.Count == 0)
            {
                cartSerialPortSelect.Items.AddRange(SerialPort.GetPortNames()); // get connected port names
                cartSerialPortSelect.Text = null;
                controllerSerialPortSelect.Items.AddRange(SerialPort.GetPortNames());
                controllerSerialPortSelect.Text = null;
            }
        }

        private void btnCartSerialConnect_Click(object sender, EventArgs e)
        {

            // Establish connection with serial
            if (!CartSerial.IsOpen)                                  // Check if the serial has been connected.
            {
                try
                {
                    CartSerial.PortName = cartSerialPortSelect.Text;
                    cartSerialStatus.Value = 100;
                    CartSerial.Open();                               //Try to connect to the serial.
                    invertCartSerialWidgets();
                    cart_mode = CART_MODES.standby;
                }
                catch
                {
                    cartSerialStatus.Value = 0;
                    cart_mode = CART_MODES.unavailable;
                    outgoingCartSerialDisplay.AppendText("Failed to connect to cart!\r\n");
                }
            }

        }

        private void btnControllerSerialConnect_Click(object sender, EventArgs e)
        {
            // Establish connection with serial
            if (!ControllerSerial.IsOpen)                                  // Check if the serial has been connected.
            {
                try
                {
                    ControllerSerial.PortName = controllerSerialPortSelect.Text;
                    controllerSerialStatus.Value = 100;
                    ControllerSerial.Open();                               //Try to connect to the serial.
                    invertControllerSerialWidgets();
                    btnManual.Enabled = true;
                    controller_mode = CONTROLLER_MODES.standby;
                }
                catch
                {
                    controllerSerialStatus.Value = 0;
                    controller_mode = CONTROLLER_MODES.unavailable;
                    outgoingControllerSerialDisplay.AppendText("Failed to connect to controller!\r\n");
                }
            }
        }

        private void btnCartSerialDisconnect_Click(object sender, EventArgs e)
        {
            cartSerialStatus.Value = 0;
            CartSerial.Close();
            cartSerialPortSelect.Items.Clear();
            cartSerialPortSelect.Items.AddRange(SerialPort.GetPortNames()); // get connected port names
            cart_mode = CART_MODES.unavailable;
            invertCartSerialWidgets();
        }

        private void btnControllerSerialDisconnect_Click(object sender, EventArgs e)
        {
            controllerSerialStatus.Value = 0;
            ControllerSerial.Close();
            controllerSerialPortSelect.Items.Clear();
            controllerSerialPortSelect.Items.AddRange(SerialPort.GetPortNames()); // get connected port names
            controller_mode = CONTROLLER_MODES.unavailable;
            invertControllerSerialWidgets();
            btnManual.Enabled=false;
        }


        // reverse states of availaiable controls on connecting/disconnecting the cart
        private void invertCartSerialWidgets()
        {
            cartSerialPortSelect.Enabled = !cartSerialPortSelect.Enabled;
            btnCartSerialConnect.Enabled = !btnCartSerialConnect.Enabled;
            btnCartSerialDisconnect.Enabled = !btnCartSerialDisconnect.Enabled; 
            outgoingCartSerialDisplay.Enabled = !outgoingCartSerialDisplay.Enabled;
            btnCartOutgoingClear.Enabled = !btnCartOutgoingClear.Enabled;
            incommingCartSerialDisplay.Enabled = !incommingCartSerialDisplay.Enabled;
            btnIncommingCartClear.Enabled = !btnIncommingCartClear.Enabled;
            groupBoxControlMode.Enabled = !groupBoxControlMode.Enabled;
            btnESTOP.Enabled = !btnESTOP.Enabled;
        }

        // reverse states of availaiable controls on connecting/disconnecting the controller
        private void invertControllerSerialWidgets()
        {
            btnControllerSerialConnect.Enabled = !btnControllerSerialConnect.Enabled;
            controllerSerialPortSelect.Enabled = !controllerSerialPortSelect.Enabled;
            btnControllerSerialDisconnect.Enabled = !btnControllerSerialDisconnect.Enabled;
            outgoingControllerSerialDisplay.Enabled = !outgoingControllerSerialDisplay.Enabled;
            incommingControllerSerialDisplay.Enabled = !incommingControllerSerialDisplay.Enabled;
            btnOutgoingControllerClear.Enabled = !btnOutgoingControllerClear.Enabled;
            btnIncommingControllerClear.Enabled = !btnIncommingControllerClear.Enabled;


        }


        // this function called by the serial event handler.
        // first byte indicates message type.
        private void cartSerial_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {

            if (CartSerial.IsOpen)
            {
                if (CartSerial.BytesToRead >= 2 && !new_incoming_cart_message) // check the message type and message length have been received
                {
                    new_incoming_cart_message = true;
                    cart_in_message_type = (byte)CartSerial.ReadByte();
                    cart_input_message_length = (byte)CartSerial.ReadByte();
                    cart_input_message_length -= (byte)0x02;
                    Console.Write(cart_in_message_type.ToString() + ", " + cart_input_message_length.ToString() + "\r\n");

                }
                else if (CartSerial.BytesToRead >= cart_input_message_length)
                {
                    new_incoming_cart_message = false;
                    this.Invoke(new EventHandler(handleCartSerialIn));
                }
            }
        }

        private void handleCartSerialIn(object sender, EventArgs e)
        {
            switch ((byte)cart_in_message_type)
            {
                // read a byte input message from the cart
                case (byte)BYTE_START:
                    String message = "";
                    for (int i = 0; i < cart_input_message_length - 1; i++)
                    {
                        Cart_Input_Array[i] = (byte)CartSerial.ReadByte(); // cast incoming data to char and add to message string
                        message += Cart_Input_Array[i].ToString() + ", ";
                    }

                    Cart_Input_Array[cart_input_message_length - 1] = (byte)CartSerial.ReadByte();
                    message += Cart_Input_Array[cart_input_message_length - 1].ToString() + "\r\n";

                    byte cart_control_byte = Cart_Input_Array[0];

                    switch (cart_control_byte)
                    {
                        case PID_DATA_LOADED:
                            
                            // update the PC variables
                            upDownKP.Value = Cart_Input_Array[1];
                            upDownKI.Value = Cart_Input_Array[2];
                            upDownKD.Value = Cart_Input_Array[3];

                            incommingCartSerialDisplay.AppendText("PID data loaded...\r\n");
                            incommingCartSerialDisplay.AppendText(message + "\r\n");
                            
                        break;

                        case PID_DATA_SAVED:

                            incommingCartSerialDisplay.AppendText("PID data saved...\r\n");
                            incommingCartSerialDisplay.AppendText(message + "\r\n");
                        
                        break;

                        case AUTO_MODE_DATA_REPORT:

                            // message format = byte message start, message length, control byte, left sensor byte, right sensor byte, left DAC value, right DAC value 
                            //int leftSensorReading, rightSensorReading
                            byte leftSensorReading, rightSensorReading, leftDACValue, rightDACValue;
                            String output = "";


                            // read in the 2 16 bit sensor reading as the high and low bytes and merge to 16 bit values
                            // then read in the rest of the message
                            leftSensorReading = Cart_Input_Array[1];
                            //leftSensorReading <<= 0x08;
                            //leftSensorReading += (byte)CartSerial.ReadByte();
                            output += "Left sensor = " + leftSensorReading.ToString() + "\r\n";
                            rightSensorReading = Cart_Input_Array[2];
                            //rightSensorReading <<= 0x08;
                            //rightSensorReading += (byte)CartSerial.ReadByte();
                            output += ", Right sensor = " + rightSensorReading.ToString() + "\r\n";
                            
                            // read in the DAC values
                            leftDACValue = Cart_Input_Array[3];
                            output += "Left DAC Value = " + leftDACValue.ToString() + "\r\n";
                            rightDACValue = Cart_Input_Array[4];
                            output += "right DAC Value = " + rightDACValue.ToString() + "\r\n";

                            incommingCartSerialDisplay.AppendText(output);

                            break;
                    }

                break;

                // read a string input message from the cart
                case (byte)STRING_START:

                    String inMessage = "";


                    for (int i = 0; i < cart_input_message_length; i++)
                    {
                        inMessage += (char)CartSerial.ReadByte(); // cast incoming data to char and add to message string
                    }

                    incommingCartSerialDisplay.AppendText((String)inMessage); // display message
                    cart_in_message_type = 0x00; // reset message type
                    cart_input_message_length = 0x00; // reset message length
                    break;
            }

        }

        // this function called by the serial event handler.
        // first byte indicates message type.
        private void controllerSerial_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {

            if (ControllerSerial.IsOpen)
            {
                if (ControllerSerial.BytesToRead >= 2 && !new_incoming_controller_message) // check the message type and message length have been received
                {
                    new_incoming_controller_message = true;
                    controller_in_message_type = (byte)ControllerSerial.ReadByte();
                    controller_input_message_length = (byte)ControllerSerial.ReadByte();
                    controller_input_message_length -= (byte)0x02;
                    Console.Write(controller_in_message_type.ToString() + ", " + controller_input_message_length.ToString() + "\r\n");

                }
                else if (ControllerSerial.BytesToRead >= controller_input_message_length)
                {
                    new_incoming_controller_message = false;
                    this.Invoke(new EventHandler(handleControllerSerialIn));
                }
            }
        }

        private void handleControllerSerialIn(object sender, EventArgs e)
        {

            switch ((byte)controller_in_message_type)
            {
                // read a byte input message
                // not impilmented yet, will be used for sensor input
                case (byte)BYTE_START:
                    // convert the x and y ADC values to the DAC left and right values and forward to the cart micro if mode is manual.

                    break;

                // read a string input message
                case (byte)STRING_START:

                    String inMessage = "";

                    for (int i = 0; i < controller_input_message_length; i++)
                    {
                        inMessage+= (char)ControllerSerial.ReadByte(); // cast incoming data to char and add to message string
                    }
                    Console.Write(inMessage + "\r\n");
                    incommingControllerSerialDisplay.AppendText((String)inMessage); // display message
                    controller_in_message_type = 0x00; // reset message type
                    controller_input_message_length = 0x00; // reset message length
                break;
            }



        }
        // cart serialport disconnected physically from PC
        private void cartSerial_PinChanged(object sender, SerialPinChangedEventArgs e)
        {
            SerialPort sp = (SerialPort)sender;
            invertCartSerialWidgets();
            CartSerial.Close();
        }

        // controller serialport disconnected physically from PC
        private void controllerSerial_PinChanged(object sender, SerialPinChangedEventArgs e)
        {
            invertControllerSerialWidgets();
            ControllerSerial.Close();
        }

        private void btnCartOutgoingClear_Click(object sender, EventArgs e)
        {
            outgoingCartSerialDisplay.Clear();
        }

        private void btnCartIncommingClear_Click(object sender, EventArgs e)
        {
            incommingCartSerialDisplay.Clear();
        }

        private void btnControllerOutgoingClear_Click(object sender, EventArgs e)
        {
            outgoingControllerSerialDisplay.Clear();
        }

        private void btnControllerIncommingClear_Click(object sender, EventArgs e)
        {
            incommingControllerSerialDisplay.Clear();
        }

        private void btnESTOP_Click(object sender, EventArgs e)
        {
            Cart_Output_Array[0] = EMERGENCY_STOP;
            cartSerialWrite(Cart_Output_Array, ESTOP_MESSAGE_LENGTH);
            enableModeButtons();
            groupboxVehicleTuningMenu.Enabled = false;
            cart_mode = CART_MODES.standby;
        }

        /***************************************************************************************
        * 
        *  Button controls for cart mode selection
        * 
        *  
        * 
        * ************************************************************************************/
        private void btnManual_Click(object sender, EventArgs e)
        {
            cart_mode = CART_MODES.manual;
            enableModeButtons();
            btnManual.Enabled = false;
            groupboxVehicleTuningMenu.Enabled = false;

        }

        private void btnAutoLineFollow_Click(object sender, EventArgs e)
        {
            cart_mode = CART_MODES.auto_line_follow;
            enableModeButtons();
            btnAutoLineFollow.Enabled = false;
            groupboxVehicleTuningMenu.Enabled = false;
        }

        private void btnTune_Click(object sender, EventArgs e)
        {
            cart_mode = CART_MODES.tuning;
            enableModeButtons();
            groupboxVehicleTuningMenu.Enabled = true;
            btnTune.Enabled = false;
        }
            // start of tuning modes control sub tree
            private void btnPIDTuneModeSelect_Click(object sender, EventArgs e)
            {
                groupBoxDACControl.Enabled = false;
                groupBoxPIDControl.Enabled = true;
                btnPIDTuneModeSelect.Enabled = false;
                btnDACTuneModeSelect.Enabled = true;
            }

            private void btnDACTuneModeSelect_Click(object sender, EventArgs e)
            {
                groupBoxPIDControl.Enabled = false;
                groupBoxDACControl.Enabled = true;
                btnDACTuneModeSelect.Enabled = false;
                btnPIDTuneModeSelect.Enabled = true;

            }


            private void btnSaveData_Click(object sender, EventArgs e)
            {
                cart_mode = CART_MODES.save_data_to_eeprom;
                comboBoxEEPROMAddress.Enabled = true;
                comboBoxEEPROMAddress.SelectedItem = 0;
                btnSaveData.Enabled = false;
                btnLoadData.Enabled = false;
                btnSaveLoadCancel.Enabled = true;
            }

            private void btnLoadData_Click(object sender, EventArgs e)
            {
                cart_mode = CART_MODES.load_data_from_eeprom;
                comboBoxEEPROMAddress.Enabled = true;
                comboBoxEEPROMAddress.SelectedItem = 0;
                btnSaveData.Enabled = false;
                btnLoadData.Enabled = false;
                btnSaveLoadCancel.Enabled = true;
            }


            private void btnSaveLoadCancel_Click(object sender, EventArgs e)
            {
                cart_mode = CART_MODES.tuning;
                comboBoxEEPROMAddress.Enabled = false;
                btnSaveData.Enabled = true;
                btnLoadData.Enabled = true;
                btnSaveLoadCancel.Enabled = false;
            }
        // end of tuning mode sub tree

        private void enableModeButtons()
        {
            btnAutoLineFollow.Enabled = true;
            btnTune.Enabled = true;

            // check if controller serial is online
            if (ControllerSerial.IsOpen)
            {
                btnManual.Enabled = true;
            }
        }

        // end of cart mode selection controls

        
        /***************************************************************************************
         * 
         *  Button controls for DAC tuning 
         * 
         * 
         * 
         * ************************************************************************************/

        // this check box for master slaving the 2 DAC channel up down value controls
        private void checkBoxLinkDACupdowns_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBoxLinkDACupdowns.Checked)
            {
                upDownRightDAC.Enabled = false;
                rightDACLabel.Enabled = false;
                btnDACRightFB.Enabled = false;
                btnDACRightFF.Enabled = false;
                btnDACRightStop.Enabled = false;
                leftDACLabel.Text = "Linked DAC Value";
            }
            else
            {
                upDownRightDAC.Enabled=true;
                rightDACLabel.Enabled=true;
                rightDACLabel.Enabled = true;
                btnDACRightFB.Enabled = true;
                btnDACRightFF.Enabled = true;
                btnDACRightStop.Enabled = true;
                leftDACLabel.Text = "Left DAC Value";
            }

        }

        private void btnDACLeftFF_Click(object sender, EventArgs e)
        {

            upDownLeftDAC.Value = 255;

            if (checkBoxLinkDACupdowns.Checked)
            {
                // set the right value from the left value
                upDownRightDAC.Value = upDownLeftDAC.Value;
            }

           
        }

        private void btnDACRightFF_Click(object sender, EventArgs e)
        {
            upDownRightDAC.Value = 255;
        }

        private void btnDACLeftStop_Click(object sender, EventArgs e)
        {
            upDownLeftDAC.Value = 127;

            if (checkBoxLinkDACupdowns.Checked)
            {
                // set the right value from the left value
                upDownRightDAC.Value = upDownLeftDAC.Value;
            }
        }

        private void btnDACRightStop_Click(object sender, EventArgs e)
        {
            upDownRightDAC.Value = 127;
        }

        private void btnDACLeftFB_Click(object sender, EventArgs e)
        {
            upDownLeftDAC.Value = 0;

            if (checkBoxLinkDACupdowns.Checked)
            {
                // set the right value from the left value
                upDownRightDAC.Value = upDownLeftDAC.Value;
            }
        }

        private void btnDACRightFB_Click(object sender, EventArgs e)
        {
            upDownRightDAC.Value = 0;
        }

        private void upDownLeftDAC_ValueChanged(object sender, EventArgs e)
        {
            // check if the channels are linked
            if (checkBoxLinkDACupdowns.Checked)
            {
                // set the right value from the left value
                upDownRightDAC.Value = upDownLeftDAC.Value;
            }
        }

        private void upDownRightDAC_ValueChanged(object sender, EventArgs e)
        {

        }

        // End of DAC tuning controls
    }

}
