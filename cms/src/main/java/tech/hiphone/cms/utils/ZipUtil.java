package tech.hiphone.cms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import tech.hiphone.framework.utils.FileUtil;

// zip文件操作
public class ZipUtil {

    private static final String ZIP_SUFFIX = ".zip";

    /**
     * 文件压缩
     * @param rawFile 压缩前的文件(夹)
     * @param zipFile 压缩后的文件 .zip
     */
    public static File zip(File rawFile, File zipFile, boolean keepDirStructure) {
        try (OutputStream fos = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {
            zip(rawFile, zos, rawFile.getName(), keepDirStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipFile;
    }

    public static File zip(File rawFile, File zipFile) {
        return zip(rawFile, zipFile, true);
    }

    public static File zip(File rawFile) {
        File zipFile = null;
        if (rawFile.isFile()) {
            zipFile = new File(rawFile.getParentFile(), FileUtil.getNameWithNoSuffix(rawFile) + ZIP_SUFFIX);
        } else if (rawFile.isDirectory()) {
            zipFile = new File(rawFile.getParentFile(), rawFile.getName() + ZIP_SUFFIX);
        }
        if (zipFile == null) {
            return null;
        }
        return zip(rawFile, zipFile, true);
    }

    /**
    * 文件解压
    * @param zipFile 被解压的文件
    * @param dstDir 解压到的目录
    * @param keepDirStructure
    */
    public static File unzip(File zipFile, File dstDir) {
        try (ZipFile zip = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> zipEntries = zip.entries();
            while (zipEntries.hasMoreElements()) {
                ZipEntry zipEntry = zipEntries.nextElement();
                String zipEntryName = zipEntry.getName();
                File file = new File(dstDir, zipEntryName);
                File parent = file.getParentFile();

                if (!parent.exists()) {
                    parent.mkdirs();
                }

                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    IOUtils.copy(zip.getInputStream(zipEntry), new FileOutputStream(file));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dstDir;
    }

    public static File unzip(File zipFile) {
        File parent = zipFile.getParentFile();
        return unzip(zipFile, parent);
    }

    /**
     * 
     * @param rawFile
     * @param zos
     * @param fileName
     * @param keepDirStructure 保留目录结构
     * @throws IOException
     */
    private static void zip(File rawFile, ZipOutputStream zos, String fileName, boolean keepDirStructure)
            throws IOException {
        if (rawFile.isFile()) {
            zos.putNextEntry(new ZipEntry(fileName));
            try (InputStream fis = new FileInputStream(rawFile)) {
                IOUtils.copy(fis, zos);
            }
            zos.closeEntry();
        } else if (rawFile.isDirectory()) {
            File[] files = rawFile.listFiles();
            if (ArrayUtils.isEmpty(files)) {
                if (keepDirStructure) { // 空文件夹处理
                    zos.putNextEntry(new ZipEntry(fileName + File.separator));
                    zos.closeEntry();
                }
            } else {
                for (File fileItem : files) {
                    if (keepDirStructure) {
                        zip(fileItem, zos, fileName + File.separator + fileItem.getName(), keepDirStructure);
                    } else {
                        zip(fileItem, zos, fileItem.getName(), keepDirStructure);
                    }
                }
            }
        }
    }

}
