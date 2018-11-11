package org.easyarch.xbuffer.kernel.util;

import org.easyarch.xbuffer.kernel.XConfig;
import org.easyarch.xbuffer.kernel.env.Settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by xingtianyu on 2018/11/12.
 */
public class FileUtil {

    public static Long maxFileNameNum(String dataDir) throws IOException {
        List<Long> nums = Files.list(Paths.get(dataDir)).filter(path -> {
            boolean flag = path.getFileName().toFile().getName().endsWith(XConfig.dataSuffix);
            return flag;
        }).map(path -> {
            String fileName = path.toFile().getName();
            String[] prefix = fileName.split(XConfig.dataSuffix);
            String[] segment = prefix[0].split("-");
            return Long.valueOf(segment[1]);
        }).collect(Collectors.toList());
        return Collections.max(nums);
    }

    public static String maxFileName(String dataDir) throws IOException {
        Long max = maxFileNameNum(dataDir);
        return String.format(XConfig.DATA_FILE_NAME,max);
    }

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings("/Users/xingtianyu/IdeaProjects/xbuffer/xbuffer-kernel/src/main/resources/xbuffer.yml");
        XConfig.init(settings);
        System.out.println(maxFileName("djtracker-mq"));
    }
}
