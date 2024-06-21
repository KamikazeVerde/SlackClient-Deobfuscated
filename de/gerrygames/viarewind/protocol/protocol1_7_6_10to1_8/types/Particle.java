/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public enum Particle {
/*  6 */   EXPLOSION_NORMAL("explode"),
/*  7 */   EXPLOSION_LARGE("largeexplode"),
/*  8 */   EXPLOSION_HUGE("hugeexplosion"),
/*  9 */   FIREWORKS_SPARK("fireworksSpark"),
/* 10 */   WATER_BUBBLE("bubble"),
/* 11 */   WATER_SPLASH("splash"),
/* 12 */   WATER_WAKE("wake"),
/* 13 */   SUSPENDED("suspended"),
/* 14 */   SUSPENDED_DEPTH("depthsuspend"),
/* 15 */   CRIT("crit"),
/* 16 */   CRIT_MAGIC("magicCrit"),
/* 17 */   SMOKE_NORMAL("smoke"),
/* 18 */   SMOKE_LARGE("largesmoke"),
/* 19 */   SPELL("spell"),
/* 20 */   SPELL_INSTANT("instantSpell"),
/* 21 */   SPELL_MOB("mobSpell"),
/* 22 */   SPELL_MOB_AMBIENT("mobSpellAmbient"),
/* 23 */   SPELL_WITCH("witchMagic"),
/* 24 */   DRIP_WATER("dripWater"),
/* 25 */   DRIP_LAVA("dripLava"),
/* 26 */   VILLAGER_ANGRY("angryVillager"),
/* 27 */   VILLAGER_HAPPY("happyVillager"),
/* 28 */   TOWN_AURA("townaura"),
/* 29 */   NOTE("note"),
/* 30 */   PORTAL("portal"),
/* 31 */   ENCHANTMENT_TABLE("enchantmenttable"),
/* 32 */   FLAME("flame"),
/* 33 */   LAVA("lava"),
/* 34 */   FOOTSTEP("footstep"),
/* 35 */   CLOUD("cloud"),
/* 36 */   REDSTONE("reddust"),
/* 37 */   SNOWBALL("snowballpoof"),
/* 38 */   SNOW_SHOVEL("snowshovel"),
/* 39 */   SLIME("slime"),
/* 40 */   HEART("heart"),
/* 41 */   BARRIER("barrier"),
/* 42 */   ICON_CRACK("iconcrack", 2),
/* 43 */   BLOCK_CRACK("blockcrack", 1),
/* 44 */   BLOCK_DUST("blockdust", 1),
/* 45 */   WATER_DROP("droplet"),
/* 46 */   ITEM_TAKE("take"),
/* 47 */   MOB_APPEARANCE("mobappearance");
/*    */   public final String name;
/*    */   
/*    */   static {
/* 51 */     particleMap = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 73 */     Particle[] particles = values();
/*    */     
/* 75 */     for (Particle particle : particles)
/* 76 */       particleMap.put(particle.name, particle); 
/*    */   }
/*    */   
/*    */   public final int extra;
/*    */   private static final HashMap<String, Particle> particleMap;
/*    */   
/*    */   Particle(String name, int extra) {
/*    */     this.name = name;
/*    */     this.extra = extra;
/*    */   }
/*    */   
/*    */   public static Particle find(String part) {
/*    */     return particleMap.get(part);
/*    */   }
/*    */   
/*    */   public static Particle find(int id) {
/*    */     if (id < 0)
/*    */       return null; 
/*    */     Particle[] values = values();
/*    */     return (id >= values.length) ? null : values[id];
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */