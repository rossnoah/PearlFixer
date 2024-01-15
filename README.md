# Pearl Fixer

## What is this?

This is a simple plugin to allow ender pearls to be thrown past the normal server simulation distance.
It is intended to be used on servers with a low simulation distance. 
A common use case is to allow for more optimized setups and not have to worry about the simulation distance interfering with pvp.


## Installation

One paper setting must be set specifically for this plugin to work.
In `paper-world-defaults.yml` set `disable-unloaded-chunk-enderpearl-exploit: false` for the plugin to work.
This can also be set on a per world.