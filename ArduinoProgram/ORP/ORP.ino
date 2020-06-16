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
char caracter;
bool start = false;
int quantPratos;
String nPedidos = " ";
String quantPedidos = " ";

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

      if(caracter != '!'){
        comandoPedido();
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
void comandoPedido(){
   noTone(buzzer);
  caracter = '!';
  comando = " ";
  while(caracter != '/'){
      if(bluetooth.available()){
        while(bluetooth.available()){
          caracter = bluetooth.read();
          switch(cmd){
            case 0:
            if(caracter == ' '){
            cmd++;
            break;
          }
            comando += caracter;
            delay(10);
            break;
    
            case 1:
            if(caracter == ' '){
               cmd++;
               Serial.println("-------------------------------- PEDIDO -------------------------------");
               delay(10);
               break;
            }
           nPedidos += caracter;
           delay(10);
           break;  
    
           case 2:
           // pedidos;
            if(caracter == '*'){
               i++;
               if(caracter == '/'){
                cmd = 0;
                break;
            }
               Serial.println(quantPedidos);
               Serial.println("-----------------------------------------------------------------------");
               quantPedidos = " ";
               break;
            }
           quantPedidos += caracter;
           delay(10);
           break;  
        }
      }
    }
  }
  if(caracter == '/'){
    Serial.print(" Quantidade de novos pedidos: ");
    Serial.print(nPedidos);
    Serial.println("");
    Serial.println("-----------------------------------------------------------------------");
    caracter = '!';
    nPedidos = " ";
  }
}

void pedidoRecebido(){
    pedidos++;
    comando = "!";
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
 cmd = 0;
 caracter = ' ';
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
