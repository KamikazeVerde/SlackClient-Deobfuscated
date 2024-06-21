/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.BlockJukebox;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemRecord extends Item {
/* 19 */   private static final Map<String, ItemRecord> RECORDS = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public final String recordName;
/*    */ 
/*    */   
/*    */   protected ItemRecord(String name) {
/* 26 */     this.recordName = name;
/* 27 */     this.maxStackSize = 1;
/* 28 */     setCreativeTab(CreativeTabs.tabMisc);
/* 29 */     RECORDS.put("records." + name, this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 40 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*    */     
/* 42 */     if (iblockstate.getBlock() == Blocks.jukebox && !((Boolean)iblockstate.getValue((IProperty)BlockJukebox.HAS_RECORD)).booleanValue()) {
/*    */       
/* 44 */       if (worldIn.isRemote)
/*    */       {
/* 46 */         return true;
/*    */       }
/*    */ 
/*    */       
/* 50 */       ((BlockJukebox)Blocks.jukebox).insertRecord(worldIn, pos, iblockstate, stack);
/* 51 */       worldIn.playAuxSFXAtEntity(null, 1005, pos, Item.getIdFromItem(this));
/* 52 */       stack.stackSize--;
/* 53 */       playerIn.triggerAchievement(StatList.field_181740_X);
/* 54 */       return true;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 71 */     tooltip.add(getRecordNameLocal());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecordNameLocal() {
/* 76 */     return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumRarity getRarity(ItemStack stack) {
/* 84 */     return EnumRarity.RARE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemRecord getRecord(String name) {
/* 92 */     return RECORDS.get(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */