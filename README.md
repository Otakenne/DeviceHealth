# Task

Create a simple application that monitors the health of your device.

- Collect the following health metrics on the device:
  - System CPU load
  - Global RAM usage
  - Battery level
- Let the user configure the thresholds (expressed in percentage) at which they want to be alerted for each of the above metrics.
- Anytime this happens when the app is running, dispatch a notification to alert the user.
- If the user opts so, also display a notification whenever the metrics returns below the threshold to say that the alert recovered.
- Make sure all messages showing when alerting thresholds are crossed remain visible in the app for historical reasons.
- Explain how you’d improve on this application design.
- [Optional] Make the notification dispatch mechanism work even if the app is not running in the foreground. (will grant bonus points)
- Be wary of the power consumption of your app while keeping it accurate.
