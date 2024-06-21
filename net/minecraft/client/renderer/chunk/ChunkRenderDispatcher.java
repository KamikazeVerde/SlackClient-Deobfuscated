/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListenableFutureTask;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.VertexBufferUploader;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.WorldVertexBufferUploader;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ChunkRenderDispatcher
/*     */ {
/*  31 */   private static final Logger logger = LogManager.getLogger();
/*  32 */   private static final ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("Chunk Batcher %d").setDaemon(true).build();
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
/*     */   public ChunkRenderDispatcher() {
/*  45 */     this(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  50 */   private final List<ChunkRenderWorker> listThreadedWorkers = Lists.newArrayList();
/*  51 */   private final BlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates = Queues.newArrayBlockingQueue(100); private final BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders;
/*  52 */   private final WorldVertexBufferUploader worldVertexUploader = new WorldVertexBufferUploader();
/*  53 */   private final VertexBufferUploader vertexUploader = new VertexBufferUploader(); private final ChunkRenderWorker renderWorker;
/*  54 */   private final Queue<ListenableFutureTask<?>> queueChunkUploads = Queues.newArrayDeque(); private final int countRenderBuilders;
/*  55 */   private List<RegionRenderCacheBuilder> listPausedBuilders = new ArrayList<>(); public ChunkRenderDispatcher(int p_i4_1_) {
/*  56 */     int i = Math.max(1, (int)(Runtime.getRuntime().maxMemory() * 0.3D) / 10485760);
/*  57 */     int j = Math.max(1, MathHelper.clamp_int(Runtime.getRuntime().availableProcessors() - 2, 1, i / 5));
/*     */     
/*  59 */     if (p_i4_1_ < 0) {
/*     */       
/*  61 */       this.countRenderBuilders = MathHelper.clamp_int(j * 8, 1, i);
/*     */     }
/*     */     else {
/*     */       
/*  65 */       this.countRenderBuilders = p_i4_1_;
/*     */     } 
/*     */     
/*  68 */     for (int k = 0; k < j; k++) {
/*     */       
/*  70 */       ChunkRenderWorker chunkrenderworker = new ChunkRenderWorker(this);
/*  71 */       Thread thread = threadFactory.newThread(chunkrenderworker);
/*  72 */       thread.start();
/*  73 */       this.listThreadedWorkers.add(chunkrenderworker);
/*     */     } 
/*     */     
/*  76 */     this.queueFreeRenderBuilders = Queues.newArrayBlockingQueue(this.countRenderBuilders);
/*     */     
/*  78 */     for (int l = 0; l < this.countRenderBuilders; l++)
/*     */     {
/*  80 */       this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
/*     */     }
/*     */     
/*  83 */     this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDebugInfo() {
/*  88 */     return String.format("pC: %03d, pU: %1d, aB: %1d", new Object[] { Integer.valueOf(this.queueChunkUpdates.size()), Integer.valueOf(this.queueChunkUploads.size()), Integer.valueOf(this.queueFreeRenderBuilders.size()) });
/*     */   }
/*     */   
/*     */   public boolean runChunkUploads(long p_178516_1_) {
/*     */     long i;
/*  93 */     boolean flag = false;
/*     */ 
/*     */     
/*     */     do {
/*  97 */       boolean flag1 = false;
/*  98 */       ListenableFutureTask listenablefuturetask = null;
/*     */       
/* 100 */       synchronized (this.queueChunkUploads) {
/*     */         
/* 102 */         listenablefuturetask = this.queueChunkUploads.poll();
/*     */       } 
/*     */       
/* 105 */       if (listenablefuturetask != null) {
/*     */         
/* 107 */         listenablefuturetask.run();
/* 108 */         flag1 = true;
/* 109 */         flag = true;
/*     */       } 
/*     */       
/* 112 */       if (p_178516_1_ == 0L || !flag1) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 117 */       i = p_178516_1_ - System.nanoTime();
/*     */     }
/* 119 */     while (i >= 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean updateChunkLater(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 130 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 135 */       final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/* 136 */       chunkcompiletaskgenerator.addFinishRunnable(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 140 */               ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */             }
/*     */           });
/* 143 */       boolean flag1 = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/*     */       
/* 145 */       if (!flag1)
/*     */       {
/* 147 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */       
/* 150 */       flag = flag1;
/*     */     }
/*     */     finally {
/*     */       
/* 154 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 157 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean updateChunkNow(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 162 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 167 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/*     */ 
/*     */       
/*     */       try {
/* 171 */         this.renderWorker.processTask(chunkcompiletaskgenerator);
/*     */       }
/* 173 */       catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 178 */       flag = true;
/*     */     }
/*     */     finally {
/*     */       
/* 182 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 185 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopChunkUpdates() {
/* 190 */     clearChunkUpdates();
/*     */     
/* 192 */     while (runChunkUploads(0L));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     List<RegionRenderCacheBuilder> list = Lists.newArrayList();
/*     */     
/* 199 */     while (list.size() != this.countRenderBuilders) {
/*     */ 
/*     */       
/*     */       try {
/* 203 */         list.add(allocateRenderBuilder());
/*     */       }
/* 205 */       catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     this.queueFreeRenderBuilders.addAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeRenderBuilder(RegionRenderCacheBuilder p_178512_1_) {
/* 216 */     this.queueFreeRenderBuilders.add(p_178512_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
/* 221 */     return this.queueFreeRenderBuilders.take();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
/* 226 */     return this.queueChunkUpdates.take();
/*     */   }
/*     */   
/*     */   public boolean updateTransparencyLater(RenderChunk chunkRenderer) {
/*     */     boolean flag1;
/* 231 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 236 */       final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskTransparency();
/*     */       
/* 238 */       if (chunkcompiletaskgenerator != null) {
/*     */         
/* 240 */         chunkcompiletaskgenerator.addFinishRunnable(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 244 */                 ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */               }
/*     */             });
/* 247 */         boolean flag2 = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/* 248 */         return flag2;
/*     */       } 
/*     */       
/* 251 */       boolean flag = true;
/* 252 */       flag1 = flag;
/*     */     }
/*     */     finally {
/*     */       
/* 256 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 259 */     return flag1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> uploadChunk(final EnumWorldBlockLayer player, final WorldRenderer p_178503_2_, final RenderChunk chunkRenderer, final CompiledChunk compiledChunkIn) {
/* 264 */     if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
/*     */       
/* 266 */       if (OpenGlHelper.useVbo()) {
/*     */         
/* 268 */         uploadVertexBuffer(p_178503_2_, chunkRenderer.getVertexBufferByLayer(player.ordinal()));
/*     */       }
/*     */       else {
/*     */         
/* 272 */         uploadDisplayList(p_178503_2_, ((ListedRenderChunk)chunkRenderer).getDisplayList(player, compiledChunkIn), chunkRenderer);
/*     */       } 
/*     */       
/* 275 */       p_178503_2_.setTranslation(0.0D, 0.0D, 0.0D);
/* 276 */       return Futures.immediateFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 280 */     ListenableFutureTask<Object> listenablefuturetask = ListenableFutureTask.create(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 284 */             ChunkRenderDispatcher.this.uploadChunk(player, p_178503_2_, chunkRenderer, compiledChunkIn);
/*     */           }
/*     */         }null);
/*     */     
/* 288 */     synchronized (this.queueChunkUploads) {
/*     */       
/* 290 */       this.queueChunkUploads.add(listenablefuturetask);
/* 291 */       return (ListenableFuture<Object>)listenablefuturetask;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uploadDisplayList(WorldRenderer p_178510_1_, int p_178510_2_, RenderChunk chunkRenderer) {
/* 298 */     GL11.glNewList(p_178510_2_, 4864);
/* 299 */     GlStateManager.pushMatrix();
/* 300 */     chunkRenderer.multModelviewMatrix();
/* 301 */     this.worldVertexUploader.func_181679_a(p_178510_1_);
/* 302 */     GlStateManager.popMatrix();
/* 303 */     GL11.glEndList();
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadVertexBuffer(WorldRenderer p_178506_1_, VertexBuffer vertexBufferIn) {
/* 308 */     this.vertexUploader.setVertexBuffer(vertexBufferIn);
/* 309 */     this.vertexUploader.func_181679_a(p_178506_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearChunkUpdates() {
/* 314 */     while (!this.queueChunkUpdates.isEmpty()) {
/*     */       
/* 316 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.queueChunkUpdates.poll();
/*     */       
/* 318 */       if (chunkcompiletaskgenerator != null)
/*     */       {
/* 320 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChunkUpdates() {
/* 327 */     return (this.queueChunkUpdates.isEmpty() && this.queueChunkUploads.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void pauseChunkUpdates() {
/* 332 */     while (this.listPausedBuilders.size() != this.countRenderBuilders) {
/*     */ 
/*     */       
/*     */       try {
/* 336 */         runChunkUploads(Long.MAX_VALUE);
/* 337 */         RegionRenderCacheBuilder regionrendercachebuilder = this.queueFreeRenderBuilders.poll(100L, TimeUnit.MILLISECONDS);
/*     */         
/* 339 */         if (regionrendercachebuilder != null)
/*     */         {
/* 341 */           this.listPausedBuilders.add(regionrendercachebuilder);
/*     */         }
/*     */       }
/* 344 */       catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeChunkUpdates() {
/* 353 */     this.queueFreeRenderBuilders.addAll(this.listPausedBuilders);
/* 354 */     this.listPausedBuilders.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\chunk\ChunkRenderDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */