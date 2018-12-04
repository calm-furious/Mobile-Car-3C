
void setup()
{
  // serial
  Serial.begin(9600);
  Serial.print("hello world");

  
  //motor
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(12, OUTPUT);
}
char inByte = 0;
int cnt=0;
void loop()
{
  if (Serial.available() > 0)
  {
    inByte = Serial.read();
    Serial.print(inByte);
    cnt=0;
  }
  else
  {
    if(!(inByte>='A'and inByte<='Z'))
      cnt+=1;
    if(cnt>100){
      cnt=0;
      inByte = 0;
    }
  }
  if(inByte == 0) stay();
  else if(inByte == 'a') forward();
  else if(inByte == 'c') anticw();
  else if(inByte == 'd') clockw();
  else if(inByte == 'b') backward();
  else if(inByte == 's') stay();
  else if(inByte == 'A') forward();
  else if(inByte == 'C') anticw();
  else if(inByte == 'D') clockw();
  else if(inByte == 'B') backward();
  else if(inByte == '1') leftdis();
  else if(inByte == '2') rightdis();
  else if(inByte == 'e') rightup();
  else if(inByte == 'f') leftup();
  else if(inByte == 'g') leftdown();
  else if(inByte == 'h') rightdown();
  delay(10);
}

void forward()
{
  digitalWrite(7, HIGH);
  digitalWrite(8, LOW);
  digitalWrite(9, LOW);
  digitalWrite(10, HIGH);
}

void backward()
{
  digitalWrite(7, LOW);
  digitalWrite(8, HIGH);
  digitalWrite(9, HIGH);
  digitalWrite(10, LOW);
}
void clockw()
{
  // circuling
  digitalWrite(7, HIGH);
  digitalWrite(8, LOW);
  digitalWrite(9, HIGH);
  digitalWrite(10, LOW);
  digitalWrite(11, HIGH);
  digitalWrite(12, LOW);
}
void anticw()
{
  // circuling
  digitalWrite(8, HIGH);
  digitalWrite(7, LOW);
  digitalWrite(10, HIGH);
  digitalWrite(9, LOW);
  digitalWrite(12, HIGH);
  digitalWrite(11, LOW);
}
void stay()
{
  digitalWrite(7, LOW);
  digitalWrite(8, LOW);
  digitalWrite(9, LOW);
  digitalWrite(10, LOW);
  digitalWrite(11, LOW);
  digitalWrite(12, LOW);
}

//8 direction displacement
void leftup()
{
  digitalWrite(7, HIGH);
  digitalWrite(8, LOW);
  digitalWrite(11, LOW);
  digitalWrite(12, HIGH);
}
void rightup()
{
  digitalWrite(9, LOW);
  digitalWrite(10, HIGH);
  digitalWrite(11, HIGH);
  digitalWrite(12, LOW);
}
void leftdown()
{
  digitalWrite(9, HIGH);
  digitalWrite(10, LOW);
  digitalWrite(11, LOW);
  digitalWrite(12, HIGH);
}
void rightdown()
{
  digitalWrite(7, LOW);
  digitalWrite(8, HIGH);
  digitalWrite(11, HIGH);
  digitalWrite(12, LOW);
}
void leftdis()
{
  digitalWrite(7, HIGH);
  digitalWrite(8, LOW);
  digitalWrite(9, HIGH);
  digitalWrite(10, LOW);
  digitalWrite(11, LOW);
  digitalWrite(12, HIGH);
}
void rightdis()
{
  digitalWrite(8, HIGH);
  digitalWrite(7, LOW);
  digitalWrite(10, HIGH);
  digitalWrite(9, LOW);
  digitalWrite(12, LOW);
  digitalWrite(11, HIGH);
}
