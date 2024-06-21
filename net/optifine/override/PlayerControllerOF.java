/*    */ package net.optifine.override;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class PlayerControllerOF
/*    */   extends PlayerControllerMP {
/*    */   private boolean acting = false;
/* 20 */   private BlockPos lastClickBlockPos = null;
/* 21 */   private Entity lastClickEntity = null;
/*    */ 
/*    */   
/*    */   public PlayerControllerOF(Minecraft mcIn, NetHandlerPlayClient netHandler) {
/* 25 */     super(mcIn, netHandler);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/* 36 */     this.acting = true;
/* 37 */     this.lastClickBlockPos = loc;
/* 38 */     boolean flag = super.clickBlock(loc, face);
/* 39 */     this.acting = false;
/* 40 */     return flag;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/* 45 */     this.acting = true;
/* 46 */     this.lastClickBlockPos = posBlock;
/* 47 */     boolean flag = super.onPlayerDamageBlock(posBlock, directionFacing);
/* 48 */     this.acting = false;
/* 49 */     return flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean sendUseItem(EntityPlayer player, World worldIn, ItemStack stack) {
/* 57 */     this.acting = true;
/* 58 */     boolean flag = super.sendUseItem(player, worldIn, stack);
/* 59 */     this.acting = false;
/* 60 */     return flag;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onPlayerRightClick(EntityPlayerSP p_178890_1, WorldClient p_178890_2, ItemStack p_178890_3, BlockPos p_178890_4, EnumFacing p_178890_5, Vec3 p_178890_6) {
/* 65 */     this.acting = true;
/* 66 */     this.lastClickBlockPos = p_178890_4;
/* 67 */     boolean flag = super.onPlayerRightClick(p_178890_1, p_178890_2, p_178890_3, p_178890_4, p_178890_5, p_178890_6);
/* 68 */     this.acting = false;
/* 69 */     return flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean interactWithEntitySendPacket(EntityPlayer player, Entity target) {
/* 77 */     this.lastClickEntity = target;
/* 78 */     return super.interactWithEntitySendPacket(player, target);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_178894_a(EntityPlayer player, Entity target, MovingObjectPosition ray) {
/* 83 */     this.lastClickEntity = target;
/* 84 */     return super.func_178894_a(player, target, ray);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActing() {
/* 89 */     return this.acting;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getLastClickBlockPos() {
/* 94 */     return this.lastClickBlockPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getLastClickEntity() {
/* 99 */     return this.lastClickEntity;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\override\PlayerControllerOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */