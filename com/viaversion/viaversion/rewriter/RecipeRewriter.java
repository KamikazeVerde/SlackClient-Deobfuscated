/*     */ package com.viaversion.viaversion.rewriter;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class RecipeRewriter<C extends ClientboundPacketType>
/*     */ {
/*     */   protected final Protocol<C, ?, ?, ?> protocol;
/*  33 */   protected final Map<String, RecipeConsumer> recipeHandlers = new HashMap<>();
/*     */   
/*     */   public RecipeRewriter(Protocol<C, ?, ?, ?> protocol) {
/*  36 */     this.protocol = protocol;
/*  37 */     this.recipeHandlers.put("crafting_shapeless", this::handleCraftingShapeless);
/*  38 */     this.recipeHandlers.put("crafting_shaped", this::handleCraftingShaped);
/*  39 */     this.recipeHandlers.put("smelting", this::handleSmelting);
/*     */ 
/*     */     
/*  42 */     this.recipeHandlers.put("blasting", this::handleSmelting);
/*  43 */     this.recipeHandlers.put("smoking", this::handleSmelting);
/*  44 */     this.recipeHandlers.put("campfire_cooking", this::handleSmelting);
/*  45 */     this.recipeHandlers.put("stonecutting", this::handleStonecutting);
/*     */ 
/*     */     
/*  48 */     this.recipeHandlers.put("smithing", this::handleSmithing);
/*     */ 
/*     */     
/*  51 */     this.recipeHandlers.put("smithing_transform", this::handleSmithingTransform);
/*  52 */     this.recipeHandlers.put("smithing_trim", this::handleSmithingTrim);
/*  53 */     this.recipeHandlers.put("crafting_decorated_pot", this::handleSimpleRecipe);
/*     */   }
/*     */   
/*     */   public void handleRecipeType(PacketWrapper wrapper, String type) throws Exception {
/*  57 */     RecipeConsumer handler = this.recipeHandlers.get(type);
/*  58 */     if (handler != null) {
/*  59 */       handler.accept(wrapper);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(C packetType) {
/*  69 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < size; i++) {
/*     */             String type = (String)wrapper.passthrough(Type.STRING);
/*     */             wrapper.passthrough(Type.STRING);
/*     */             handleRecipeType(wrapper, Key.stripMinecraftNamespace(type));
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
/*  80 */     int ingredientsNo = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue() * ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*  81 */     wrapper.passthrough(Type.STRING);
/*  82 */     for (int i = 0; i < ingredientsNo; i++) {
/*  83 */       handleIngredient(wrapper);
/*     */     }
/*  85 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */   }
/*     */   
/*     */   public void handleCraftingShapeless(PacketWrapper wrapper) throws Exception {
/*  89 */     wrapper.passthrough(Type.STRING);
/*  90 */     handleIngredients(wrapper);
/*  91 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */   }
/*     */   
/*     */   public void handleSmelting(PacketWrapper wrapper) throws Exception {
/*  95 */     wrapper.passthrough(Type.STRING);
/*  96 */     handleIngredient(wrapper);
/*  97 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*  98 */     wrapper.passthrough((Type)Type.FLOAT);
/*  99 */     wrapper.passthrough((Type)Type.VAR_INT);
/*     */   }
/*     */   
/*     */   public void handleStonecutting(PacketWrapper wrapper) throws Exception {
/* 103 */     wrapper.passthrough(Type.STRING);
/* 104 */     handleIngredient(wrapper);
/* 105 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */   }
/*     */   
/*     */   public void handleSmithing(PacketWrapper wrapper) throws Exception {
/* 109 */     handleIngredient(wrapper);
/* 110 */     handleIngredient(wrapper);
/* 111 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */   }
/*     */   
/*     */   public void handleSimpleRecipe(PacketWrapper wrapper) throws Exception {
/* 115 */     wrapper.passthrough((Type)Type.VAR_INT);
/*     */   }
/*     */   
/*     */   public void handleSmithingTransform(PacketWrapper wrapper) throws Exception {
/* 119 */     handleIngredient(wrapper);
/* 120 */     handleIngredient(wrapper);
/* 121 */     handleIngredient(wrapper);
/* 122 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */   }
/*     */   
/*     */   public void handleSmithingTrim(PacketWrapper wrapper) throws Exception {
/* 126 */     handleIngredient(wrapper);
/* 127 */     handleIngredient(wrapper);
/* 128 */     handleIngredient(wrapper);
/*     */   }
/*     */   
/*     */   protected void rewrite(Item item) {
/* 132 */     if (this.protocol.getItemRewriter() != null) {
/* 133 */       this.protocol.getItemRewriter().handleItemToClient(item);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void handleIngredient(PacketWrapper wrapper) throws Exception {
/* 138 */     Item[] items = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/* 139 */     for (Item item : items) {
/* 140 */       rewrite(item);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void handleIngredients(PacketWrapper wrapper) throws Exception {
/* 145 */     int ingredients = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/* 146 */     for (int i = 0; i < ingredients; i++)
/* 147 */       handleIngredient(wrapper); 
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RecipeConsumer {
/*     */     void accept(PacketWrapper param1PacketWrapper) throws Exception;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\RecipeRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */