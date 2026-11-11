# Lab 08 – Observer pattern

Run: `clean package exec:java`

- `framework/Observer`, `framework/Subject`: the interfaces given in the lab.
- `sensors/Sensor` (abstract, implements `Subject` and `Cloneable`): keeps the list of observers;
  `setReading()` notifies them. `TemperatureSensor` and `HumiditySensor` extend it.
- `observers/SensorObserver` (abstract): common `update()` code; `DashboardObserver` prints the value,
  `LoggerObserver` prints it and writes it to the log file.
- **Cloning (question 3):** `Sensor.clone()` copies the sensor but gives the copy a **new, empty**
  observer list, so the clone is not observed by the original's observers and gets its own.
- `App`: two sensors, two observers registered to both, the 10-step reading loop, an unregister
  example and the clone test.
