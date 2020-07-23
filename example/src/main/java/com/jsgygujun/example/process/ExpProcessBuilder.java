package com.jsgygujun.example.process;


import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author jsgygujun@gmail.com
 * @since 2020/7/23 2:28 下午
 */
public class ExpProcessBuilder {
    @Test
    public void process_builder() {
        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "/opt/script.sh");
        File log = new File("/opt/script.log");
        pb.directory(new File("/opt"));
        // 重定向标准输出和标准错误到日志文件
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log)); // 重定向子进程的标准输出
        try {
            Process p = pb.start(); // pb示例可以重复使用
            System.out.println("start----1");
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        try {
            Process p = pb.start(); // pb示例可以重复使用
            System.out.println("start----2");
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
