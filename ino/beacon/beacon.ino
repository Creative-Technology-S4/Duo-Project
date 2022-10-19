const String START_DELIMITER = "<";
const String END_DELIMITER = ">";

const int pingPin_1 = 3;
const int echoPin_1 = 2;
const int pingPin_2 = 5;
const int echoPin_2 = 4;

void setup() {
  Serial.begin(9600);
  pinMode(pingPin_1, OUTPUT);
  pinMode(echoPin_1, INPUT);
  pinMode(pingPin_2, OUTPUT);
  pinMode(echoPin_2, INPUT);
}

void loop() {
  int sensors[2] = {};

  sensors[0] = isTriggered(1, pingPin_1, echoPin_1);
  sensors[1] = isTriggered(2, pingPin_2, echoPin_2);

  String data = "[";
  for (int i = 0; i < sizeof sensors / sizeof sensors[0]; i++) {
    if (sensors[i]) {
      data += String(i + 1) + ",";
      // Serial.println(String(i + 1));
    }
  }
  if (data.length() > 1) {  
    data.remove(data.length() - 1);
  }
  data += "]";

  send(toJson("sensors", data));
}

bool isTriggered(int id, int ping, int echo) {  
  digitalWrite(ping, LOW);
  delayMicroseconds(2);
  digitalWrite(ping, HIGH);
  delayMicroseconds(10);
  digitalWrite(ping, LOW);

  long cm = microsecondsToCentimeters(pulseIn(echo, HIGH));

  delay(100);
  return withinThreshold(cm);
}

long microsecondsToCentimeters(long microseconds) {
  return microseconds / 29 / 2;
}

bool withinThreshold(long cm) {
  return cm < 10;
}

void send(String raw) {
  Serial.println(START_DELIMITER + raw + END_DELIMITER);
}

String toJson(String key, String value) {
  return "{\"" + key + "\":" + value + "}";
}
