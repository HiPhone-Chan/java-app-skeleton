package tech.hiphone.cms.utils;

import java.io.File;

import tech.hiphone.commons.utils.CmdUtil;

public class VideoUtil {

    public static void movflags(File from, File to) {
        StringBuilder cmdSb = new StringBuilder("ffmpeg -i ").append(from.getAbsolutePath())
                .append(" -movflags faststart -acodec copy -vcodec copy -y ").append(to.getAbsolutePath());
        try {
            CmdUtil.runCmd(cmdSb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
