#include <SoftwareSerial.h>

SoftwareSerial bluetooth(10,11);
  
#define led1 9 //LED Amarelo
#define led2 8 //LED Verde
#define btn1 7
#define buzzer 6
#define btn2 5 

String comando;
int aux = 0;
int i = 0;
int cmd = 0;
int tempo = 0;
int counter = 0;
int pedidos = 0;
bool start = false;
int quantPratos;
String quantipedidos = " ";

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);

  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(btn1, INPUT_PULLUP);
  pinMode(btn2, INPUT_PULLUP);
  pinMode(buzzer, OUTPUT);
  
}

void loop() {
  noTone(buzzer);
  comando = " ";

  if(bluetooth.available()){
    while(bluetooth.available()){
      char caracter = bluetooth.read();
      switch(cmd){
        case 0:
        if(caracter == ' '){
        break;
        cmd++;
      }
        comando += caracter;
        delay(10);
        break;

        case 1:
         // quanti pratos;
          comando = " ";
          if(caracter == ' '){
             cmd++;
             quantPratos = int(caracter);
             Serial.println(quantPratos);
             break;
           }
        caracter += caracter;
        delay(10);     
        break;
        
        case 2:
        // pedidos;
        String pedidosN[quantPratos];
        if(caracter == ' '){
           pedidosN[i] = comando; 
           counter++;
           i++;
           comando = " ";
           break;
        }
       comando += caracter;
       delay(10);  
       
        if(comando.indexOf("fim")){
          counter = 0;
        }
        for(int i = 0; i < quantPratos; i++){
    Serial.println(pedidosN[i]);
    }
       break;
    }
  }
  }
     if(digitalRead(btn1) == LOW && pedidos > 0){
      
      pedidoPronto();
     }
     if(comando.indexOf("novoPedido") >= 0){
      pedidoRecebido();
     }
    if(tempo >= 30){
      digitalWrite(led2, LOW);
      start == false;
      tempo = 0;
    }
    if(start == true){
      tempo++;
      delay(1000);
    }
} 

void pedidoRecebido(){
    pedidos++;
    Serial.println(pedidos);
        while(digitalRead(btn2) == HIGH){
        digitalWrite(led1, !digitalRead(led1));
        tone(buzzer,330);    
        delay(200);
         if(digitalRead(btn2) == LOW){
          break;    
         }
         tone(buzzer, 392);
         delay(200);
          if(digitalRead(btn2) == LOW){
          break;    
         }
         digitalWrite(led1, !digitalRead(led1));
         tone(buzzer,440);    
         delay(200); 
          if(digitalRead(btn2) == LOW){
          break;    
         }
        tone(buzzer,262);    
        delay(400);
        if(digitalRead(btn2) == LOW){
        break;    
       }
       noTone(buzzer);
       delay(500);
      }
    digitalWrite(led1, HIGH);
    noTone(buzzer);
    bluetooth.println("Pedido recebido");
    }
    
void pedidoPronto(){
  pedidos--;
 cmd = " ";
  if(pedidos <= 0){
    digitalWrite(led1, LOW);
  }
        digitalWrite(led2, HIGH);
        tone(buzzer,392);    
        delay(200);    
        tone(buzzer, 293);
        delay(200);
        tone(buzzer,261);    
        delay(200); 
        tone(buzzer,392);    
        delay(200);    
        tone(buzzer, 293);
        delay(200);
        tone(buzzer,261);    
        delay(200);
        tone(buzzer,392);    
        delay(200); 
        noTone(buzzer);
        start = true;
        bluetooth.println("Pedido pronto");
      }
