#include <SoftwareSerial.h>
SoftwareSerial bluetooth(10,11);

String comando;
String received;

void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);
bluetooth.begin(9600);
}

void loop() {
  // put your main code here, to run rpeatedly:
  received = "";
  comando = "";
if(bluetooth.available()){
    while(bluetooth.available()){
    char caractere =  bluetooth.read();
    comando += caractere;
  }
    Serial.println(comando);
}   
  if(Serial.available()){
   while(Serial.available()){
    char mesagem =  Serial.read();
    received += mesagem;
  }
    bluetooth.println(received);
  }
}
