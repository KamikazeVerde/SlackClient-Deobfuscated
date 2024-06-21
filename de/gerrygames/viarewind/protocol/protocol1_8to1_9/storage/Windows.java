/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*    */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*    */ import de.gerrygames.viarewind.utils.PacketUtil;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Windows extends StoredObject {
/* 19 */   private final HashMap<Short, String> types = new HashMap<>();
/* 20 */   private final HashMap<Short, Item[]> brewingItems = (HashMap)new HashMap<>();
/*    */   
/*    */   public Windows(UserConnection user) {
/* 23 */     super(user);
/*    */   }
/*    */   
/*    */   public String get(short windowId) {
/* 27 */     return this.types.get(Short.valueOf(windowId));
/*    */   }
/*    */   
/*    */   public void put(short windowId, String type) {
/* 31 */     this.types.put(Short.valueOf(windowId), type);
/*    */   }
/*    */   
/*    */   public void remove(short windowId) {
/* 35 */     this.types.remove(Short.valueOf(windowId));
/* 36 */     this.brewingItems.remove(Short.valueOf(windowId));
/*    */   }
/*    */   
/*    */   public Item[] getBrewingItems(short windowId) {
/* 40 */     return this.brewingItems.computeIfAbsent(Short.valueOf(windowId), key -> new Item[] { (Item)new DataItem(), (Item)new DataItem(), (Item)new DataItem(), (Item)new DataItem() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void updateBrewingStand(UserConnection user, Item blazePowder, short windowId) {
/* 49 */     if (blazePowder != null && blazePowder.identifier() != 377)
/* 50 */       return;  int amount = (blazePowder == null) ? 0 : blazePowder.amount();
/* 51 */     PacketWrapper openWindow = PacketWrapper.create((PacketType)ClientboundPackets1_8.OPEN_WINDOW, user);
/* 52 */     openWindow.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(windowId));
/* 53 */     openWindow.write(Type.STRING, "minecraft:brewing_stand");
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     Component title = ((TextComponent)((TextComponent)((TextComponent)Component.empty().append((Component)Component.translatable("container.brewing"))).append((Component)Component.text(": ", (TextColor)NamedTextColor.DARK_GRAY))).append((Component)Component.text(amount + " ", (TextColor)NamedTextColor.DARK_RED))).append((Component)Component.translatable("item.blazePowder.name", (TextColor)NamedTextColor.DARK_RED));
/* 59 */     openWindow.write(Type.COMPONENT, GsonComponentSerializer.colorDownsamplingGson().serializeToTree(title));
/* 60 */     openWindow.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)420));
/* 61 */     PacketUtil.sendPacket(openWindow, Protocol1_8TO1_9.class);
/*    */     
/* 63 */     Item[] items = ((Windows)user.get(Windows.class)).getBrewingItems(windowId);
/* 64 */     for (int i = 0; i < items.length; i++) {
/* 65 */       PacketWrapper setSlot = PacketWrapper.create((PacketType)ClientboundPackets1_8.SET_SLOT, user);
/* 66 */       setSlot.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(windowId));
/* 67 */       setSlot.write((Type)Type.SHORT, Short.valueOf((short)i));
/* 68 */       setSlot.write(Type.ITEM, items[i]);
/* 69 */       PacketUtil.sendPacket(setSlot, Protocol1_8TO1_9.class);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\storage\Windows.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */