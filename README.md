# Sonic-Devices-API

A small library for adding [Sonic-Devices](https://github.com/stuin/SonicDevices) functionality to your blocks.

To include it in your project, add this to your build.gradle:
```groovy
repositories {
    maven { 
        url = 'https://jitpack.io' 
    }
}
dependencies {
    modImplementation 'com.github.stuin:sonic-devices-api:LATEST'
}
```
Where `LATEST` is the latest tag under the releases tab.

## Usage
A good example can be found in Bacterium:
[ActivateAction.java](https://github.com/stuin/Bacteria/blob/master/src/main/java/com/stuintech/bacteria/integration/sonicdevices/ActivateAction.java)
[fabric.mod.json](https://github.com/stuin/Bacteria/blob/master/src/main/resources/fabric.mod.json)


