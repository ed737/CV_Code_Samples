#include <max6675.h>
#include <LiquidCrystal_I2C.h>
#include <Key.h>
#include <Keypad.h>
#include <Wire.h>
#include <EEPROM.h>

#define SIM5320A_ON 8 // arduino pin to wake/sleep SIM unit
#define MAX_NUM_CONTACTS 5

#define LCD_ADDRESS 0x27
#define LCD_COLS 20
#define LCD_ROWS 4

// Settings struct definition:
typedef struct Settings{
    int temp_limit;
    int number_contacts;
    char phone_numbers[MAX_NUM_CONTACTS][11];
}Settings;

// Global variables:
Settings settings;
HardwareSerial& Terminal = Serial;
HardwareSerial& SimSerial = Serial1;
volatile byte eepromIndex = 0;
volatile bool msgSent = false;
volatile bool incident = false;
volatile char key = ' ';

// Max 6675 thermocouple IC pin setup:
int thermo_gnd = 46;
int thermo_vcc = 44;
int thermo_sck = 42;
int thermo_cs = 40;
int thermo_so = 38;

MAX6675 thermocouple(thermo_sck, thermo_cs, thermo_so);

// inialize LCD, I2C IC is a PCF8574T, these use I2C address 0x27 as standard, this panel is 20*4:
static LiquidCrystal_I2C lcd(LCD_ADDRESS, LCD_COLS, LCD_ROWS);  

// keypad setup:
const byte KEYPAD_ROWS = 4;
const byte KEYPAD_COLS = 4;

// define the kaypad matrix    
char keys[KEYPAD_ROWS][KEYPAD_COLS] = {{'1','2','3','A'},
                         {'4','5','6','B'},
                         {'7','8','9','C'},
                         {'*','0','#','D'}};

// define keypad pins
byte rowPins[KEYPAD_ROWS] = {26, 27, 28, 29};
byte colPins[KEYPAD_COLS] = {22, 23, 24, 25};

// create keypad object
Keypad myKeypad = Keypad(makeKeymap(keys), rowPins, colPins, KEYPAD_ROWS, KEYPAD_COLS);

void setup()
{
    // Thermocouple IC:
    pinMode(thermo_vcc, OUTPUT);
    pinMode(thermo_gnd, OUTPUT);
    digitalWrite(thermo_vcc, HIGH);
    digitalWrite(thermo_gnd, LOW);
    delay(500);

    char line3[20];

    // inialize serial:
    Terminal.begin(115200);
    SimSerial.begin(115200);
    lcd.begin(LCD_COLS, LCD_ROWS);
    lcd.backlight();
    lcd.setCursor(0,0);
    lcd.print("**** Cool Safe  ****");
    lcd.setCursor(0,1);
    lcd.print("*   Press # key    *");
    lcd.setCursor(0,2);
    lcd.print("*   to enter setup *");

    // loop for 10 seconds and wait for user input
    int i = 100;
    bool done = false;
    while(i > 0 && !done)
    {
        key = myKeypad.getKey();
        if(key == '#')
        {
          firstTimeSetup();
          done = true;
        }
        lcd.setCursor(0,3);      
        sprintf(line3, "****     %2d     ****",(int)(i/10));
        lcd.print(line3);
        delay(100);
        i--;
    }
    lcd.clear();
    lcd.setCursor(0,0); 
    delay(200); 
}

void loop() 
{
    char line3[20];
    settings = EEPROM.get(eepromIndex, settings);
    // the fuction readCelcius in the MAX6675 library returns double,
    // put this in fixed point format:
    int temp = thermocouple.readCelsius()*100;
    int deg = temp/100;
    int dp = temp%100;
    
    // If statement for initiation of incident code.
    if(deg > settings.temp_limit && !incident)
    {
        sendTempWarnings();
    }
    
    lcd.setCursor(0,0);
    lcd.print("**** Cool Safe  ****");
    lcd.setCursor(0,1);
    lcd.print("*   Current temp:  *");
    lcd.setCursor(0,2);
    lcd.print("*                  *");
    lcd.setCursor(0,3);
    sprintf(line3, "****    %d.%02d   ****", deg, dp);
    lcd.print(line3);
    delay(200);
}

