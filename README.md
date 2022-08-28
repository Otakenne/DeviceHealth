How to use the SDK
- Simply call `DeviceHealthSDKEntry.initialize(this@DeviceHealthApplication)` to initialize the SDK, passing in the application
  context
- In your MainActivity.kt, call `DeviceHealthSDKEntry.launchHomeActivity(this)`, passing in the activity context to launch the
  device health composable

Improvements that could be made to the design/application
- To keep in line with Jetpack recommended architecture/standard, I could use the Jetpack Datastore in place of the classic
  Shared Preferences to store simple key - value pairs like user settings and other simple data we wish to cache. Datastore would be
  a decent alternative as it is built on coroutines, thus, it offers async benefits. It is also more consistent and it better manages
  corrupt data.

- To better improve the user experience for the room persisted alerts, I could page the results, this way data is fetched from
  room when it is needed. We do not need to load every record at the start.

- Ideally, given more time on the project, I should integrate more tests, especially UI tests and integration tests to ensure
  that changes to the UI layer does not break expected behaviour

Notes and constraints
- I used a WorkManager to handle the updates in the background, I also added battery constraints to the WorkManager to ensure
  I take care for the user's battery.

- The system CPU load is computed according to the stackoverflow post for Linux systems
  https://stackoverflow.com/questions/3017162/how-to-get-total-cpu-usage-in-linux-using-c/3017438#3017438

- The system CPU load was accessed through "/proc/stat". The downside being that this method of fetching CPU information doesn't
  work on Android O+, as a result, devices running Android O+ return 0 for system CPU load. Apparently, Google and the Android team restricted access to this information for security reasons. Within
  the time allotted for the take home project, I couldn't find another way to reliably collect system CPU information.

- Ideally, for user experience reasons, the battery level threshold should work differently from that of the global RAM usage and
  the system CPU load. This is because the user will want to be alerted when their battery level drops below a threshold. However,
  the question did not explicitly state the direction the alert should follow so I chose not to assume.