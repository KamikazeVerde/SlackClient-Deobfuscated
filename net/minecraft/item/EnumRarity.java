/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.util.ChatFormatting;
/*    */ 
/*    */ public enum EnumRarity
/*    */ {
/*  7 */   COMMON(ChatFormatting.WHITE, "Common"),
/*  8 */   UNCOMMON(ChatFormatting.YELLOW, "Uncommon"),
/*  9 */   RARE(ChatFormatting.AQUA, "Rare"),
/* 10 */   EPIC(ChatFormatting.LIGHT_PURPLE, "Epic");
/*    */ 
/*    */ 
/*    */   
/*    */   public final ChatFormatting rarityColor;
/*    */ 
/*    */ 
/*    */   
/*    */   public final String rarityName;
/*    */ 
/*    */ 
/*    */   
/*    */   EnumRarity(ChatFormatting color, String name) {
/* 23 */     this.rarityColor = color;
/* 24 */     this.rarityName = name;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\EnumRarity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */