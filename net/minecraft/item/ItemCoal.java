/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ 
/*    */ public class ItemCoal
/*    */   extends Item
/*    */ {
/*    */   public ItemCoal() {
/* 10 */     setHasSubtypes(true);
/* 11 */     setMaxDamage(0);
/* 12 */     setCreativeTab(CreativeTabs.tabMaterials);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 21 */     return (stack.getMetadata() == 1) ? "item.charcoal" : "item.coal";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 31 */     subItems.add(new ItemStack(itemIn, 1, 0));
/* 32 */     subItems.add(new ItemStack(itemIn, 1, 1));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemCoal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */