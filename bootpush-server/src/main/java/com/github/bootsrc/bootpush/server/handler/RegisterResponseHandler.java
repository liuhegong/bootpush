package com.github.bootsrc.bootpush.server.handler;

import com.github.bootsrc.bootpush.api.constant.ChannelAttrKey;
import com.github.bootsrc.bootpush.api.enums.MessageType;
import com.github.bootsrc.bootpush.api.enums.RegisterState;
import com.github.bootsrc.bootpush.api.model.StandardBody;
import com.github.bootsrc.bootpush.api.model.StandardHeader;
import com.github.bootsrc.bootpush.api.model.StandardMessage;
import com.github.bootsrc.bootpush.api.util.IdUtil;
import com.github.bootsrc.bootpush.server.channel.ChannelMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterResponseHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterResponseHandler.class);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelMap.remove(ctx.channel());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof StandardMessage) {
            StandardMessage message = (StandardMessage) msg;
            StandardHeader header = message.getHeader();
            if (header != null
                    && header.getType() == MessageType.REGISTER_REQ.value()) {
                // 生成新的registrationId
                String registrationId = IdUtil.newUUID();
                LOGGER.info("generate new registrationId={}", registrationId);
                header.setRegistrationId(registrationId);
                StandardMessage targetMessage = buildRegisterResponse(header, ctx);
                ctx.writeAndFlush(targetMessage);
            } else {
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    private StandardMessage buildRegisterResponse(StandardHeader requestHeader, ChannelHandlerContext ctx) {
        StandardMessage message = new StandardMessage();
        StandardHeader header = new StandardHeader();
        header.setType(MessageType.REGISTER_RESP.value());
//		boolean passed = PassportUtil.checkByClientToken(header.getAppId(), header.getClientToken());
        boolean passed = true;
        if (passed) {
            String registrationId = header.getRegistrationId();
            ctx.channel().attr(ChannelAttrKey.REGISTRATION_ID).set(registrationId);
            ChannelMap.put(registrationId, ctx.channel());
            header.setResultCode(RegisterState.SUCCESS.getCode());
            header.setResultText(RegisterState.SUCCESS.getText());
            LOGGER.info("---Server:deviceRegisterSuccess.registrationId={}", registrationId);
        } else {
            header.setResultCode("001");
            header.setResultText("客户端设备注册失败");
            LOGGER.info("---Server:deviceRegisterFailed.");
        }

        message.setHeader(header);

        StandardBody body = new StandardBody();
        body.setContentType("text");
        body.setContent("This is a Description");
        body.setExtras(null);
        message.setBody(body);
        return message;
    }
}
