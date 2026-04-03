ModListChecker

A NeoForge mod for Minecraft 1.21.1 that validates connecting players' mod lists against a server-configured allowlist. Players with mods not on the allowlist, or missing required mods, will be disconnected.
Installation
Server

Place modlistchecker-x.x.x.jar into your server's mods/ folder
Start the server once to generate the config file
Open config/modlistchecker-server.toml
Edit the allowedMods list with the mod IDs you want to allow:

tomlallowedMods = ["minecraft", "neoforge", "modlistchecker", "jei", "waystones"]

Restart the server

Client

Place modlistchecker-x.x.x.jar into your mods/ folder
No configuration needed on the client side

How It Works
When a player connects to the server, the mod sends their mod list to the server. The server then compares it against the allowlist defined in the config. If the player has any mods not on the list, or is missing any required mods, they will be disconnected with a message showing which mods are missing or not allowed.
Finding Mod IDs
Mod IDs can be found in the server log on startup, or inside each mod's neoforge.mods.toml file.
Compatibility

Minecraft: 1.21.1
Loader: NeoForge 21.1.x
Required on: Server and Client

Credits
Made by JozoWasTaken
