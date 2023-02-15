package com.example;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.data.*;

import java.util.Objects;

public class start {
    private Bot bot;
    private final Long GROUP_NUMBER;

    public start(Bot bot, Long group_number) {
        this.bot = bot;
        GROUP_NUMBER = group_number;
    }

    public void groupAtAll() {
//群聊里发送
        MessageChain chain = new MessageChainBuilder()
                //.append(new PlainText("该起床啦开启今天的学习,"))
                .append("每天跟我一起学习英语吧！") // 会被构造成 PlainText 再添加, 相当于上一行
                //.append("现在是中午复习时间")
                .append("现在是晚间复习时间")
                .build();
        bot.getGroup(GROUP_NUMBER).sendMessage(AtAll.INSTANCE);
        bot.getGroup(GROUP_NUMBER).sendMessage(chain);
        bot.getGroup(GROUP_NUMBER).sendMessage("10分钟后还有每日测试哦，记得要来考试哦(≧∇≦)/");


    }
    // 私聊个人
    public Long[][] friendInGroup() {
        ContactList<NormalMember> List = bot.getGroup(GROUP_NUMBER).getMembers();
        Object[] member = List.toArray();
        Long val;
        Long grpMe[][] = new Long[member.length +2][2];
        for (int i = 0; i < member.length; i++) {
            String temp = String.valueOf(member[i]);
            String mVal = temp.substring(13, temp.length() - 1);
            try {
                grpMe[i][0] = Long.parseLong(mVal);
                grpMe[i][1] = 0L;
                val = Long.valueOf(mVal);
                if (grpMe[i][0] != 1810300694) {
                    bot.getGroup(GROUP_NUMBER).get(val).nudge().sendTo(bot.getFriend(val));
                    Objects.requireNonNull(bot.getGroup(GROUP_NUMBER)).get(val).sendMessage("快去群里学习每日英语(≧∇≦)/");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return grpMe;
    }

}
