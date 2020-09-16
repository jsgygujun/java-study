package com.jsgygujun.example.process;

import org.apache.commons.exec.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author GuJun
 * @date 2020/9/16
 */
public class ApacheCommonsExecExample {

    @Test
    public void basic_test() {
        String cmd = "ping -c 100 www.baidu.com";
        final CommandLine cmdLine = CommandLine.parse(cmd);
        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValue(0); // 明确脚本正常退出时的状态码
        try {
            exec.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void basic_test_2() {
        final CommandLine cmdLine = new CommandLine("ping");
        cmdLine.addArgument("-c").addArgument("100").addArgument("www.baidu.com");
        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValue(0); // 明确脚本正常退出时的状态码
        try {
            exec.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 超时管理
     */
    @Test
    public void time_out() {
        final CommandLine cmdLine = new CommandLine("ping");
        cmdLine.addArgument("-c").addArgument("100").addArgument("www.baidu.com");
        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValue(0); // 明确脚本正常退出时的状态码
        exec.setWatchdog(new ExecuteWatchdog(5*1000)); // 5秒超时
        try {
            exec.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得进程的输出信息
     */
    @Test
    public void get_out_err() {

        final CommandLine cmdLine = new CommandLine("ping");
        cmdLine.addArgument("-c").addArgument("100").addArgument("www.baidu.com");

        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValue(0); // 明确脚本正常退出时的状态码
        exec.setWatchdog(new ExecuteWatchdog(5*1000)); // 5秒超时
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        try {
            exec.setStreamHandler(new PumpStreamHandler(out, err));
            exec.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("out: " + out.toString().trim());
            System.out.println("err: " + err.toString().trim());
            try {
                out.close();
                err.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void advanced_test() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); ByteArrayOutputStream err = new ByteArrayOutputStream()) {
            final CommandLine cmdLine = new CommandLine("ping");
            cmdLine.addArgument("-c").addArgument("100").addArgument("www.baidu.com");
            DefaultExecutor exec = new DefaultExecutor();
            exec.setWorkingDirectory(new File("src/main/resources"));
            exec.setExitValue(0); // 明确脚本正常退出时的状态码
            exec.setWatchdog(new ExecuteWatchdog(5*1000)); // 5秒超时
            exec.setStreamHandler(new PumpStreamHandler(out, err));
            try {
                exec.execute(cmdLine);
            } catch (ExecuteException e) {
                e.printStackTrace(); // 脚本执行异常
            }
            System.out.println("out: " + out.toString().trim());
            System.out.println("err: " + err.toString().trim());
        } catch (IOException e) {
            e.printStackTrace(); // 输入输出异常
        }
    }

    @Test
    public void working_dir() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); ByteArrayOutputStream err = new ByteArrayOutputStream()) {
            final CommandLine cmdLine = new CommandLine("sh");
            cmdLine.addArgument("/Users/gujun/Github/java-study/example/src/main/resources/script.sh");
            DefaultExecutor exec = new DefaultExecutor();
            exec.setWorkingDirectory(new File("src/main/resources"));
            exec.setExitValue(0); // 明确脚本正常退出时的状态码
            exec.setWatchdog(new ExecuteWatchdog(5*1000)); // 5秒超时
            exec.setStreamHandler(new PumpStreamHandler(out, err));
            try {
                int code = exec.execute(cmdLine);
            } catch (ExecuteException e) {
                e.printStackTrace(); // 脚本执行异常
            }
            System.out.println("out: " + out.toString().trim());
            System.out.println("err: " + err.toString().trim());
        } catch (IOException e) {
            e.printStackTrace(); // 输入输出异常
        }
    }

}
