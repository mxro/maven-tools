package de.mxro.maven.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
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
            org.bouncycastle.util.encoders.Hex.encode(arg0)
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

        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(Files.readAllBytes(baseFile), offset, len);
        final byte[] md5 = messageDigest.digest();

        final Path md5File = baseFile.getFileSystem().getPath(baseFile.toString() + ".md5");

        Files.write(md5File, Hex.encode());

    }

    public static void writeSh1(final Path baseFile) throws NoSuchAlgorithmException, IOException {

        final byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(Files.readAllBytes(baseFile));

        final Path sha1File = baseFile.getFileSystem().getPath(baseFile.toString() + ".sh1");

        Files.write(sha1File, sha1);
    }
}
