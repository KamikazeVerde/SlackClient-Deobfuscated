package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;

public interface ItemRewriter<T extends com.viaversion.viaversion.api.protocol.Protocol> extends Rewriter<T> {
  Item handleItemToClient(Item paramItem);
  
  Item handleItemToServer(Item paramItem);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\rewriter\ItemRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */