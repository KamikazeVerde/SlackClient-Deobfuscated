/*    */ package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
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
/*    */ public class RecipeRewriter1_19_3<C extends ClientboundPacketType>
/*    */   extends RecipeRewriter<C>
/*    */ {
/*    */   public RecipeRewriter1_19_3(Protocol<C, ?, ?, ?> protocol) {
/* 29 */     super(protocol);
/*    */     
/* 31 */     this.recipeHandlers.put("crafting_special_armordye", this::handleSimpleRecipe);
/* 32 */     this.recipeHandlers.put("crafting_special_bookcloning", this::handleSimpleRecipe);
/* 33 */     this.recipeHandlers.put("crafting_special_mapcloning", this::handleSimpleRecipe);
/* 34 */     this.recipeHandlers.put("crafting_special_mapextending", this::handleSimpleRecipe);
/* 35 */     this.recipeHandlers.put("crafting_special_firework_rocket", this::handleSimpleRecipe);
/* 36 */     this.recipeHandlers.put("crafting_special_firework_star", this::handleSimpleRecipe);
/* 37 */     this.recipeHandlers.put("crafting_special_firework_star_fade", this::handleSimpleRecipe);
/* 38 */     this.recipeHandlers.put("crafting_special_tippedarrow", this::handleSimpleRecipe);
/* 39 */     this.recipeHandlers.put("crafting_special_bannerduplicate", this::handleSimpleRecipe);
/* 40 */     this.recipeHandlers.put("crafting_special_shielddecoration", this::handleSimpleRecipe);
/* 41 */     this.recipeHandlers.put("crafting_special_shulkerboxcoloring", this::handleSimpleRecipe);
/* 42 */     this.recipeHandlers.put("crafting_special_suspiciousstew", this::handleSimpleRecipe);
/* 43 */     this.recipeHandlers.put("crafting_special_repairitem", this::handleSimpleRecipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleCraftingShapeless(PacketWrapper wrapper) throws Exception {
/* 48 */     wrapper.passthrough(Type.STRING);
/* 49 */     wrapper.passthrough((Type)Type.VAR_INT);
/* 50 */     handleIngredients(wrapper);
/* 51 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
/* 56 */     int ingredients = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue() * ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/* 57 */     wrapper.passthrough(Type.STRING);
/* 58 */     wrapper.passthrough((Type)Type.VAR_INT);
/* 59 */     for (int i = 0; i < ingredients; i++) {
/* 60 */       handleIngredient(wrapper);
/*    */     }
/* 62 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleSmelting(PacketWrapper wrapper) throws Exception {
/* 67 */     wrapper.passthrough(Type.STRING);
/* 68 */     wrapper.passthrough((Type)Type.VAR_INT);
/* 69 */     handleIngredient(wrapper);
/* 70 */     rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/* 71 */     wrapper.passthrough((Type)Type.FLOAT);
/* 72 */     wrapper.passthrough((Type)Type.VAR_INT);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_3to1_19_1\rewriter\RecipeRewriter1_19_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */