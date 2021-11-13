# SocketWrench
A simple and modular api for making powerful multi-tools and universal wrenches

## Installation
```
repositories {
	maven {
		url "https://github.com/stuin/SocketWrench/raw/maven2"
	}
}

dependencies {
    // SocketWrench
	modImplementation "com.stuintech:SocketWrench:1.0.1+1.17.1"
	include "com.stuintech:SocketWrench:1.0.1+1.17.1"
}
```

### Sockets
Every action on an entity or block is defined by a Socket object with onFasten, this should return true if an action was successfully performed or false to pass the action onto the next socket. CancelFasteningException can be thrown to prevent any action being run on the selected object.

Sockets are organized into recursive SocketSets and can be connected to Identifiers in SocketSetManager, meaning that sockets and tools from separate mods can easily be connected together just using Identifiers.

Sockets for rotating blocks are built into the Wrench system from the start, and support everything from chests, dispensers and observers, to doors, repeaters and torches. Slabs and stairs are also included, though rails are a WIP.

* [Socket](https://github.com/stuin/SocketWrench/blob/master/src/main/java/com/stuintech/socketwrench/socket/Socket.java)
* [SocketSet](https://github.com/stuin/SocketWrench/blob/master/src/main/java/com/stuintech/socketwrench/socket/SocketSet.java)
* [SocketSetManager](https://github.com/stuin/SocketWrench/blob/master/src/main/java/com/stuintech/socketwrench/socket/SocketSetManager.java)
* [FacingRotation](https://github.com/stuin/SocketWrench/blob/master/src/main/java/com/stuintech/socketwrench/rotate/FacingRotation.java)

### SocketSetLoader
Registering new sockets can be done in normal initialization, but can also be handled by implementing SocketSetLoader and adding a socketwrench entrypoint, as in [SonicDevices SonicSocketSet](https://github.com/stuin/SonicDevices/blob/master/src/main/java/com/stuintech/sonicdevices/socket/SonicSocketSet.java)

```
"entrypoints": {
    "socketwrench": [
      "com.stuintech.sonicdevices.socket.SonicSocketSet"
    ]
}
```

### Wrench
A basic wrench should run onFasten over the WRENCH_MASTER_KEY set, meaning that anything in FASTENER_SET_KEY, ADDON_SET_KEY, or ROTATE_SET_KEY will also be run, prioritized in that order.

Adding a new Socket to all wrenches is as easy as `SocketSetManager.addSocket(SocketSetManager.ADDON_SET_KEY, socket);`, and any wrench using the WRENCH_MASTER_KEY will run those sockets.

For easy implementation, any block or entity that implements the corresponding Fastener interface will be called by the FastenerSocket, and will follow the same conventions as a normal Socket.

Also note that the included wrenches implement CancelBlockInteraction, a special feature allowing them to skip interacting with blocks such as chests and repeaters.

* [BasicWrenchItem](https://github.com/stuin/SocketWrench/blob/master/src/main/java/com/stuintech/socketwrench/item/BasicWrenchItem.java)
* [ModeWrenchItem](https://github.com/stuin/SocketWrench/blob/master/src/main/java/com/stuintech/socketwrench/item/ModeWrenchItem.java)
* [FastenerBlock](https://github.com/stuin/SocketWrench/blob/master/src/main/java/com/stuintech/socketwrench/fasteners/FastenerBlock.java)

### New tools
Adding new non-wrench tools is also an option, and multiple modes is fully supported. 
Each mode or new wrench should connect to a separate SocketSet in the SocketSetManager, 
and included items can be used as examples or to simplify the process. 

More examples of tools and custom sockets can be found in [SonicDevices](https://github.com/stuin/SonicDevices/tree/master/src/main/java/com/stuintech/sonicdevices)


