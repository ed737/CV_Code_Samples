using System;

namespace SerialGUISample
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.CartSerial = new System.IO.Ports.SerialPort(this.components);
            this.getIOtimer = new System.Windows.Forms.Timer(this.components);
            this.btnCartSerialConnect = new System.Windows.Forms.Button();
            this.cartSerialPortSelect = new System.Windows.Forms.ComboBox();
            this.cartSerialStatus = new System.Windows.Forms.ProgressBar();
            this.btnCartSerialDisconnect = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.outgoingCartSerialDisplay = new System.Windows.Forms.TextBox();
            this.incommingCartSerialDisplay = new System.Windows.Forms.TextBox();
            this.btnCartOutgoingClear = new System.Windows.Forms.Button();
            this.btnIncommingCartClear = new System.Windows.Forms.Button();
            this.groupBoxCartSerial = new System.Windows.Forms.GroupBox();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBoxControllerSerial = new System.Windows.Forms.GroupBox();
            this.label4 = new System.Windows.Forms.Label();
            this.btnControllerSerialConnect = new System.Windows.Forms.Button();
            this.controllerSerialPortSelect = new System.Windows.Forms.ComboBox();
            this.controllerSerialStatus = new System.Windows.Forms.ProgressBar();
            this.btnControllerSerialDisconnect = new System.Windows.Forms.Button();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.outgoingControllerSerialDisplay = new System.Windows.Forms.TextBox();
            this.incommingControllerSerialDisplay = new System.Windows.Forms.TextBox();
            this.btnOutgoingControllerClear = new System.Windows.Forms.Button();
            this.btnIncommingControllerClear = new System.Windows.Forms.Button();
            this.ControllerSerial = new System.IO.Ports.SerialPort(this.components);
            this.groupBoxControlMode = new System.Windows.Forms.GroupBox();
            this.btnTune = new System.Windows.Forms.Button();
            this.btnUpdateCart = new System.Windows.Forms.Button();
            this.btnAutoLineFollow = new System.Windows.Forms.Button();
            this.btnManual = new System.Windows.Forms.Button();
            this.groupboxVehicleTuningMenu = new System.Windows.Forms.GroupBox();
            this.btnDACTuneModeSelect = new System.Windows.Forms.Button();
            this.btnPIDTuneModeSelect = new System.Windows.Forms.Button();
            this.groupBoxDACControl = new System.Windows.Forms.GroupBox();
            this.checkBoxLinkDACupdowns = new System.Windows.Forms.CheckBox();
            this.btnDACRightFB = new System.Windows.Forms.Button();
            this.btnDACLeftFB = new System.Windows.Forms.Button();
            this.btnDACRightStop = new System.Windows.Forms.Button();
            this.btnDACLeftStop = new System.Windows.Forms.Button();
            this.btnDACRightFF = new System.Windows.Forms.Button();
            this.btnDACLeftFF = new System.Windows.Forms.Button();
            this.leftDACLabel = new System.Windows.Forms.Label();
            this.rightDACLabel = new System.Windows.Forms.Label();
            this.upDownLeftDAC = new System.Windows.Forms.NumericUpDown();
            this.upDownRightDAC = new System.Windows.Forms.NumericUpDown();
            this.groupBoxPIDControl = new System.Windows.Forms.GroupBox();
            this.btnSaveLoadCancel = new System.Windows.Forms.Button();
            this.label9 = new System.Windows.Forms.Label();
            this.comboBoxEEPROMAddress = new System.Windows.Forms.ComboBox();
            this.btnLoadData = new System.Windows.Forms.Button();
            this.btnSaveData = new System.Windows.Forms.Button();
            this.label7 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.upDownKP = new System.Windows.Forms.NumericUpDown();
            this.upDownKI = new System.Windows.Forms.NumericUpDown();
            this.upDownKD = new System.Windows.Forms.NumericUpDown();
            this.label8 = new System.Windows.Forms.Label();
            this.btnESTOP = new System.Windows.Forms.Button();
            this.groupBoxCartSerial.SuspendLayout();
            this.groupBoxControllerSerial.SuspendLayout();
            this.groupBoxControlMode.SuspendLayout();
            this.groupboxVehicleTuningMenu.SuspendLayout();
            this.groupBoxDACControl.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.upDownLeftDAC)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.upDownRightDAC)).BeginInit();
            this.groupBoxPIDControl.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.upDownKP)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.upDownKI)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.upDownKD)).BeginInit();
            this.SuspendLayout();
            // 
            // CartSerial
            // 
            this.CartSerial.PinChanged += new System.IO.Ports.SerialPinChangedEventHandler(this.cartSerial_PinChanged);
            // 
            // getIOtimer
            // 
            this.getIOtimer.Enabled = true;
            this.getIOtimer.Interval = 10;
            this.getIOtimer.Tick += new System.EventHandler(this.getIOtimer_Tick);
            // 
            // btnCartSerialConnect
            // 
            resources.ApplyResources(this.btnCartSerialConnect, "btnCartSerialConnect");
            this.btnCartSerialConnect.Name = "btnCartSerialConnect";
            this.btnCartSerialConnect.UseVisualStyleBackColor = true;
            this.btnCartSerialConnect.Click += new System.EventHandler(this.btnCartSerialConnect_Click);
            // 
            // cartSerialPortSelect
            // 
            this.cartSerialPortSelect.FormattingEnabled = true;
            resources.ApplyResources(this.cartSerialPortSelect, "cartSerialPortSelect");
            this.cartSerialPortSelect.Name = "cartSerialPortSelect";
            // 
            // cartSerialStatus
            // 
            resources.ApplyResources(this.cartSerialStatus, "cartSerialStatus");
            this.cartSerialStatus.Name = "cartSerialStatus";
            // 
            // btnCartSerialDisconnect
            // 
            resources.ApplyResources(this.btnCartSerialDisconnect, "btnCartSerialDisconnect");
            this.btnCartSerialDisconnect.Name = "btnCartSerialDisconnect";
            this.btnCartSerialDisconnect.UseVisualStyleBackColor = true;
            this.btnCartSerialDisconnect.Click += new System.EventHandler(this.btnCartSerialDisconnect_Click);
            // 
            // label2
            // 
            resources.ApplyResources(this.label2, "label2");
            this.label2.Name = "label2";
            // 
            // label3
            // 
            resources.ApplyResources(this.label3, "label3");
            this.label3.Name = "label3";
            // 
            // outgoingCartSerialDisplay
            // 
            resources.ApplyResources(this.outgoingCartSerialDisplay, "outgoingCartSerialDisplay");
            this.outgoingCartSerialDisplay.Name = "outgoingCartSerialDisplay";
            // 
            // incommingCartSerialDisplay
            // 
            resources.ApplyResources(this.incommingCartSerialDisplay, "incommingCartSerialDisplay");
            this.incommingCartSerialDisplay.Name = "incommingCartSerialDisplay";
            // 
            // btnCartOutgoingClear
            // 
            resources.ApplyResources(this.btnCartOutgoingClear, "btnCartOutgoingClear");
            this.btnCartOutgoingClear.Name = "btnCartOutgoingClear";
            this.btnCartOutgoingClear.UseVisualStyleBackColor = true;
            this.btnCartOutgoingClear.Click += new System.EventHandler(this.btnCartOutgoingClear_Click);
            // 
            // btnIncommingCartClear
            // 
            resources.ApplyResources(this.btnIncommingCartClear, "btnIncommingCartClear");
            this.btnIncommingCartClear.Name = "btnIncommingCartClear";
            this.btnIncommingCartClear.UseVisualStyleBackColor = true;
            this.btnIncommingCartClear.Click += new System.EventHandler(this.btnCartIncommingClear_Click);
            // 
            // groupBoxCartSerial
            // 
            this.groupBoxCartSerial.Controls.Add(this.label1);
            this.groupBoxCartSerial.Controls.Add(this.btnCartSerialConnect);
            this.groupBoxCartSerial.Controls.Add(this.cartSerialPortSelect);
            this.groupBoxCartSerial.Controls.Add(this.cartSerialStatus);
            this.groupBoxCartSerial.Controls.Add(this.btnCartSerialDisconnect);
            this.groupBoxCartSerial.Controls.Add(this.label2);
            this.groupBoxCartSerial.Controls.Add(this.label3);
            this.groupBoxCartSerial.Controls.Add(this.outgoingCartSerialDisplay);
            this.groupBoxCartSerial.Controls.Add(this.incommingCartSerialDisplay);
            this.groupBoxCartSerial.Controls.Add(this.btnCartOutgoingClear);
            this.groupBoxCartSerial.Controls.Add(this.btnIncommingCartClear);
            resources.ApplyResources(this.groupBoxCartSerial, "groupBoxCartSerial");
            this.groupBoxCartSerial.Name = "groupBoxCartSerial";
            this.groupBoxCartSerial.TabStop = false;
            // 
            // label1
            // 
            resources.ApplyResources(this.label1, "label1");
            this.label1.Name = "label1";
            // 
            // groupBoxControllerSerial
            // 
            this.groupBoxControllerSerial.Controls.Add(this.label4);
            this.groupBoxControllerSerial.Controls.Add(this.btnControllerSerialConnect);
            this.groupBoxControllerSerial.Controls.Add(this.controllerSerialPortSelect);
            this.groupBoxControllerSerial.Controls.Add(this.controllerSerialStatus);
            this.groupBoxControllerSerial.Controls.Add(this.btnControllerSerialDisconnect);
            this.groupBoxControllerSerial.Controls.Add(this.label5);
            this.groupBoxControllerSerial.Controls.Add(this.label6);
            this.groupBoxControllerSerial.Controls.Add(this.outgoingControllerSerialDisplay);
            this.groupBoxControllerSerial.Controls.Add(this.incommingControllerSerialDisplay);
            this.groupBoxControllerSerial.Controls.Add(this.btnOutgoingControllerClear);
            this.groupBoxControllerSerial.Controls.Add(this.btnIncommingControllerClear);
            resources.ApplyResources(this.groupBoxControllerSerial, "groupBoxControllerSerial");
            this.groupBoxControllerSerial.Name = "groupBoxControllerSerial";
            this.groupBoxControllerSerial.TabStop = false;
            // 
            // label4
            // 
            resources.ApplyResources(this.label4, "label4");
            this.label4.Name = "label4";
            // 
            // btnControllerSerialConnect
            // 
            resources.ApplyResources(this.btnControllerSerialConnect, "btnControllerSerialConnect");
            this.btnControllerSerialConnect.Name = "btnControllerSerialConnect";
            this.btnControllerSerialConnect.UseVisualStyleBackColor = true;
            this.btnControllerSerialConnect.Click += new System.EventHandler(this.btnControllerSerialConnect_Click);
            // 
            // controllerSerialPortSelect
            // 
            this.controllerSerialPortSelect.FormattingEnabled = true;
            resources.ApplyResources(this.controllerSerialPortSelect, "controllerSerialPortSelect");
            this.controllerSerialPortSelect.Name = "controllerSerialPortSelect";
            // 
            // controllerSerialStatus
            // 
            resources.ApplyResources(this.controllerSerialStatus, "controllerSerialStatus");
            this.controllerSerialStatus.Name = "controllerSerialStatus";
            // 
            // btnControllerSerialDisconnect
            // 
            resources.ApplyResources(this.btnControllerSerialDisconnect, "btnControllerSerialDisconnect");
            this.btnControllerSerialDisconnect.Name = "btnControllerSerialDisconnect";
            this.btnControllerSerialDisconnect.UseVisualStyleBackColor = true;
            this.btnControllerSerialDisconnect.Click += new System.EventHandler(this.btnControllerSerialDisconnect_Click);
            // 
            // label5
            // 
            resources.ApplyResources(this.label5, "label5");
            this.label5.Name = "label5";
            // 
            // label6
            // 
            resources.ApplyResources(this.label6, "label6");
            this.label6.Name = "label6";
            // 
            // outgoingControllerSerialDisplay
            // 
            resources.ApplyResources(this.outgoingControllerSerialDisplay, "outgoingControllerSerialDisplay");
            this.outgoingControllerSerialDisplay.Name = "outgoingControllerSerialDisplay";
            // 
            // incommingControllerSerialDisplay
            // 
            resources.ApplyResources(this.incommingControllerSerialDisplay, "incommingControllerSerialDisplay");
            this.incommingControllerSerialDisplay.Name = "incommingControllerSerialDisplay";
            // 
            // btnOutgoingControllerClear
            // 
            resources.ApplyResources(this.btnOutgoingControllerClear, "btnOutgoingControllerClear");
            this.btnOutgoingControllerClear.Name = "btnOutgoingControllerClear";
            this.btnOutgoingControllerClear.UseVisualStyleBackColor = true;
            this.btnOutgoingControllerClear.Click += new System.EventHandler(this.btnControllerOutgoingClear_Click);
            // 
            // btnIncommingControllerClear
            // 
            resources.ApplyResources(this.btnIncommingControllerClear, "btnIncommingControllerClear");
            this.btnIncommingControllerClear.Name = "btnIncommingControllerClear";
            this.btnIncommingControllerClear.UseVisualStyleBackColor = true;
            this.btnIncommingControllerClear.Click += new System.EventHandler(this.btnControllerIncommingClear_Click);
            // 
            // ControllerSerial
            // 
            this.ControllerSerial.PortName = "COM7";
            // 
            // groupBoxControlMode
            // 
            this.groupBoxControlMode.Controls.Add(this.btnTune);
            this.groupBoxControlMode.Controls.Add(this.btnUpdateCart);
            this.groupBoxControlMode.Controls.Add(this.btnAutoLineFollow);
            this.groupBoxControlMode.Controls.Add(this.btnManual);
            resources.ApplyResources(this.groupBoxControlMode, "groupBoxControlMode");
            this.groupBoxControlMode.Name = "groupBoxControlMode";
            this.groupBoxControlMode.TabStop = false;
            // 
            // btnTune
            // 
            resources.ApplyResources(this.btnTune, "btnTune");
            this.btnTune.Name = "btnTune";
            this.btnTune.UseVisualStyleBackColor = true;
            this.btnTune.Click += new System.EventHandler(this.btnTune_Click);
            // 
            // btnUpdateCart
            // 
            resources.ApplyResources(this.btnUpdateCart, "btnUpdateCart");
            this.btnUpdateCart.Name = "btnUpdateCart";
            this.btnUpdateCart.UseVisualStyleBackColor = true;
            this.btnUpdateCart.Click += new System.EventHandler(this.btnUpdateCart_Click);
            // 
            // btnAutoLineFollow
            // 
            resources.ApplyResources(this.btnAutoLineFollow, "btnAutoLineFollow");
            this.btnAutoLineFollow.Name = "btnAutoLineFollow";
            this.btnAutoLineFollow.UseVisualStyleBackColor = true;
            this.btnAutoLineFollow.Click += new System.EventHandler(this.btnAutoLineFollow_Click);
            // 
            // btnManual
            // 
            resources.ApplyResources(this.btnManual, "btnManual");
            this.btnManual.Name = "btnManual";
            this.btnManual.UseVisualStyleBackColor = true;
            this.btnManual.Click += new System.EventHandler(this.btnManual_Click);
            // 
            // groupboxVehicleTuningMenu
            // 
            this.groupboxVehicleTuningMenu.Controls.Add(this.btnDACTuneModeSelect);
            this.groupboxVehicleTuningMenu.Controls.Add(this.btnPIDTuneModeSelect);
            this.groupboxVehicleTuningMenu.Controls.Add(this.groupBoxDACControl);
            this.groupboxVehicleTuningMenu.Controls.Add(this.groupBoxPIDControl);
            resources.ApplyResources(this.groupboxVehicleTuningMenu, "groupboxVehicleTuningMenu");
            this.groupboxVehicleTuningMenu.Name = "groupboxVehicleTuningMenu";
            this.groupboxVehicleTuningMenu.TabStop = false;
            // 
            // btnDACTuneModeSelect
            // 
            resources.ApplyResources(this.btnDACTuneModeSelect, "btnDACTuneModeSelect");
            this.btnDACTuneModeSelect.Name = "btnDACTuneModeSelect";
            this.btnDACTuneModeSelect.UseVisualStyleBackColor = true;
            this.btnDACTuneModeSelect.Click += new System.EventHandler(this.btnDACTuneModeSelect_Click);
            // 
            // btnPIDTuneModeSelect
            // 
            resources.ApplyResources(this.btnPIDTuneModeSelect, "btnPIDTuneModeSelect");
            this.btnPIDTuneModeSelect.Name = "btnPIDTuneModeSelect";
            this.btnPIDTuneModeSelect.UseVisualStyleBackColor = true;
            this.btnPIDTuneModeSelect.Click += new System.EventHandler(this.btnPIDTuneModeSelect_Click);
            // 
            // groupBoxDACControl
            // 
            this.groupBoxDACControl.Controls.Add(this.checkBoxLinkDACupdowns);
            this.groupBoxDACControl.Controls.Add(this.btnDACRightFB);
            this.groupBoxDACControl.Controls.Add(this.btnDACLeftFB);
            this.groupBoxDACControl.Controls.Add(this.btnDACRightStop);
            this.groupBoxDACControl.Controls.Add(this.btnDACLeftStop);
            this.groupBoxDACControl.Controls.Add(this.btnDACRightFF);
            this.groupBoxDACControl.Controls.Add(this.btnDACLeftFF);
            this.groupBoxDACControl.Controls.Add(this.leftDACLabel);
            this.groupBoxDACControl.Controls.Add(this.rightDACLabel);
            this.groupBoxDACControl.Controls.Add(this.upDownLeftDAC);
            this.groupBoxDACControl.Controls.Add(this.upDownRightDAC);
            resources.ApplyResources(this.groupBoxDACControl, "groupBoxDACControl");
            this.groupBoxDACControl.Name = "groupBoxDACControl";
            this.groupBoxDACControl.TabStop = false;
            // 
            // checkBoxLinkDACupdowns
            // 
            resources.ApplyResources(this.checkBoxLinkDACupdowns, "checkBoxLinkDACupdowns");
            this.checkBoxLinkDACupdowns.Name = "checkBoxLinkDACupdowns";
            this.checkBoxLinkDACupdowns.UseVisualStyleBackColor = true;
            this.checkBoxLinkDACupdowns.CheckedChanged += new System.EventHandler(this.checkBoxLinkDACupdowns_CheckedChanged);
            // 
            // btnDACRightFB
            // 
            resources.ApplyResources(this.btnDACRightFB, "btnDACRightFB");
            this.btnDACRightFB.Name = "btnDACRightFB";
            this.btnDACRightFB.UseVisualStyleBackColor = true;
            this.btnDACRightFB.Click += new System.EventHandler(this.btnDACRightFB_Click);
            // 
            // btnDACLeftFB
            // 
            resources.ApplyResources(this.btnDACLeftFB, "btnDACLeftFB");
            this.btnDACLeftFB.Name = "btnDACLeftFB";
            this.btnDACLeftFB.UseVisualStyleBackColor = true;
            this.btnDACLeftFB.Click += new System.EventHandler(this.btnDACLeftFB_Click);
            // 
            // btnDACRightStop
            // 
            resources.ApplyResources(this.btnDACRightStop, "btnDACRightStop");
            this.btnDACRightStop.Name = "btnDACRightStop";
            this.btnDACRightStop.UseVisualStyleBackColor = true;
            this.btnDACRightStop.Click += new System.EventHandler(this.btnDACRightStop_Click);
            // 
            // btnDACLeftStop
            // 
            resources.ApplyResources(this.btnDACLeftStop, "btnDACLeftStop");
            this.btnDACLeftStop.Name = "btnDACLeftStop";
            this.btnDACLeftStop.UseVisualStyleBackColor = true;
            this.btnDACLeftStop.Click += new System.EventHandler(this.btnDACLeftStop_Click);
            // 
            // btnDACRightFF
            // 
            resources.ApplyResources(this.btnDACRightFF, "btnDACRightFF");
            this.btnDACRightFF.Name = "btnDACRightFF";
            this.btnDACRightFF.UseVisualStyleBackColor = true;
            this.btnDACRightFF.Click += new System.EventHandler(this.btnDACRightFF_Click);
            // 
            // btnDACLeftFF
            // 
            resources.ApplyResources(this.btnDACLeftFF, "btnDACLeftFF");
            this.btnDACLeftFF.Name = "btnDACLeftFF";
            this.btnDACLeftFF.UseVisualStyleBackColor = true;
            this.btnDACLeftFF.Click += new System.EventHandler(this.btnDACLeftFF_Click);
            // 
            // leftDACLabel
            // 
            resources.ApplyResources(this.leftDACLabel, "leftDACLabel");
            this.leftDACLabel.Name = "leftDACLabel";
            // 
            // rightDACLabel
            // 
            resources.ApplyResources(this.rightDACLabel, "rightDACLabel");
            this.rightDACLabel.Name = "rightDACLabel";
            // 
            // upDownLeftDAC
            // 
            resources.ApplyResources(this.upDownLeftDAC, "upDownLeftDAC");
            this.upDownLeftDAC.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.upDownLeftDAC.Name = "upDownLeftDAC";
            this.upDownLeftDAC.Value = new decimal(new int[] {
            127,
            0,
            0,
            0});
            this.upDownLeftDAC.ValueChanged += new System.EventHandler(this.upDownLeftDAC_ValueChanged);
            // 
            // upDownRightDAC
            // 
            resources.ApplyResources(this.upDownRightDAC, "upDownRightDAC");
            this.upDownRightDAC.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.upDownRightDAC.Name = "upDownRightDAC";
            this.upDownRightDAC.Value = new decimal(new int[] {
            127,
            0,
            0,
            0});
            this.upDownRightDAC.ValueChanged += new System.EventHandler(this.upDownRightDAC_ValueChanged);
            // 
            // groupBoxPIDControl
            // 
            this.groupBoxPIDControl.Controls.Add(this.btnSaveLoadCancel);
            this.groupBoxPIDControl.Controls.Add(this.label9);
            this.groupBoxPIDControl.Controls.Add(this.comboBoxEEPROMAddress);
            this.groupBoxPIDControl.Controls.Add(this.btnLoadData);
            this.groupBoxPIDControl.Controls.Add(this.btnSaveData);
            this.groupBoxPIDControl.Controls.Add(this.label7);
            this.groupBoxPIDControl.Controls.Add(this.label10);
            this.groupBoxPIDControl.Controls.Add(this.upDownKP);
            this.groupBoxPIDControl.Controls.Add(this.upDownKI);
            this.groupBoxPIDControl.Controls.Add(this.upDownKD);
            this.groupBoxPIDControl.Controls.Add(this.label8);
            resources.ApplyResources(this.groupBoxPIDControl, "groupBoxPIDControl");
            this.groupBoxPIDControl.Name = "groupBoxPIDControl";
            this.groupBoxPIDControl.TabStop = false;
            // 
            // btnSaveLoadCancel
            // 
            resources.ApplyResources(this.btnSaveLoadCancel, "btnSaveLoadCancel");
            this.btnSaveLoadCancel.Name = "btnSaveLoadCancel";
            this.btnSaveLoadCancel.UseVisualStyleBackColor = true;
            this.btnSaveLoadCancel.Click += new System.EventHandler(this.btnSaveLoadCancel_Click);
            // 
            // label9
            // 
            resources.ApplyResources(this.label9, "label9");
            this.label9.Name = "label9";
            // 
            // comboBoxEEPROMAddress
            // 
            resources.ApplyResources(this.comboBoxEEPROMAddress, "comboBoxEEPROMAddress");
            this.comboBoxEEPROMAddress.FormattingEnabled = true;
            this.comboBoxEEPROMAddress.Items.AddRange(new object[] {
            resources.GetString("comboBoxEEPROMAddress.Items"),
            resources.GetString("comboBoxEEPROMAddress.Items1"),
            resources.GetString("comboBoxEEPROMAddress.Items2"),
            resources.GetString("comboBoxEEPROMAddress.Items3")});
            this.comboBoxEEPROMAddress.Name = "comboBoxEEPROMAddress";
            // 
            // btnLoadData
            // 
            resources.ApplyResources(this.btnLoadData, "btnLoadData");
            this.btnLoadData.Name = "btnLoadData";
            this.btnLoadData.UseVisualStyleBackColor = true;
            this.btnLoadData.Click += new System.EventHandler(this.btnLoadData_Click);
            // 
            // btnSaveData
            // 
            resources.ApplyResources(this.btnSaveData, "btnSaveData");
            this.btnSaveData.Name = "btnSaveData";
            this.btnSaveData.UseVisualStyleBackColor = true;
            this.btnSaveData.Click += new System.EventHandler(this.btnSaveData_Click);
            // 
            // label7
            // 
            resources.ApplyResources(this.label7, "label7");
            this.label7.Name = "label7";
            // 
            // label10
            // 
            resources.ApplyResources(this.label10, "label10");
            this.label10.Name = "label10";
            // 
            // upDownKP
            // 
            this.upDownKP.DecimalPlaces = 1;
            this.upDownKP.Increment = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            resources.ApplyResources(this.upDownKP, "upDownKP");
            this.upDownKP.Maximum = new decimal(new int[] {
            32,
            0,
            0,
            65536});
            this.upDownKP.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            this.upDownKP.Name = "upDownKP";
            this.upDownKP.Value = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            // 
            // upDownKI
            // 
            this.upDownKI.DecimalPlaces = 1;
            this.upDownKI.Increment = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            resources.ApplyResources(this.upDownKI, "upDownKI");
            this.upDownKI.Maximum = new decimal(new int[] {
            32,
            0,
            0,
            65536});
            this.upDownKI.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            this.upDownKI.Name = "upDownKI";
            this.upDownKI.Value = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            // 
            // upDownKD
            // 
            this.upDownKD.DecimalPlaces = 1;
            resources.ApplyResources(this.upDownKD, "upDownKD");
            this.upDownKD.Increment = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            this.upDownKD.Maximum = new decimal(new int[] {
            32,
            0,
            0,
            65536});
            this.upDownKD.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            this.upDownKD.Name = "upDownKD";
            this.upDownKD.Value = new decimal(new int[] {
            1,
            0,
            0,
            65536});
            // 
            // label8
            // 
            resources.ApplyResources(this.label8, "label8");
            this.label8.Name = "label8";
            // 
            // btnESTOP
            // 
            this.btnESTOP.BackColor = System.Drawing.SystemColors.Control;
            resources.ApplyResources(this.btnESTOP, "btnESTOP");
            this.btnESTOP.ForeColor = System.Drawing.Color.Red;
            this.btnESTOP.Name = "btnESTOP";
            this.btnESTOP.UseVisualStyleBackColor = false;
            this.btnESTOP.Click += new System.EventHandler(this.btnESTOP_Click);
            // 
            // Form1
            // 
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.btnESTOP);
            this.Controls.Add(this.groupboxVehicleTuningMenu);
            this.Controls.Add(this.groupBoxControlMode);
            this.Controls.Add(this.groupBoxControllerSerial);
            this.Controls.Add(this.groupBoxCartSerial);
            this.Name = "Form1";
            this.groupBoxCartSerial.ResumeLayout(false);
            this.groupBoxCartSerial.PerformLayout();
            this.groupBoxControllerSerial.ResumeLayout(false);
            this.groupBoxControllerSerial.PerformLayout();
            this.groupBoxControlMode.ResumeLayout(false);
            this.groupboxVehicleTuningMenu.ResumeLayout(false);
            this.groupBoxDACControl.ResumeLayout(false);
            this.groupBoxDACControl.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.upDownLeftDAC)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.upDownRightDAC)).EndInit();
            this.groupBoxPIDControl.ResumeLayout(false);
            this.groupBoxPIDControl.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.upDownKP)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.upDownKI)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.upDownKD)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Timer getIOtimer;
        private System.IO.Ports.SerialPort CartSerial;
        private System.Windows.Forms.Button btnCartSerialConnect;
        private System.Windows.Forms.ComboBox cartSerialPortSelect;
        private System.Windows.Forms.ProgressBar cartSerialStatus;
        private System.Windows.Forms.Button btnCartSerialDisconnect;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox outgoingCartSerialDisplay;
        private System.Windows.Forms.TextBox incommingCartSerialDisplay;
        private System.Windows.Forms.Button btnCartOutgoingClear;
        private System.Windows.Forms.Button btnIncommingCartClear;
        private System.Windows.Forms.GroupBox groupBoxCartSerial;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBoxControllerSerial;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button btnControllerSerialConnect;
        private System.Windows.Forms.ComboBox controllerSerialPortSelect;
        private System.Windows.Forms.ProgressBar controllerSerialStatus;
        private System.Windows.Forms.Button btnControllerSerialDisconnect;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox outgoingControllerSerialDisplay;
        private System.Windows.Forms.TextBox incommingControllerSerialDisplay;
        private System.Windows.Forms.Button btnOutgoingControllerClear;
        private System.Windows.Forms.Button btnIncommingControllerClear;
        private System.IO.Ports.SerialPort ControllerSerial;
        private System.Windows.Forms.GroupBox groupBoxControlMode;
        private System.Windows.Forms.Button btnAutoLineFollow;
        private System.Windows.Forms.Button btnManual;
        private System.Windows.Forms.GroupBox groupboxVehicleTuningMenu;
        private System.Windows.Forms.GroupBox groupBoxPIDControl;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.NumericUpDown upDownKP;
        private System.Windows.Forms.NumericUpDown upDownKI;
        private System.Windows.Forms.NumericUpDown upDownKD;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Button btnUpdateCart;
        private System.Windows.Forms.Button btnTune;
        private System.Windows.Forms.GroupBox groupBoxDACControl;
        private System.Windows.Forms.Label leftDACLabel;
        private System.Windows.Forms.Label rightDACLabel;
        private System.Windows.Forms.NumericUpDown upDownLeftDAC;
        private System.Windows.Forms.NumericUpDown upDownRightDAC;
        private System.Windows.Forms.Button btnDACRightFB;
        private System.Windows.Forms.Button btnDACLeftFB;
        private System.Windows.Forms.Button btnDACRightStop;
        private System.Windows.Forms.Button btnDACLeftStop;
        private System.Windows.Forms.Button btnDACRightFF;
        private System.Windows.Forms.Button btnDACLeftFF;
        private System.Windows.Forms.Button btnESTOP;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.ComboBox comboBoxEEPROMAddress;
        private System.Windows.Forms.Button btnLoadData;
        private System.Windows.Forms.Button btnSaveLoadCancel;
        private System.Windows.Forms.Button btnSaveData;
        private System.Windows.Forms.CheckBox checkBoxLinkDACupdowns;
        private System.Windows.Forms.Button btnDACTuneModeSelect;
        private System.Windows.Forms.Button btnPIDTuneModeSelect;
    }
}