void firstTimeSetup()
{
    // initalize blank settings struct 
    settings = {30, 1, {{'0','0','0','0','0','0','0','0','0','0','0'},
                        {'0','0','0','0','0','0','0','0','0','0','0'},
                        {'0','0','0','0','0','0','0','0','0','0','0'},
                        {'0','0','0','0','0','0','0','0','0','0','0'},
                        {'0','0','0','0','0','0','0','0','0','0','0'}}};
                                                  
    // setup temperature limit
    signed int scaler = 1;
    char temp[3] = "";
    int ii = 0;
    key = ' ';
    lcd.clear(); 
    lcd.setCursor(0,0);
    lcd.print("Please enter temp"); 
    lcd.setCursor(0,1);
    lcd.print("Press A when done");
    lcd.setCursor(0,2);
    lcd.print("* = neg, D = del");
    lcd.setCursor(0,3);
    
    while(key != 'A')
    {      
        key = myKeypad.waitForKey();
        if(key == '*' && ii == 0)
        {
            scaler = -1;
            lcd.print("-");      
        }
        if(key == 'D')
        {
            temp[ii] = ' ';
            lcd.setCursor(ii,3);
            lcd.print(" ");
            lcd.setCursor(ii,3);
            if(ii > 0) ii--;
        }
        if(isDigit(key))
        {
           temp[ii] = key;
           lcd.print(key);
           ii++;
        }
    }

    // change the char to int with atoi then multiply by scaler for negative values.
    settings.temp_limit = atoi(temp) * scaler;
    Terminal.print(settings.temp_limit);
    Terminal.print("\n");

    // setup number of contacts
    int num_contacts = 0;
    key = ' ';
    char nums[1] = "";
    ii = 0;
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("Enter # contacts");
    lcd.setCursor(0,1);
    lcd.print("Press A when done");
    lcd.setCursor(0,2);
    lcd.print("Max = 5, D = del");
    lcd.setCursor(0,3);

    // while loop for user input
    while(key != 'A')
    {
        key = myKeypad.waitForKey();
      
        if(key == 'D')
        {
            lcd.setCursor(ii,3);
            lcd.print(" ");
            lcd.setCursor(ii,3);              
            if(ii > 0) ii--;
        }
        if(isDigit(key))
        {
            nums[ii] = key;
            lcd.print(key);
            ii++;
        }
    }
    
    settings.number_contacts = atoi(nums);
    Terminal.print(settings.number_contacts);
    Terminal.print("\n");
    
    // setup phone numbers                            
    for(int ii = 0; ii < settings.number_contacts; ii++)
    {
          char number[11];
          lcd.clear();
          lcd.setCursor(0,0);
          lcd.print("Enter phone number");
          lcd.setCursor(0,1);
          lcd.print("Press A when done");
          lcd.setCursor(0,2);
          lcd.print("D = del");
          lcd.setCursor(0,3);
          
          int jj = 0;
          while(jj < 11)
          {
              // this function pauses program until a key is pressed.
              key = myKeypad.waitForKey(); // wait for key entry
              if(isDigit(key))
              {
                  // add digit to number
                  number[jj] = key; 
                  // print digit to lcd
                  lcd.print(key);
                  jj++;
              }
              // delete last digit
              else if(key == 'D')
              {
                  jj -= 1;
                  lcd.setCursor(jj,3);
                  lcd.print(' ');
                  lcd.setCursor(jj,3);
              }
              // acknowlege number done
              else if(key == 'A')
              { 
                  // insert Australian country code
                  settings.phone_numbers[ii][0] = '6';
                  settings.phone_numbers[ii][1] = '1';
                  Terminal.print(settings.phone_numbers[ii][0]);
                  Terminal.print(settings.phone_numbers[ii][1]);
                  
                  // set this number to number char array
                  for(int kk = 2; kk < 11; kk++)
                  {
                    settings.phone_numbers[ii][kk] = number[kk-1];
                    Terminal.print(settings.phone_numbers[ii][kk]);
                  }
                  Terminal.print("\n");
                  jj++;
               }
           } // end while
      }// end for
      EEPROM.put(eepromIndex, settings); // store the set settings struct in EEPROM
}

void simInialize()
{
    // turn on SIM5320A
    pinMode(8, OUTPUT);
    digitalWrite(8, HIGH);
    delay(180);
    digitalWrite(8, LOW);
    delay(3000);
}

void sendTempWarnings()
{
    // turn off global interrupts
    Settings settings = EEPROM.get(eepromIndex, settings); // eeprom.get can get objects and structs from eeprom.
    char AT_Command[30];
    eepromIndex = 0;
    char phoneNumber[] = {"00000000000"}; 
    byte endMessage = '\x1a';
    char message[100];
    sprintf(message,"This is an automated message from Cool Safe. Temperature probe is out of range.");

    // turn on SIM module
    simInialize();
   
    if( msgSent == false)
    {
        // fetch all numbers from eeprom and message...
        for( int ii = 0; ii < settings.number_contacts; ii++)
        {
            for(int jj = 0; jj < 11; jj++)
            {
                phoneNumber[jj] = settings.phone_numbers[ii][jj];
            }
            // send message here...
            SimSerial.println("AT");
            Terminal.println("AT");
            delay(5000); // delay 5 seconds to allow init of sim module.
            sprintf(AT_Command,"AT+CMGS=\"%s\"",phoneNumber);
            SimSerial.println(AT_Command);
            Terminal.println(AT_Command);
            delay(200);
            Terminal.write(message);
            SimSerial.write(message);
            Terminal.print(endMessage);
            SimSerial.write(endMessage);
            Terminal.println();
            SimSerial.println();
            Terminal.flush();
            SimSerial.flush();
            delay(5000);  
        }
        eepromIndex = 0;
        msgSent = true;
    }
    incident = true;
}
