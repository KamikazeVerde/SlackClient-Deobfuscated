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
/*     */ import cc.slack.utils.player.PlayerUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ContainerChest;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ @ModuleInfo(name = "Stealer", category = Category.WORLD)
/*     */ public class Stealer
/*     */   extends Module {
/*  36 */   private final NumberValue<Double> openDelaymax = new NumberValue("Open Delay Max", Double.valueOf(150.0D), Double.valueOf(25.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*  37 */   private NumberValue<Double> openDelaymin = new NumberValue("Open Delay Min", Double.valueOf(125.0D), Double.valueOf(25.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*  38 */   private final NumberValue<Double> stealDelaymax = new NumberValue("Steal Delay Max", Double.valueOf(150.0D), Double.valueOf(25.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*  39 */   private NumberValue<Double> stealDelaymin = new NumberValue("Steal Delay Min", Double.valueOf(125.0D), Double.valueOf(25.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*  40 */   private final BooleanValue autoClose = new BooleanValue("Auto Close", true);
/*  41 */   private final NumberValue<Double> autocloseDelaymax = new NumberValue("Auto Close Delay Max", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*  42 */   private NumberValue<Double> autocloseDelaymin = new NumberValue("Auto Close Delay Min", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*     */ 
/*     */   
/*  45 */   private final AtomicReference<ArrayList<Slot>> sortedSlots = new AtomicReference<>();
/*  46 */   private final AtomicReference<ContainerChest> chest = new AtomicReference<>();
/*  47 */   private final AtomicBoolean inChest = new AtomicBoolean(false);
/*  48 */   private final MathTimerUtil delayTimer = new MathTimerUtil(0L);
/*  49 */   private final MathTimerUtil closeTimer = new MathTimerUtil(0L);
/*  50 */   private final List<Item> whiteListedItems = Arrays.asList(new Item[] { Items.milk_bucket, Items.golden_apple, (Item)Items.potionitem, Items.ender_pearl, Items.water_bucket, Items.arrow, (Item)Items.bow });
/*     */ 
/*     */   
/*     */   public Stealer() {
/*  54 */     addSettings(new Value[] { (Value)this.openDelaymax, (Value)this.openDelaymin, (Value)this.stealDelaymax, (Value)this.stealDelaymin, (Value)this.autoClose, (Value)this.autocloseDelaymax, (Value)this.autocloseDelaymin });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent event) {
/*  61 */     if (((Double)this.openDelaymin.getValue()).doubleValue() > ((Double)this.openDelaymax.getValue()).doubleValue()) this.openDelaymin = this.openDelaymax; 
/*  62 */     if (((Double)this.stealDelaymin.getValue()).doubleValue() > ((Double)this.stealDelaymax.getValue()).doubleValue()) this.stealDelaymin = this.stealDelaymax; 
/*  63 */     if (((Double)this.autocloseDelaymin.getValue()).doubleValue() > ((Double)this.autocloseDelaymax.getValue()).doubleValue()) this.autocloseDelaymin = this.autocloseDelaymax;
/*     */     
/*  65 */     if (mc.getCurrentScreen() != null && (mc.getPlayer()).inventoryContainer != null && 
/*  66 */       (mc.getPlayer()).inventoryContainer instanceof net.minecraft.inventory.ContainerPlayer && 
/*  67 */       mc.getCurrentScreen() instanceof net.minecraft.client.gui.inventory.GuiChest) {
/*  68 */       if (!this.inChest.get()) {
/*  69 */         this.chest.set((ContainerChest)(mc.getPlayer()).openContainer);
/*  70 */         this.delayTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(((Double)this.openDelaymin.getValue()).doubleValue(), ((Double)this.openDelaymax
/*  71 */               .getValue()).doubleValue() + 0.01D));
/*  72 */         this.delayTimer.start();
/*  73 */         generatePath(this.chest.get());
/*  74 */         this.inChest.set(true);
/*     */       } 
/*     */       
/*  77 */       if (this.inChest.get() && this.sortedSlots.get() != null && !((ArrayList)this.sortedSlots.get()).isEmpty() && 
/*  78 */         this.delayTimer.hasFinished()) {
/*  79 */         clickSlot(((Slot)((ArrayList)this.sortedSlots.get()).get(0)).s);
/*  80 */         this.delayTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(((Double)this.stealDelaymin.getValue()).doubleValue(), ((Double)this.stealDelaymax
/*  81 */               .getValue()).doubleValue() + 0.01D));
/*  82 */         this.delayTimer.start();
/*  83 */         ((ArrayList)this.sortedSlots.get()).remove(0);
/*     */       } 
/*     */ 
/*     */       
/*  87 */       if (this.sortedSlots.get() != null && ((ArrayList)this.sortedSlots.get()).isEmpty() && ((Boolean)this.autoClose.getValue()).booleanValue()) {
/*  88 */         if (this.closeTimer.firstFinish()) {
/*  89 */           mc.getPlayer().closeScreen();
/*  90 */           this.inChest.set(false);
/*     */         } else {
/*  92 */           this.closeTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(((Double)this.autocloseDelaymin.getValue()).doubleValue(), ((Double)this.autocloseDelaymax
/*  93 */                 .getValue()).doubleValue() + 0.01D));
/*  94 */           this.closeTimer.start();
/*     */         } 
/*     */       }
/*     */     } else {
/*  98 */       this.inChest.set(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generatePath(ContainerChest chest) {
/* 123 */     ArrayList<Slot> slots = (ArrayList<Slot>)IntStream.range(0, chest.getLowerChestInventory().getSizeInventory()).mapToObj(i -> { ItemStack itemStack = chest.getInventory().get(i); if (itemStack != null) { Predicate<ItemStack> stealCondition = (); if (stealCondition.test(itemStack)) return new Slot(i);  }  return null; }).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
/*     */     
/* 125 */     Slot[] sorted = sort(slots.<Slot>toArray(new Slot[0]));
/* 126 */     this.sortedSlots.set(new ArrayList<>(Arrays.asList(sorted)));
/*     */   }
/*     */   
/*     */   private Slot[] sort(Slot[] in) {
/* 130 */     if (in == null || in.length == 0) {
/* 131 */       return in;
/*     */     }
/* 133 */     Slot[] out = new Slot[in.length];
/* 134 */     Slot current = in[ThreadLocalRandom.current().nextInt(0, in.length)];
/* 135 */     for (int i = 0; i < in.length; i++) {
/* 136 */       Slot currentSlot = current;
/* 137 */       if (i == in.length - 1) {
/* 138 */         out[in.length - 1] = Arrays.<Slot>stream(in).filter(p -> !p.visited).findAny().orElse(null);
/*     */         break;
/*     */       } 
/* 141 */       out[i] = current;
/* 142 */       current.visit();
/*     */       
/* 144 */       Slot next = Arrays.<Slot>stream(in).filter(p -> !p.visited).min(Comparator.comparingDouble(p -> p.getDistance(currentSlot))).orElse(null);
/* 145 */       current = next;
/*     */     } 
/* 147 */     return out;
/*     */   }
/*     */   
/*     */   static class Slot {
/*     */     final int x;
/*     */     final int y;
/*     */     final int s;
/*     */     boolean visited;
/*     */     
/*     */     Slot(int s) {
/* 157 */       this.x = (s + 1) % 10;
/* 158 */       this.y = s / 9;
/* 159 */       this.s = s;
/*     */     }
/*     */     
/*     */     public double getDistance(Slot s) {
/* 163 */       return (Math.abs(this.x - s.x) + Math.abs(this.y - s.y));
/*     */     }
/*     */     
/*     */     public void visit() {
/* 167 */       this.visited = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private void clickSlot(int x) {
/* 172 */     mc.getPlayerController().windowClick((mc.getPlayer()).openContainer.windowId, x, 0, 1, (EntityPlayer)mc.getPlayer());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\world\Stealer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */