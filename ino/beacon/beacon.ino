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
  doChecks(1, pingPin_1, echoPin_1);
  doChecks(2, pingPin_2, echoPin_2);
}

void doChecks(int id, int ping, int echo) {
  digitalWrite(ping, LOW);
  delayMicroseconds(2);
  digitalWrite(ping, HIGH);
  delayMicroseconds(10);
  digitalWrite(ping, LOW);

  long cm = microsecondsToCentimeters(pulseIn(echo, HIGH));

  if (withinThreshold(cm)) {
    send(toJson("sensor", String(id)));
  }

  delay(100);
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
