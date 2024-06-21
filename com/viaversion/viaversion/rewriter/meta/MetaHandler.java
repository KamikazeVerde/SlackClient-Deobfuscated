package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;

@FunctionalInterface
public interface MetaHandler {
  void handle(MetaHandlerEvent paramMetaHandlerEvent, Metadata paramMetadata);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\meta\MetaHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */