package de.mxro.maven.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class WriteHashes {

    public static void forFile(final Path baseFile) throws Exception {
        writeMd5(baseFile);
        writeSh1(baseFile);
    }

    public static String getMd5ForFile(final File file) {
        String value = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

            value = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return value;
    }

    public static void writeMd5(final Path baseFile) throws NoSuchAlgorithmException, IOException {

        final Path md5File = baseFile.getFileSystem().getPath(baseFile.toString() + ".md5");

        Files.write(md5File, getMd5ForFile(baseFile.toFile()).getBytes("UTF-8"));
        // System.out.println(getMd5ForFile(baseFile.toFile()));
        // System.out.println(Spawn.sh("mvn checksum:file -Dfile=" +
        // baseFile.toFile().getAbsolutePath()));
        // final byte[] md5 =
        // MessageDigest.getInstance("MD5").digest(Files.readAllBytes(baseFile));
        //
        // final StringBuffer sb = new StringBuffer();
        // for (int i = 0; i < md5.length; i++) {
        // sb.append(Integer.toString((md5[i] & 0xff) + 0x100,
        // 16).substring(1));
        // }
        //
        // final StringBuffer hexString = new StringBuffer();
        // for (int i = 0; i < md5.length; i++) {
        // final String hex = Integer.toHexString(0xff & md5[i]);
        // if (hex.length() == 1) {
        // hexString.append('0');
        // }
        // hexString.append(hex);
        // }
        //
        // final Path md5File =
        // baseFile.getFileSystem().getPath(baseFile.toString() + ".md5");
        //
        // Files.write(md5File, hexString.toString().getBytes("UTF-8"));

    }

    public static void writeSh1(final Path baseFile) throws NoSuchAlgorithmException, IOException {

        final byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(Files.readAllBytes(baseFile));

        final Path sha1File = baseFile.getFileSystem().getPath(baseFile.toString() + ".sh1");

        Files.write(sha1File, sha1);
    }
}
