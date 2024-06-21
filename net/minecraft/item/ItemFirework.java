/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemFirework
/*    */   extends Item
/*    */ {
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 24 */     if (!worldIn.isRemote) {
/*    */       
/* 26 */       EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, (pos.getX() + hitX), (pos.getY() + hitY), (pos.getZ() + hitZ), stack);
/* 27 */       worldIn.spawnEntityInWorld((Entity)entityfireworkrocket);
/*    */       
/* 29 */       if (!playerIn.capabilities.isCreativeMode)
/*    */       {
/* 31 */         stack.stackSize--;
/*    */       }
/*    */       
/* 34 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 38 */     return false;
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
/* 50 */     if (stack.hasTagCompound()) {
/*    */       
/* 52 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Fireworks");
/*    */       
/* 54 */       if (nbttagcompound != null) {
/*    */         
/* 56 */         if (nbttagcompound.hasKey("Flight", 99))
/*    */         {
/* 58 */           tooltip.add(StatCollector.translateToLocal("item.fireworks.flight") + " " + nbttagcompound.getByte("Flight"));
/*    */         }
/*    */         
/* 61 */         NBTTagList nbttaglist = nbttagcompound.getTagList("Explosions", 10);
/*    */         
/* 63 */         if (nbttaglist != null && nbttaglist.tagCount() > 0)
/*    */         {
/* 65 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */             
/* 67 */             NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 68 */             List<String> list = Lists.newArrayList();
/* 69 */             ItemFireworkCharge.addExplosionInfo(nbttagcompound1, list);
/*    */             
/* 71 */             if (list.size() > 0) {
/*    */               
/* 73 */               for (int j = 1; j < list.size(); j++)
/*    */               {
/* 75 */                 list.set(j, "  " + (String)list.get(j));
/*    */               }
/*    */               
/* 78 */               tooltip.addAll(list);
/*    */             } 
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemFirework.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */