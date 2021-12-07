v.4.0.13
Infinite dungeons now scale to your level
Additional infinite dungeon addon compat (Mine and slash specifics drops :))
MNS crates from infinite dungeons should now nearly all be set to your level when you aquired them, not when you open

v.4.0.12

fix currencies not dropping

v.4.0.11

fix favor disabling to work, hopefully

v.4.0.9

* switched to server configs
* fix some compat config bugs

v.4.0.8

* higher gear rarity now has innate unbreaking, the higher the rarity, the better.
* added a common gear stat soul producer item. This item can be crafted with vanilla materials and produces a soul which can create a common item out of anything. This is meant for people who lose their gear after death. The produced item can't be salvaged
* added back identify tome, now identifies all souls in your inventory

v4.0.7

Gem system rework:
1) gem stats are no longer random
2) gems can now be extracted from their sockets with a gem extractor
3) higher tier gems no longer drop
4) crafting higher tier gems has a high chance to fail
5) removed the original recipe

increased base stats on weapons
decreased int dex str stats given

v.4.0.6

fix item tiers being wrong sometimes, when salvaging too
fix arrows spells not being castable on servers


v.4.0.5

fix archers not doing mns damage

v.4.0.4

gems now no longer have a level. They now get the level of the gear.
Higher tier gems have better stats. Droprates of higher tier gems heavily nerfed

upgrades now add sockets at +4,8 and 12, instead of adding a transcendant affix
transcendant affixes removed

fix mobs not doing mns dmg because of attack cooldown..

v.4.0.3

changed major version to 4 to differentiate from 1.15

Removed salvaging station, added salvage hammer! (stick and 6 cobbles to craft it)
Just tap the hammer on an item to salvage it.

Higher rarity items now drop rarity dust too when salvaged. Click to combine it into an upgrade stone.

changed rarity datapacks (modpackers note)
changed core stats to give only flat stats, and made core stats scale normally

should allow crossbows to shoot spells


v.3.0.2

Added a scaling per player difficulty system. [Experimental]
I always wanted some way to push player to progress and get stronger, and this could be a good way.
I will be taking hints from the Scaling health mod everyone knows, to try and make this work.
It is a bit more difficult because mob levels will increase and that's where extra balance work will be needed.
In short, as you play more and kill mobs, difficulty will increase. If you are progressing fast enough, this will be good, as more areas are now useful and you don't have to travel that far out.
On the other hand if you are progressing slowly, mobs will become too difficult to defeat, and you will have a hard time.
There are enough configs to completely disable or alter the system.

Added item upgrading. [Experimental]
Every gear can now be upgraded with upgrade stones. 
Each upgrade increases the item level.

Added transcendent affixes.
When you upgrade your item enough, a random one will appear.
Further upgrades will enhance the affix at certain breakpoints.

fix bow not working
fix runeword border
try fix a crash on some thrown weapons
should fix crash with newest dungeons of exile version

v.3.0.1
bug fixes

#v3.0.0

This update finally brings Mine and Slash to 1.16.5 Forge!
I want to talk about the main changes that I'm excited about first.

# Gear system is entirely reworked.

From now on, gear no longer drops. But wait! Instead of gear dropping, what drops is a Soul.
That soul can be inserted into a piece of gear to give it stats. Kind of like socketing a gem in most other games.

What this means is:
- 99% of gear is now compatible by default!
- All gear is now useful, as you will always need new fresh gears for inserting souls into. 
- Vanilla progression actually matters, as you probably want gear with lots of durability, like that sweet netherite gear.
- It's much easier on me to develop, as I don't have to make new item models and icons.

I hope this can satisfy the whole playerbase as I know how much everyone hated dealing with gear compatibility issues! In case some gears aren't recognized like say a Hammer from some other mod. You can easily add compatibility with either config or datapack. You just need to specify item id, and what type of item it is. And as long as the mod knows the item is a Hammer, you can put any Hammer soul you find on it!

# Professions.

A long requested feature by players was consumable and gear crafting. So I thought, why not add both? 

This update adds 6 new professions:
- Alchemy
- Cooking
- Inscribing
- Jewel crafting
- Armor crafting
- Weapon crafting


The way it works is simple. Mobs can drop ingredients, and you can use them to craft things.
Not all ingredients can be used for all professions. So mix and match what you find to craft the things you need.
You can make potions, short but strong buffs. Foods, which are long lasting buffs. You can even craft gear with stats you want! Although beware, there is a chance of failure the more ingredients are used.

# The draggy clicky QOL

Ever had gear on low durability in the middle of an adventure? Or a gem dropped but you are away from your socketing station at home? No worries, I deleted both the repair, socketing and modify stations! Now you don't need them anymore, as you can just drag the item. Drag the gem to your gear and it's socketed! Drag the repair material to your broken armor piece and it's repaired!


# Other notable changes
- Runes now give no stats, and are instead used to craft runewords at the Runeword station. The station has a simplified interface with neat QOL features like telling you what runewords you can craft on which item type and whether you have the runes required for them.
- Spell school gui has been redesigned to be prettier and easier to use. Now you can setup your hotbar in the same gui where you pick spells!
- Casting improvements! You no longer need to press shift to cast spells! That is only required for the second hotbar now. (which you can fill with high cooldown buffs and ultimate spells)
- All items can now be socketed! Once. In my play tests, this made gems much more useful.
- gear stat requirements removed
- gear now comes in tiers, not levels. This means if you are killing mobs your tier, you will always get gear that you can wear right now. You can wear T1 gear at lvl 1, T2 gear at lvl 10 etc. The higher the gear rarity, the higher the effective level of the gear. 
This means getting a high rarity gear is key to progressing. No longer will you get a slightly higher level crappy gear to replace your legendary.
- less gear rarities, more meaningful differences. You will feel the change from each rarity. An epic gear has about 10 effective levels more than a common gear, making it that much stronger.
- talents have been temporarily removed due to design difficulties
- blocking now spends energy, and running stops energy regeneration.
