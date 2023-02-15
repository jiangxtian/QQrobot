package com.example;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class publish {
    private File file;
    private Bot bot1;
    private final Long GROUP_NUMBER;
    private final int vocabulary;
    private final int iniPosition;
    private final Long adminId;
    private final Long BotId;

    public publish(File file, Bot bot1, Long group_number, int vocabulary, int iniPosition, Long adminId, Long botId) {
        this.file = file;
        this.bot1 = bot1;
        GROUP_NUMBER = group_number;
        this.vocabulary = vocabulary;
        this.iniPosition = iniPosition;
        this.adminId = adminId;
        BotId = botId;
    }

    public String[][] txt2String() {
        String answer[][] = new String[vocabulary][2];
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        InputStreamReader isr = null;
        int count = 0;
        int inCount = 0;
        try {
            isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader br = new BufferedReader(isr);//构造一个BufferedReader类来读取文件
            String s = null;
            String re = "";//英语
            String endre = "";//汉译

            bot1.getFriend(adminId).sendMessage("今天单词起始位置"+ iniPosition);

            int inx = 0;
            List<String> chain = new ArrayList<String>();

            MessageChainBuilder builder = new MessageChainBuilder();
//             builder.append(new PlainText("Hello "));
//            builder.append(new PlainText(" Mirai!"));
//            MessageChain chain = builder.build();

            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                //while (count<5) {//使用readLine方法，一次读一行
                //s = br.readLine();
                if (inCount == vocabulary) {
                    break;
                }
                if (count >= iniPosition && inCount < vocabulary) {
                    result.append(System.lineSeparator() + s);
                    if (s.indexOf("conj.") > 0) {
                        inx = s.indexOf("conj.");
                    }
                    else if (s.indexOf("pron.") > 0) {
                        inx = s.indexOf("pron.");
                    }
                    else if (s.indexOf("vt.") > 0) {
                        inx = s.indexOf("vt.");
                    }
                    else if (s.indexOf("adj.") > 0) {
                        inx = s.indexOf("adj.");
                    }
                    else if (s.indexOf("adv.") > 0) {
                        inx = s.indexOf("adv.");
                    }else if (s.indexOf("n.") > 0) {
                        inx = s.indexOf("n.");
                    }
                    else if (s.indexOf("v.") > 0) {
                        inx = s.indexOf("v.");
                    }
                    else if (s.indexOf("art.") > 0) {
                        inx = s.indexOf("art.");
                    }
                    else if (s.indexOf("prep.") > 0) {
                        inx = s.indexOf("prep.");
                    }

                    re = s.substring(0, inx).trim();
                    endre = s.substring(inx, s.length()).trim();
                    answer[inCount][0] = re;
                    answer[inCount][1] = endre;
                    int sNum = inCount +1;  // 序号
                    builder.append(new PlainText("("+sNum+")"+" "+"英文是： " + re + "\n"+ "   " + "汉语是： " + endre) + "\n" + "\n");
                    inCount++;
                } else {
                    count++;
                }

            }
            MessageChain chains = builder.build();
            List a = new ArrayList<ForwardMessage.Node>();
            a.add(new ForwardMessage.Node(BotId, 0, "小茉莉", chains));
            bot1.getGroup(GROUP_NUMBER).sendMessage(new ForwardMessage(chain, "今日英语背诵内容，点击查看", "[聊天记录]", "点击查看查看", "", a));
            bot1.getFriend(adminId).sendMessage(new ForwardMessage(chain, "今日英语背诵内容，点击查看", "[聊天记录]", "点击查看查看", "", a));
            //bot1.getGroup(GROUP_NUMBER).sendMessage(result.toString());

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }


}
