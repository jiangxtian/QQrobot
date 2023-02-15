package com.example;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;

import java.util.Random;

public class notice extends SimpleListenerHost {
    private String answer[][];
    private String question;
    private final Bot bot1;
    private int j = 0;
    private int count = 0;
    private final Long[][] grpMe;
    private final Long GROUP_NUMBER;
    private final Long adminId;
    private final int learned;
    private final int remain;
    private final int begPosition;

    public notice(String[][] answer, Bot bot1, Long[][] grpMe, Long group_number, Long adminId, int learned, int remain, int begPosition) {
        this.answer = answer;
        this.bot1 = bot1;
        GROUP_NUMBER = group_number;
        this.adminId = adminId;
        this.learned = learned;
        this.remain = remain;
        this.begPosition = begPosition;
        this.question = question;
        this.grpMe = grpMe;

    }

    @EventHandler
    private ListeningStatus test(GroupMessageEvent event) {
        end end = new end(bot1,GROUP_NUMBER);
        Random r = new Random();
        String msg = event.getMessage().contentToString().trim();
        //退出
        if (msg.equals("/exit") && event.getSender().getId() == adminId) {
            event.getGroup().sendMessage("退出考试成功!!!");
            end.groupAtAll();
            return ListeningStatus.STOPPED;
        }
        //回答正确
        if (answer[j][0].indexOf(msg) >= 0) {
            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("回答正确！"));
            event.getGroup().sendMessage("正确答案是：" + "\n" + answer[j][1] + "\n" + answer[j][0]);
            for (int i = 0; i < 10; i++) {
                try {
                    if (grpMe[i][0] == event.getSender().getId()) {
                        grpMe[i][1] = grpMe[i][1] + 1;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            //第一个人回答出问题
            if (count == 0) {
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你是第一个回答出来的哦，太棒啦！！"));
                event.getGroup().sendMessage(new Face(293));
            }
            //随机题设置
            if (j < begPosition + 16) {
                j = r.nextInt(16) + begPosition + 16;
            } else if (j >= begPosition + 16) {
                j = r.nextInt(16) + begPosition;
            }
            count++;
            //出题，并判断结束最多的得分
            if (count >= 20) {
                Long max = grpMe[0][1];
                int inx = 0;
                int val;
                for (int i = 1; i < 10; i++) {
                    try {
                        if (grpMe[i][1] > max) {
                            max = grpMe[i][1];
                            inx = i;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }


                }
                try {
                    event.getGroup().sendMessage(new At(grpMe[inx][0]).plus("是今天答对最多的人！！\n得分： " + grpMe[inx][1]) + " 分");
                    event.getGroup().sendMessage(new Face(Face.HE_CAI));
                    MessageChain chain = new MessageChainBuilder()
                            .append("群内得分情况如下：\n")
                            .append(bot1.getGroup(GROUP_NUMBER).get(grpMe[0][0]).getNick() + " 的得分是: " + grpMe[0][1] + " 分" + "\n")
                            .append(bot1.getGroup(GROUP_NUMBER).get(grpMe[1][0]).getNick() + " 的得分是: " + grpMe[1][1] + " 分" + "\n")
                            .append(bot1.getGroup(GROUP_NUMBER).get(grpMe[2][0]).getNick() + " 的得分是: " + grpMe[2][1] + " 分" + "\n")
                            .append(bot1.getGroup(GROUP_NUMBER).get(grpMe[3][0]).getNick() + " 的得分是: " + grpMe[3][1] + " 分" + "\n")
                            .append(bot1.getGroup(GROUP_NUMBER).get(grpMe[4][0]).getNick() + " 的得分是: " + grpMe[4][1] + " 分" + "\n")
                            .append(bot1.getGroup(GROUP_NUMBER).get(grpMe[5][0]).getNick() + " 的得分是: " + grpMe[5][1] + " 分" + "\n")
                            .append(bot1.getGroup(GROUP_NUMBER).get(grpMe[6][0]).getNick() + " 的得分是: " + grpMe[6][1] + " 分")
                            .build();
                    bot1.getGroup(GROUP_NUMBER).sendMessage(chain);
                    bot1.getGroup(GROUP_NUMBER).sendMessage("目前已经学习 " + learned + " 个单词，" + "剩余 " + remain + " 个单词未学习");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                end.groupAtAll();
                return ListeningStatus.STOPPED;
            } else {
                //bot1.getGroup(groupenumber).sendMessage("请写出 " + answer[j][1] + " 对应的英文单词" + "\n" +
                //        "(PS:如果拼写无误，则是系统拼接错误，请在转发消息中查找并复制答案)");
                bot1.getGroup(GROUP_NUMBER).sendMessage("请写出 " + answer[j][1] + " 对应的英文单词" + "\n");

            }
            //回答错误
        } else {
            event.getGroup().sendMessage("回答错误");
            bot1.getGroup(GROUP_NUMBER).sendMessage("请写出 " + answer[j][1] + " 对应的英文单词" + "\n" +
                    "(PS:如果拼写无误，则是系统拼接错误，请在转发消息中查找并复制答案)");
            if (r.nextInt(10) < 1) {
                event.getSender().nudge().sendTo(event.getGroup());
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你是真拉，要继续努力哦"));
                event.getGroup().sendMessage(new Face(312));
            }
            try {
                MessageSource.recall(event.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return ListeningStatus.LISTENING;
    }

    public void send() {
        bot1.getGroup(GROUP_NUMBER).sendMessage("考试时间到");
        bot1.getGroup(GROUP_NUMBER).sendMessage("请给出下列汉语的英语表达");
        bot1.getGroup(GROUP_NUMBER).sendMessage("请写出 " + answer[j][1] + " 对应的英文单词" + "\n" +
                "(PS:如果拼写无误，则是系统拼接错误，请在转发消息中查找并复制答案)");
    }

}