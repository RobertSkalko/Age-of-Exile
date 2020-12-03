## v1.8.0


Skills Update! No, not the spells, but skills/professions!

Added many gathering skills: Mining, Farming, Exploring, and Fishing.
These skills level up as you mine ores, gather crops, loot chests or fish. 
You will gain extra random drops as your skill level increases.

Added crafting skills: Alchemy, Cooking, Tinkering and Inscribing.
These skills use ingredients from the gathering skills to craft useful items to aid you in your adventuring:
- instant hp potions from alchemy
- long-lasting food buffs from cooking
- protection tablets, enchanting scrolls and various teleportation scrolls from inscribing
- keys that unlock locked chests gained from exploration
- auto pickup backpacks that can store your skill materials or valuables
Leveling up any of these skills gives various buffs to your stats and experience gain too!
In fact, leveling your combat level is now harder, and bonus exp given by leveling skills should compensate for that.

These skills were added to provide a reason and rewards for doing things besides killing mobs and exploring.
They will provide you useful items to aid in combat, and ways to teleport to adventure and back!

* new overlay gui. I think this one will become the main one. 
Basically like hack slash mine's gui, just less textured.
It's fully configurable unlike the other guis.
* nerfed salvaging a bit
* increased mob hp
* nerfed ranged weapon base damage
* mob lvl is slower per distance now
* need bit more exp per lvl
* more hp, less regen (I hear there's too many one shot deaths, I want less of that)
* fix restoration to magic shield stat not working on spell effects
* added max wear cap for uniques and runeword gear back in (I had that in mine and slash but thought i could balance things perfectly this time, yeah that wasn't possible, everyone just wore runed gear)
* added new healer talents
* newbie gear drops now drop with much more durability
* newbie gear bag now costs much less
* jewelry should no longer drop as super high level when killing low level mobs. Same with all types of gear really but jewelry had the highest level variance and problem
* on high favor bosses and chests now drop extra loot but cost extra favor per item (think of it as faster to get loot, but with a higher favor cost)
if I'm correct, this will make it much harder to have too much favor but still rewarding enough so players don't resort to mob farms.
* player is now fully restored on level up
* added 2 items, empty and full favor bottle. Contains 1000 favor. 
Possible uses: modpacker way to give favor, players can save their favor if they don't want to spend it. etc.
* enabled keepinventory gamerule by default, instead of just hotbar and armor.
Reason: I upped difficulty, going back to spawn should be punishment enough.
 If not, you can disable it, or wait till I get an idea for a fair death peanlty in a mod where your gear is 90% of your strength.
* if item too high lvl, item is tried to put in inventory instead of dropping
* gear has more durability now
* added salvage bag item back in. 
Salvage bag now has a nifty feature, if you currently have a lower rarity gear equipped, it won't salvage that rarity or lower!
* buffed gear base defense values
* added death xp penalty back in (i accidentally disabled it)
* added repair hammers! Craftable with aoe gems like a pickaxe. These hammers when used repair all your gear by a certain amount!
* added level up reward bags
* added level up reward configs, works based on loot tables.
You lvl up, get the reward bag, click it, it gives you items based on the configured loot table.
This bag item can be used for more than level up rewards if modpackers wish.
* leveling now has milestone rewards! Get useful materials to progress as you level up!
* gem lvl req now higher, max tier is max lvl
* choose spell is now V, use spell is R hotkey. I think that's easier.
* essence items are now crafted with dyes instead
* critical damage now isn't a total multiplier and instead works like all other damage additions. Additive percent calculated at the end.
* reduced slow stat scaling from 100% more at max level, to 25% more.
This is for stats like critical damage that on a lvl 1 item give 10% crit but on lvl 50 item give 20% crit.
This scaling is supposed to be small and only there so lvl 1 items never get to be better than lvl 50 items. (in case they have no scaling stats)
High level players should feel less overpowered now.
* added spell cast hotkey! 
* items from craftable utilities mod have been transfered to this mod and are now part of inscribing skill
* fix items not being enchantable at anvil (this might not fix existing items)
* removed other gui overlay types as this one is configurable enough that it can replace them. 
* added bonus favor stat (used in exploration skill)
* when on 0 favor, exp is reduced by 4 times
* you gain increased exp gain on high favor
* added auto compat mod whitelist config. 
Now you can enable compat only for certain mods.
* added death statistics overlay when dead. 
Now you can see what damage types you died to. 
So if you die to mostly fire damage for example, you should know to up your resist.
* added hurt sounds to spells
* magic shield users are now knocked back by spells
* added chat warning if you have immersive portals installed (configurable)
* fixed projectile spells feeling laggy
* updated damage particle
* removed most elemental mobs. They weren't special enough to warrant being separate mobs. 
I will just increase the elemental affixes for existing mobs to compensate for the elemental damage loss.
More mobs will be coming in my World of Exile mod!
* mob affixes are now more meaningful
* fix chance to apply effect stats not working (they applied effect for 1 tick actually)
* crit damage starts at 150% now instead of 120%.
* stat points now give flat amounts, so each point is say 20 health, no matter your level.
The stat points you receive though are multiplied by the scaling.
* tweaked base stats
* new stat: accuracy
* mobs now all have dodge chance. You shouldn't notice if you build even a tiny amount of accuracy,
but battling high level mobs should be a bigger problem due to it.
* if dodge fails, it rolls a second time to try to fail the crit.
* melee and ranged spells are now affected by accuracy and dodge
* high rarity mobs now drop higher level loot. Legendary for example drop loot 2 levels higher.
This should encourage the killing of high rarity mobs a bit, especially for endgame players searching max level loot.
* added spell dodge and spell accuracy. These are "invisible" stats. 
For now they are used to allow mobs to dodge spells from low level players, same as they dodge the attacks.
They could be used in something else in the future, like bosses that avoid spells etc.
You shouldn't notice your spells missing at all as long as you aren't fighting mobs too high level.
* player base stats are now a datapack instead of a config 
Reason: too many times did I change the base stats and players with old configs had completely screwed games, 
because they already had a generated config that didn't change with update. 
Now as a datapack, that will only happen if the player actually changes something. 
* Crystal of Purification no longer a random drop anymore
* removed the lvl 10 boss golem, modify station now crafts with a diamond instead
* will, vit, wis removed. 
* removed stat point allocation
* talent screen lag optimization
* changed identifier of some perks, instead of spell_dmg_big, it's now big_spell_damage etc.
* max level changed to 100
* big talent perks now have a name and identity, to more easily share builds
"That one talent that gives int, health and mana" is probably not nice to say.
* added projectile damage stat, useful way of adding damage to both rangers and mages with projectile spells
* spell datapacks now have a tag field, projectile spells have projectile tag there.
* added damage taken to mana stat
* added spell crit chance and damage stats, "critical chance" now doesn't affect spells unless it has "spell" in the name.
* added all critical chance and damage stats, increases for both attack and spell based crits.
* lifesteal and other leech types now work on attack based spells too, not just basic attacks
* slightly reduced favor gains
* removed some stats like magic steal. Made new stats that allow for more customization.
Now you can have a "% Spell damage leeched as mana", or "% Attack damage leeched as magic shield" etc.
* spell damage now no longer works on attack based spells
* attack based skills now tag their weapon, so "% more sword damage" stat should work on them.
* added elemental damage over time stats
* new command /age_of_exile runtest. I will try to add tests here that are useful to myself, and maybe for modpack makers.
Could be useful for wiki editors too, like the exp requirement test command prints out all exp requirements.
* removed give spell stat. As spells are now skill gems.
* added give skill_gem command
* new datapack types: skill gems, random skill gem stats
* effects like burn can only by applied by correct element type damage now. So you can't apply burn with physical attacks.
* mana cost red now mana cost and works opposite
* cast speed now affects the cooldown of instant spells
* attack speed now acts as cast speed for attack based spells
* faster cast rate stat renamed to cast speed. (I'm trying to keep all stat names neutral, because "- 5 to faster rate" is confusing as hell)
* stat req in datapacks have all values now as optional, + base req feature. This means you can have something that requires 50 str at all levels.
Or maybe 20 str + scaling over lvls slowly.
* your starting gear and spell are now different based on which talent starting position you pick. (I had a few people who went archer but started with a wand lol)
* newbie gear now comes with unbreaking
* stat modifier in datapacks now saved differently. "first_min" is now "min1" to reduce size. etc (for modpack devs, you can just do a replaceall in files)
* stat modifiers now only save 2nd value when needed. Means it's optional now
* added reduced mana reserved stat and increased effect of auras stat

Spell adjustements:
* whirlpool is now at caster, not a projectile
* fire/frost etc balls now have about 4 times less cooldown, in other words, pew pew pew
* many more!