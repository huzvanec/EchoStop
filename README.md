# EchoStop
## About
EchoStop is a simple plugin that allows you to set a timer for shutting down your minecraft server. It is [fully customizable](https://github.com/Mandlemankiller/EchoStop/blob/master/src/main/resources/config.yml) and easy to understand.
## Supported versions
Paper based servers 1.19.4+<br>
**Bukkit and Spigot are not supported!**
# Usage
#### Stop the server with default countdown
`/estop stop`, `/estop`
#### Stop the server with custom countdown
`/estop stop <time in seconds>`
#### Cancel a server stop during the countdown
`/estop cancel`
#### Reload the plugin configuration
`/estop reload`
## Build
Requirements: <br>
[Java](https://java.com), [Git](https://git-scm.com/), [Maven](https://maven.apache.org/)<br>
Clone the repository:<br>
```git clone https://github.com/Mandlemankiller/EchoStop``` <br>
Move to the folder:<br>
```cd EchoStop``` <br>
Run maven: <br>
```mvn package``` <br>
Done! The jar is now in the target directory, it's called ```EchoStop-1.0-SNAPSHOT.jar```