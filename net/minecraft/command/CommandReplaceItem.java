/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandReplaceItem
/*     */   extends CommandBase {
/*  22 */   private static final Map<String, Integer> SHORTCUTS = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  29 */     return "replaceitem";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  37 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  47 */     return "commands.replaceitem.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     boolean flag;
/*     */     int i;
/*     */     Item item;
/*  58 */     if (args.length < 1)
/*     */     {
/*  60 */       throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     if (args[0].equals("entity")) {
/*     */       
/*  68 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/*  72 */       if (!args[0].equals("block"))
/*     */       {
/*  74 */         throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
/*     */       }
/*     */       
/*  77 */       flag = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  82 */     if (flag) {
/*     */       
/*  84 */       if (args.length < 6)
/*     */       {
/*  86 */         throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
/*     */       }
/*     */       
/*  89 */       i = 4;
/*     */     }
/*     */     else {
/*     */       
/*  93 */       if (args.length < 4)
/*     */       {
/*  95 */         throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
/*     */       }
/*     */       
/*  98 */       i = 2;
/*     */     } 
/*     */     
/* 101 */     int j = getSlotForShortcut(args[i++]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 106 */       item = getItemByText(sender, args[i]);
/*     */     }
/* 108 */     catch (NumberInvalidException numberinvalidexception) {
/*     */       
/* 110 */       if (Block.getBlockFromName(args[i]) != Blocks.air)
/*     */       {
/* 112 */         throw numberinvalidexception;
/*     */       }
/*     */       
/* 115 */       item = null;
/*     */     } 
/*     */     
/* 118 */     i++;
/* 119 */     int k = (args.length > i) ? parseInt(args[i++], 1, 64) : 1;
/* 120 */     int l = (args.length > i) ? parseInt(args[i++]) : 0;
/* 121 */     ItemStack itemstack = new ItemStack(item, k, l);
/*     */     
/* 123 */     if (args.length > i) {
/*     */       
/* 125 */       String s = getChatComponentFromNthArg(sender, args, i).getUnformattedText();
/*     */ 
/*     */       
/*     */       try {
/* 129 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
/*     */       }
/* 131 */       catch (NBTException nbtexception) {
/*     */         
/* 133 */         throw new CommandException("commands.replaceitem.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     if (itemstack.getItem() == null)
/*     */     {
/* 139 */       itemstack = null;
/*     */     }
/*     */     
/* 142 */     if (flag) {
/*     */       
/* 144 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/* 145 */       BlockPos blockpos = parseBlockPos(sender, args, 1, false);
/* 146 */       World world = sender.getEntityWorld();
/* 147 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 149 */       if (tileentity == null || !(tileentity instanceof IInventory))
/*     */       {
/* 151 */         throw new CommandException("commands.replaceitem.noContainer", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 154 */       IInventory iinventory = (IInventory)tileentity;
/*     */       
/* 156 */       if (j >= 0 && j < iinventory.getSizeInventory())
/*     */       {
/* 158 */         iinventory.setInventorySlotContents(j, itemstack);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 163 */       Entity entity = func_175768_b(sender, args[1]);
/* 164 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/*     */       
/* 166 */       if (entity instanceof EntityPlayer)
/*     */       {
/* 168 */         ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */       
/* 171 */       if (!entity.replaceItemInInventory(j, itemstack))
/*     */       {
/* 173 */         throw new CommandException("commands.replaceitem.failed", new Object[] { Integer.valueOf(j), Integer.valueOf(k), (itemstack == null) ? "Air" : itemstack.getChatComponent() });
/*     */       }
/*     */       
/* 176 */       if (entity instanceof EntityPlayer)
/*     */       {
/* 178 */         ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */     } 
/*     */     
/* 182 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
/* 183 */     notifyOperators(sender, this, "commands.replaceitem.success", new Object[] { Integer.valueOf(j), Integer.valueOf(k), (itemstack == null) ? "Air" : itemstack.getChatComponent() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getSlotForShortcut(String shortcut) throws CommandException {
/* 189 */     if (!SHORTCUTS.containsKey(shortcut))
/*     */     {
/* 191 */       throw new CommandException("commands.generic.parameter.invalid", new Object[] { shortcut });
/*     */     }
/*     */ 
/*     */     
/* 195 */     return ((Integer)SHORTCUTS.get(shortcut)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 201 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "entity", "block" }) : ((args.length == 2 && args[0].equals("entity")) ? getListOfStringsMatchingLastWord(args, getUsernames()) : ((args.length >= 2 && args.length <= 4 && args[0].equals("block")) ? func_175771_a(args, 1, pos) : (((args.length != 3 || !args[0].equals("entity")) && (args.length != 5 || !args[0].equals("block"))) ? (((args.length != 4 || !args[0].equals("entity")) && (args.length != 6 || !args[0].equals("block"))) ? null : getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys())) : getListOfStringsMatchingLastWord(args, SHORTCUTS.keySet()))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getUsernames() {
/* 206 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 217 */     return (args.length > 0 && args[0].equals("entity") && index == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 222 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 224 */       SHORTCUTS.put("slot.container." + i, Integer.valueOf(i));
/*     */     }
/*     */     
/* 227 */     for (int j = 0; j < 9; j++)
/*     */     {
/* 229 */       SHORTCUTS.put("slot.hotbar." + j, Integer.valueOf(j));
/*     */     }
/*     */     
/* 232 */     for (int k = 0; k < 27; k++)
/*     */     {
/* 234 */       SHORTCUTS.put("slot.inventory." + k, Integer.valueOf(9 + k));
/*     */     }
/*     */     
/* 237 */     for (int l = 0; l < 27; l++)
/*     */     {
/* 239 */       SHORTCUTS.put("slot.enderchest." + l, Integer.valueOf(200 + l));
/*     */     }
/*     */     
/* 242 */     for (int i1 = 0; i1 < 8; i1++)
/*     */     {
/* 244 */       SHORTCUTS.put("slot.villager." + i1, Integer.valueOf(300 + i1));
/*     */     }
/*     */     
/* 247 */     for (int j1 = 0; j1 < 15; j1++)
/*     */     {
/* 249 */       SHORTCUTS.put("slot.horse." + j1, Integer.valueOf(500 + j1));
/*     */     }
/*     */     
/* 252 */     SHORTCUTS.put("slot.weapon", Integer.valueOf(99));
/* 253 */     SHORTCUTS.put("slot.armor.head", Integer.valueOf(103));
/* 254 */     SHORTCUTS.put("slot.armor.chest", Integer.valueOf(102));
/* 255 */     SHORTCUTS.put("slot.armor.legs", Integer.valueOf(101));
/* 256 */     SHORTCUTS.put("slot.armor.feet", Integer.valueOf(100));
/* 257 */     SHORTCUTS.put("slot.horse.saddle", Integer.valueOf(400));
/* 258 */     SHORTCUTS.put("slot.horse.armor", Integer.valueOf(401));
/* 259 */     SHORTCUTS.put("slot.horse.chest", Integer.valueOf(499));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandReplaceItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */