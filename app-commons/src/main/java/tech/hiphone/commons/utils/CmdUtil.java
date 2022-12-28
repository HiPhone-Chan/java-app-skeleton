package tech.hiphone.commons.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmdUtil {

    private static final Logger log = LoggerFactory.getLogger(CmdUtil.class);

    public static void runCmd(String cmd) {
        try {
            log.info("Execute cmd {}", cmd);
            Process process = Runtime.getRuntime().exec(cmd);

            String line;
            try (BufferedInputStream bis = new BufferedInputStream(process.getErrorStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(bis));) {
                while ((line = br.readLine()) != null) {
                    log.warn(line);
                }
            }

            try (BufferedInputStream bis = new BufferedInputStream(process.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(bis));) {
                while ((line = br.readLine()) != null) {
                    log.debug(line);
                }
            }

            process.waitFor();
            if (process.exitValue() != 0) {
                log.warn("exitValue: {}", process.exitValue());
            }
            log.info("Execute finish");
        } catch (Exception e) {
            log.warn("Execute cmd error", e);
        }
    }

}
