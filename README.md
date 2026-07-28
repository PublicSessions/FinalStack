# FinalStack

A Fabric mod for Minecraft 1.21.11 that removes item stack size limits and adds count abbreviation.

## Features

- **Customizable max stack sizes** — Configure per-category via GUI:
  - Normal items (default 64): any value up to `Integer.MAX_VALUE`
  - 16-stack items (ender pearls, snowballs, etc.)
  - Unstackable items (tools, armor, etc.)
- **Count abbreviation** — `1000` → `1k`, `1174` → `1.2k`, `1,000,000` → `1M`, etc. (toggle on/off in GUI)
- **Rich configuration GUI** — Command or keybind to open, with live-adjustable values

## Usage

| Action | Method |
|--------|--------|
| Open config GUI | `/finalstack gui` or press **G** (configurable in Options → Controls → Key Binds) |
| Adjust stack sizes | Click text field and type, or use `+1` / `+10` / `+100` / `∞` buttons |
| Toggle abbreviation | Click **Count Abbreviation** button in GUI |

Config is auto-saved to `.minecraft/config/finalstack.json`.

## Dependencies

- Fabric Loader ≥ 0.19.3
- Fabric API ≥ 0.141.6+1.21.11
- Minecraft 1.21.11

## Technical

This mod uses 7 mixins to override stack limits at every relevant layer:

| Layer | Target |
|-------|--------|
| Item definition | `Item.getMaxCount()` |
| ItemStack | `ItemStack.getMaxCount()` |
| Inventory transfer | `SimpleInventory.transfer` – `getMaxCount()` redirect |
| Slot limit | `Slot.getMaxItemCount()` |
| Data component | `DataComponentTypes.MAX_STACK_SIZE` range |
| Packet validation | `ItemStack$3.decode` – skip round-trip encode validation |
| Codec | `ItemStack.validate()` – skip count validation |
| Render | `DrawContext.drawStackCount` – `String.valueOf` redirect for abbreviation |

## License

CC0-1.0
