## v.1.7.0

+ Core stat rework!
Stats like intelligence, strength and dexterity had a problem. They were used as requirements for gear. 
That means you picked them based on what gear you want to wear, not based on what which ones you wanted.
There was also a lack of variety so.. I added: Agility, Vitality and Willpower.
I separated them into 2 and 3 types. Let me explain.
There's Defense and Attack based core stats, and each of those are split into Melee, Ranger and Caster types.
So we have:

- Vitality (Melee defense)
- Strength (Melee attack)

- Willpower (Caster defense)
- Intelligence (Caster attack)

- Agility (Ranger defense)
- Dexterity (Ranger attack)

The actual stats they give aren't final but this is the general outline.
Now they give actual stats instead of multiplying your existing values. 
In return, I reduced player's base stats, as now your attributes determine that mostly.
Note, I'll probably have to set up limits or diminishing returns somehow later.

+ Crackdown on mob farming:
- iron golems and polar bears now dont drop loot
- npcs and animals now dont drop loot either (they had a very small chance)
- angerable mobs (those that don't attack you until you attack them, now have reduced loot and exp.)
All except endermen. Endermen are the only tough angerable mobs in vanilla.
You really had to farm animals that drop 100 times less loot?? I underestimated you guys.
- new update coming to Balance of Exile mod: remove ALL exp and loot from mobs spawned by mob spawners.
For the haters, all these features are configurable with entity config datapacks.

+ increased default stat scaling by a ton. Should be closer to mine and slash scaling now.
+ Reverted rarities to mine and slash ones. That means epic, legendary and mythic are back! I also added antique rarity.
Antiques are basically easier to get but worse relics.
+ compatible item datapack customization fields are now optional and have defaults
+ compatible item now uses gear rarity group datapacks instead of min and max rarity rank fields. 
The ranks were confusing, so they're gone. Now each rarity can tell if some other rarity is a higher version of itself or not.
The default if it's not inputted is all rarities besides unique.
+ durability tooltip now says unbreakable if item is unbreakable.
+ added more hp to npcs and less to animals. You don't have to spend so much effort slaughtering cows now.
+ gear name tooltips are now on 2 lines sometimes to reduce tooltip length
+ new affixes: magic find/item quantity affixes on jewelry and more.
+ spawner blocks now drop loot when mined! Apparently the stick doesn't work good enough, so I'm bringing carrots too.
+ fix talent tree being laggy, especially on datapacks that make bigger trees
+ fix hp gui rendering over F3 text
+ added high level area warning
+ environmental damage now scales to level
+ magic shield now reduces environmental damage instead of not reducing and additionally losing itself when target is damaged
In short, it's a needed buff. 
+ sync mana and magic shield better
+ plate and leather armor now has health as base stat, health affixes reduced
+ dodge max %: 80 > 90

B: known bug, poison can kill you now.
