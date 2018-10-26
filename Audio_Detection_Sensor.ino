int soundSensor = 2;

void setup()
{
  pinMode (soundSensor, INPUT);
  Serial.begin(9600);
}

void loop()
{
  int statusSensor = digitalRead (soundSensor);

  if (statusSensor == 1)
  {
    delay(200);
    Serial.println(statusSensor);
    Serial.print(" ");
  }

  else
  {
    Serial.println(statusSensor);
    Serial.print(" ");
    delay(200);
  }
}
