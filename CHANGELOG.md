## v.1.6.0

- All armors received new icons and models made by Sattekrokodil! Feedback is welcome, especially from texture artists.
- fix infused iron and other material recipes not existing
- new system, item sealing. Modifying items adds instability to them, and increases chance they become sealed.
What this means for players is that at endgame you can't just dump thousands of currencies on 1 good item. 
For everyone else, this shouldn't matter at all.
- added crystal of purification, unseals the item.
- new reward for leveling. Mobs drop enchanting experience bags, this is included in the "leveling rewards droprates"
No idea what else i can put there.
- added enchanting experience bag (for modpack makers, it has a single int field "exp", set it to any number)
- OPTIONAL CONFIG: armor and hotbar will no longer be dropped on death. (SUPER EXPERIMENTAL, PROBABLY BUGGY) 
I think arpgs where it can take years to get good gear shouldn't allow you to lose it that easily.
Depending on how stable this feature is, it will either become core or removed.
- trying out .md changelogs
- updated chinese lang
- fix orb of disorder giving infinite affixes
- fix shield and armor's stats not removing when de-equipped, only after other gear is changed
- added mana steal, and magic on hit stats
- reworked stat gui.
- gear now has more durability, armor, etc the higher level it is, and uniques have 20% more.
- projectile spells now work in water
- added rarity loot chat announcements. If player finds a unique item, it will be broadcasted to the server.
It doesn't work for everything currently, if it's crafted it won't be announced, but loot drops should.
It's configurable per rarity datapacks and a global config
- added back the stat ranges on item tooltips.
- nerf food mana regen slightly