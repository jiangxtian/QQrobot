package com.example;

import kotlin.jvm.JvmOverloads;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

public class end {
    private Bot bot;
    private final Long GROUP_NUMBER;
    public end(Bot bot, Long group_number) {
        this.bot = bot;
        GROUP_NUMBER = group_number;
    }


    public void groupAtAll() {
//群聊里发送
        MessageChain chain = new MessageChainBuilder()
                .append(new PlainText("今天的学习结束啦,"))
                .append("大家今天都很棒")
                .build();
        bot.getGroup(GROUP_NUMBER).sendMessage(chain);
        bot.getGroup(GROUP_NUMBER).sendMessage("每天都要学习哦，记得明天也要来哦(≧∇≦)/");


    }


}
