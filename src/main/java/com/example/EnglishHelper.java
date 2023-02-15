package com.example;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;

public final class EnglishHelper extends JavaPlugin {
    public static final EnglishHelper INSTANCE = new EnglishHelper();
    private final Long GROUP_NUMBER = L;  // 群号
    private String path = "D:/CET4.txt";   // 单词文件目录
    private int times = 60;   // 距离考试时间 秒
    private int vocabulary = 100; // 每次单词发布个数，上线
    private final int iniPosition = 0;//单词起始位置，每次＋上线，即下线
    private final Long adminId = ; // 管理员ID
    private final Long BotId = ;  // 机器人ID
    private final String password = "";  // 机器人密码
    private final int begPosition = 0;// 单词开始位置，可选值 0 、30 、60
    private final int learned = iniPosition;  // 已经学习的单词数量2023/1/5
    private final int remain = 7508 - learned; // 剩余单词数量

    private EnglishHelper() {
        super(new JvmPluginDescriptionBuilder("com.example.englishhelper", "1.0.0")
                .name("EnglishHelper")
                .author("jxt")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("EnglishHelper Plugin loaded!");
        Bot bot1 = BotFactory.INSTANCE.newBot(BotId, password,new BotConfiguration(){{
            setProtocol(MiraiProtocol.ANDROID_PAD);
        }});
        bot1.login();
        getLogger().info("EnglishHelper Plugin loaded!");
        start start = new start(bot1, GROUP_NUMBER);
        Long[][] grpMe;
        start.groupAtAll();
        grpMe = start.friendInGroup();
        getLogger().info("everyday english start success!!!!! ");

        end end = new end(bot1, GROUP_NUMBER);
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        File file = new File(path);//我的txt文本存放目录，根据自己的路径修改即可
        publish publish = new publish(file, bot1, GROUP_NUMBER, vocabulary, iniPosition, adminId, BotId);
        String answer[][];

        answer = publish.txt2String();
        notice notice = new notice(answer, bot1, grpMe, GROUP_NUMBER, adminId, learned, remain, begPosition);
        try {
            Thread.sleep(times);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        notice.send();
        GlobalEventChannel.INSTANCE.registerListenerHost(new notice(answer, bot1, grpMe, GROUP_NUMBER, adminId, learned, remain, begPosition));
        getLogger().info("everyday english end success!!!!! ");
        //getScheduler().delayed(30000L, () -> end.groupAtAll());
    }
}