/*     */ package cc.slack.features.modules.impl.world;
/*     */ 
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.other.MathTimerUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemAxe;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemPickaxe;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ 
/*     */ @ModuleInfo(name = "InvManager", category = Category.WORLD)
/*     */ public class InvManager
/*     */   extends Module
/*     */ {
/*  37 */   private final NumberValue<Long> managerDelayvalue = new NumberValue("Delay", Long.valueOf(150L), Long.valueOf(0L), Long.valueOf(300L), Long.valueOf(25L));
/*  38 */   private final BooleanValue openInvvalue = new BooleanValue("Open Inventory", true);
/*  39 */   private final BooleanValue autoArmorvalue = new BooleanValue("AutoArmor", false);
/*  40 */   private final BooleanValue noTrashvalue = new BooleanValue("No Trash", true);
/*  41 */   private final BooleanValue noMovevalue = new BooleanValue("No Move", true);
/*     */   
/*  43 */   private final int INVENTORY_ROWS = 4, INVENTORY_COLUMNS = 9, ARMOR_SLOTS = 4;
/*  44 */   private final int INVENTORY_SLOTS = 40;
/*     */   
/*     */   private PlayerControllerMP playerController;
/*     */   
/*  48 */   private final MathTimerUtil timer = new MathTimerUtil(0L);
/*     */   private boolean movedItem;
/*     */   private boolean inventoryOpen;
/*     */   
/*     */   public InvManager() {
/*  53 */     addSettings(new Value[] { (Value)this.managerDelayvalue, (Value)this.openInvvalue, (Value)this.autoArmorvalue, (Value)this.noTrashvalue, (Value)this.noMovevalue });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  58 */     this.timer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  63 */     closeInventory();
/*     */   }
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent event) {
/*  69 */     if (!this.timer.reached(((Long)this.managerDelayvalue.getValue()).longValue())) {
/*  70 */       closeInventory();
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     if (mc.getCurrentScreen() instanceof net.minecraft.client.gui.inventory.GuiChest) {
/*     */       return;
/*     */     }
/*  77 */     if (((mc.getGameSettings()).keyBindJump.isKeyDown() || (mc.getGameSettings()).keyBindForward.isKeyDown() || 
/*  78 */       (mc.getGameSettings()).keyBindLeft.isKeyDown() || (mc.getGameSettings()).keyBindBack.isKeyDown() || 
/*  79 */       (mc.getGameSettings()).keyBindRight.isKeyDown()) && ((Boolean)this.noMovevalue.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*  82 */     this.movedItem = false;
/*  83 */     this.timer.reset();
/*  84 */     this.timer.reached(((Long)this.managerDelayvalue.getValue()).longValue());
/*     */     
/*  86 */     if (!(mc.getCurrentScreen() instanceof net.minecraft.client.gui.inventory.GuiInventory) && ((Boolean)this.openInvvalue.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*  89 */     this.playerController = mc.getPlayerController();
/*     */     
/*  91 */     if (((Boolean)this.noTrashvalue.getValue()).booleanValue()) {
/*  92 */       for (int j = 0; j < 40; j++) {
/*  93 */         ItemStack itemStack = (mc.getPlayer()).inventory.getStackInSlot(j);
/*     */         
/*  95 */         if (itemStack != null && itemStack.getItem() != null)
/*     */         {
/*     */           
/*  98 */           if (!itemWhitelisted(itemStack)) {
/*  99 */             throwItem(getSlotId(j));
/*     */           }
/*     */         }
/*     */       } 
/*     */     }
/* 104 */     Integer bestHelmet = null;
/* 105 */     Integer bestChestPlate = null;
/* 106 */     Integer bestLeggings = null;
/* 107 */     Integer bestBoots = null;
/* 108 */     Integer bestSword = null;
/* 109 */     Integer bestPickaxe = null;
/* 110 */     Integer bestAxe = null;
/* 111 */     Integer bestBlock = null;
/* 112 */     Integer bestBow = null;
/* 113 */     Integer bestPotion = null;
/* 114 */     Integer bestGaps = null;
/*     */     int i;
/* 116 */     for (i = 0; i < 40; i++) {
/* 117 */       ItemStack itemStack = (mc.getPlayer()).inventory.getStackInSlot(i);
/*     */       
/* 119 */       if (itemStack != null && itemStack.getItem() != null) {
/*     */ 
/*     */         
/* 122 */         Item item = itemStack.getItem();
/*     */         
/* 124 */         if (item instanceof ItemArmor) {
/* 125 */           ItemArmor armor = (ItemArmor)item;
/* 126 */           int damageReductionItem = getArmorDamageReduction(itemStack);
/*     */           
/* 128 */           if (armor.armorType == 0 && (
/* 129 */             bestHelmet == null || damageReductionItem > getArmorDamageReduction(
/* 130 */               (mc.getPlayer()).inventory.getStackInSlot(bestHelmet.intValue())))) {
/* 131 */             bestHelmet = Integer.valueOf(i);
/*     */           }
/*     */ 
/*     */           
/* 135 */           if (armor.armorType == 1 && (
/* 136 */             bestChestPlate == null || damageReductionItem > getArmorDamageReduction(
/* 137 */               (mc.getPlayer()).inventory.getStackInSlot(bestChestPlate.intValue())))) {
/* 138 */             bestChestPlate = Integer.valueOf(i);
/*     */           }
/*     */ 
/*     */           
/* 142 */           if (armor.armorType == 2 && (
/* 143 */             bestLeggings == null || damageReductionItem > getArmorDamageReduction(
/* 144 */               (mc.getPlayer()).inventory.getStackInSlot(bestLeggings.intValue())))) {
/* 145 */             bestLeggings = Integer.valueOf(i);
/*     */           }
/*     */ 
/*     */           
/* 149 */           if (armor.armorType == 3 && (
/* 150 */             bestBoots == null || damageReductionItem > getArmorDamageReduction(
/* 151 */               (mc.getPlayer()).inventory.getStackInSlot(bestBoots.intValue())))) {
/* 152 */             bestBoots = Integer.valueOf(i);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 158 */         if (item instanceof ItemSword) {
/* 159 */           float damage = getSwordDamage(itemStack);
/* 160 */           if (bestSword == null || damage > getSwordDamage((mc.getPlayer()).inventory.getStackInSlot(bestSword.intValue()))) {
/* 161 */             bestSword = Integer.valueOf(i);
/*     */           }
/*     */         } 
/*     */         
/* 165 */         if (item instanceof ItemPickaxe) {
/* 166 */           float mineSpeed = getMineSpeed(itemStack);
/* 167 */           if (bestPickaxe == null || mineSpeed > getMineSpeed((mc.getPlayer()).inventory.getStackInSlot(bestPickaxe.intValue()))) {
/* 168 */             bestPickaxe = Integer.valueOf(i);
/*     */           }
/*     */         } 
/*     */         
/* 172 */         if (item instanceof ItemAxe) {
/* 173 */           float mineSpeed = getMineSpeed(itemStack);
/* 174 */           if (bestAxe == null || mineSpeed > getMineSpeed((mc.getPlayer()).inventory.getStackInSlot(bestAxe.intValue()))) {
/* 175 */             bestAxe = Integer.valueOf(i);
/*     */           }
/*     */         } 
/*     */         
/* 179 */         if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().isFullCube()) {
/* 180 */           float amountOfBlocks = itemStack.stackSize;
/* 181 */           if (bestBlock == null || amountOfBlocks > ((mc.getPlayer()).inventory.getStackInSlot(bestBlock.intValue())).stackSize) {
/* 182 */             bestBlock = Integer.valueOf(i);
/*     */           }
/*     */         } 
/*     */         
/* 186 */         if (item instanceof net.minecraft.item.ItemBow) {
/* 187 */           int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
/* 188 */           if (bestBow == null || level > 1) {
/* 189 */             bestBow = Integer.valueOf(i);
/*     */           }
/*     */         } 
/*     */         
/* 193 */         if (item instanceof net.minecraft.item.ItemAppleGold) {
/* 194 */           float amountOfGaps = itemStack.stackSize;
/* 195 */           if (bestGaps == null || amountOfGaps > 1.0F) {
/* 196 */             bestGaps = Integer.valueOf(i);
/*     */           }
/*     */         } 
/*     */         
/* 200 */         if (item instanceof ItemPotion) {
/* 201 */           ItemPotion itemPotion = (ItemPotion)item;
/* 202 */           if (bestPotion == null && ItemPotion.isSplash(itemStack.getMetadata()) && itemPotion
/* 203 */             .getEffects(itemStack.getMetadata()) != null) {
/* 204 */             int potionID = ((PotionEffect)itemPotion.getEffects(itemStack.getMetadata()).get(0)).getPotionID();
/* 205 */             boolean isPotionActive = false;
/*     */             
/* 207 */             for (PotionEffect potion : mc.getPlayer().getActivePotionEffects()) {
/* 208 */               if (potion.getPotionID() == potionID && potion.getDuration() > 0) {
/* 209 */                 isPotionActive = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 214 */             ArrayList<Integer> whitelistedPotions = new ArrayList<Integer>()
/*     */               {
/*     */               
/*     */               };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 225 */             if (!isPotionActive && (whitelistedPotions.contains(Integer.valueOf(potionID)) || potionID == 10 || potionID == 6))
/* 226 */               bestPotion = Integer.valueOf(i); 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 231 */     if (((Boolean)this.noTrashvalue.getValue()).booleanValue()) {
/* 232 */       for (i = 0; i < 40; i++) {
/* 233 */         ItemStack itemStack = (mc.getPlayer()).inventory.getStackInSlot(i);
/*     */         
/* 235 */         if (itemStack != null && itemStack.getItem() != null) {
/*     */ 
/*     */           
/* 238 */           Item item = itemStack.getItem();
/*     */           
/* 240 */           if (item instanceof ItemArmor) {
/* 241 */             ItemArmor armor = (ItemArmor)item;
/*     */             
/* 243 */             if ((armor.armorType == 0 && bestHelmet != null && i != bestHelmet.intValue()) || (armor.armorType == 1 && bestChestPlate != null && i != bestChestPlate.intValue()) || (armor.armorType == 2 && bestLeggings != null && i != bestLeggings.intValue()) || (armor.armorType == 3 && bestBoots != null && i != bestBoots.intValue())) {
/* 244 */               throwItem(getSlotId(i));
/*     */             }
/*     */           } 
/*     */           
/* 248 */           if (item instanceof ItemSword && 
/* 249 */             bestSword != null && i != bestSword.intValue()) {
/* 250 */             throwItem(getSlotId(i));
/*     */           }
/*     */ 
/*     */           
/* 254 */           if (item instanceof ItemPickaxe && 
/* 255 */             bestPickaxe != null && i != bestPickaxe.intValue()) {
/* 256 */             throwItem(getSlotId(i));
/*     */           }
/*     */ 
/*     */           
/* 260 */           if (item instanceof ItemAxe && 
/* 261 */             bestAxe != null && i != bestAxe.intValue()) {
/* 262 */             throwItem(getSlotId(i));
/*     */           }
/*     */ 
/*     */           
/* 266 */           if (item instanceof net.minecraft.item.ItemAppleGold && 
/* 267 */             bestGaps != null && i != bestGaps.intValue()) {
/* 268 */             throwItem(getSlotId(i));
/*     */           }
/*     */ 
/*     */           
/* 272 */           if (item instanceof net.minecraft.item.ItemBow && 
/* 273 */             bestBow != null && i != bestBow.intValue()) {
/* 274 */             throwItem(getSlotId(i));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 280 */     if (((Boolean)this.autoArmorvalue.getValue()).booleanValue()) {
/*     */       
/* 282 */       if (bestHelmet != null) {
/* 283 */         equipArmor(getSlotId(bestHelmet.intValue()));
/*     */       }
/* 285 */       if (bestChestPlate != null) {
/* 286 */         equipArmor(getSlotId(bestChestPlate.intValue()));
/*     */       }
/* 288 */       if (bestLeggings != null) {
/* 289 */         equipArmor(getSlotId(bestLeggings.intValue()));
/*     */       }
/* 291 */       if (bestBoots != null) {
/* 292 */         equipArmor(getSlotId(bestBoots.intValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getSwordDamage(ItemStack itemStack) {
/* 298 */     ItemSword sword = (ItemSword)itemStack.getItem();
/* 299 */     int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
/* 300 */     return (float)(sword.getDamageVsEntity() + efficiencyLevel * 1.25D);
/*     */   }
/*     */   
/*     */   private int getArmorDamageReduction(ItemStack itemStack) {
/* 304 */     return ((ItemArmor)itemStack.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
/*     */   }
/*     */   
/*     */   private void openInventory() {
/* 308 */     if (!this.inventoryOpen) {
/* 309 */       this.inventoryOpen = true;
/* 310 */       (mc.getPlayer()).sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeInventory() {
/* 315 */     if (this.inventoryOpen) {
/* 316 */       this.inventoryOpen = false;
/* 317 */       (mc.getPlayer()).sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(0));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void throwItem(int slot) {
/*     */     try {
/* 323 */       if (!this.movedItem) {
/* 324 */         openInventory();
/* 325 */         this.playerController.windowClick((mc.getPlayer()).inventoryContainer.windowId, slot, 1, 4, (EntityPlayer)mc.getPlayer());
/* 326 */         this.movedItem = true;
/*     */       } 
/* 328 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*     */   }
/*     */   
/*     */   private void equipArmor(int slot) {
/*     */     try {
/* 333 */       if (slot > 8 && !this.movedItem) {
/* 334 */         openInventory();
/* 335 */         this.playerController.windowClick((mc.getPlayer()).inventoryContainer.windowId, slot, 0, 1, (EntityPlayer)mc.getPlayer());
/* 336 */         this.movedItem = true;
/*     */       } 
/* 338 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*     */   }
/*     */   
/*     */   public int getSlotId(int slot) {
/* 342 */     if (slot >= 36)
/* 343 */       return 8 - slot - 36; 
/* 344 */     if (slot < 9)
/* 345 */       return slot + 36; 
/* 346 */     return slot;
/*     */   }
/*     */   
/*     */   private boolean itemWhitelisted(ItemStack itemStack) {
/* 350 */     ArrayList<Item> whitelistedItems = new ArrayList<Item>()
/*     */       {
/*     */       
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 360 */     Item item = itemStack.getItem();
/*     */     
/* 362 */     ArrayList<Integer> whitelistedPotions = new ArrayList<Integer>()
/*     */       {
/*     */       
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     if (item instanceof ItemPotion) {
/* 376 */       int potionID = getPotionId(itemStack);
/* 377 */       return whitelistedPotions.contains(Integer.valueOf(potionID));
/*     */     } 
/*     */     
/* 380 */     return ((item instanceof ItemBlock && !(((ItemBlock)item).getBlock() instanceof net.minecraft.block.BlockTNT) && !(((ItemBlock)item).getBlock() instanceof net.minecraft.block.BlockChest) && !(((ItemBlock)item).getBlock() instanceof net.minecraft.block.BlockFalling)) || item instanceof net.minecraft.item.ItemAnvilBlock || item instanceof ItemSword || item instanceof ItemArmor || item instanceof net.minecraft.item.ItemTool || item instanceof net.minecraft.item.ItemFood || (whitelistedItems.contains(item) && !item.equals(Items.spider_eye)));
/*     */   }
/*     */   
/*     */   private int getPotionId(ItemStack potion) {
/* 384 */     Item item = potion.getItem();
/*     */     
/*     */     try {
/* 387 */       if (item instanceof ItemPotion) {
/* 388 */         ItemPotion p = (ItemPotion)item;
/* 389 */         return ((PotionEffect)p.getEffects(potion.getMetadata()).get(0)).getPotionID();
/*     */       } 
/* 391 */     } catch (NullPointerException nullPointerException) {}
/*     */     
/* 393 */     return 0;
/*     */   }
/*     */   
/*     */   private float getMineSpeed(ItemStack itemStack) {
/* 397 */     Item item = itemStack.getItem();
/* 398 */     int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
/*     */     
/* 400 */     switch (efficiencyLevel) {
/*     */       case 1:
/* 402 */         efficiencyLevel = 30;
/*     */         break;
/*     */       case 2:
/* 405 */         efficiencyLevel = 69;
/*     */         break;
/*     */       case 3:
/* 408 */         efficiencyLevel = 120;
/*     */         break;
/*     */       case 4:
/* 411 */         efficiencyLevel = 186;
/*     */         break;
/*     */       case 5:
/* 414 */         efficiencyLevel = 271;
/*     */         break;
/*     */       
/*     */       default:
/* 418 */         efficiencyLevel = 0;
/*     */         break;
/*     */     } 
/*     */     
/* 422 */     if (item instanceof ItemPickaxe || item instanceof ItemAxe) {
/* 423 */       return getToolEfficiency(item) + efficiencyLevel;
/*     */     }
/* 425 */     return 0.0F;
/*     */   }
/*     */   
/*     */   private float getToolEfficiency(Item item) {
/* 429 */     if (item instanceof ItemPickaxe)
/* 430 */       return ((ItemPickaxe)item).getToolMaterial().getEfficiencyOnProperMaterial(); 
/* 431 */     if (item instanceof ItemAxe) {
/* 432 */       return ((ItemAxe)item).getToolMaterial().getEfficiencyOnProperMaterial();
/*     */     }
/* 434 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\world\InvManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */