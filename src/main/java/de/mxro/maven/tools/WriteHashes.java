package de.mxro.maven.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WriteHashes {

    public static void forFile(final Path baseFile) throws Exception {
        writeMd5(baseFile);
        writeSh1(baseFile);
    }

    public static void writeMd5(final Path baseFile) throws NoSuchAlgorithmException, IOException {
        final byte[] md5 = MessageDigest.getInstance("MD5").digest(Files.readAllBytes(baseFile));

        final Path md5File = baseFile.getFileSystem().getPath(baseFile.toString() + ".md5");

        Files.write(md5File, md5);

    }

    public static void writeSh1(final Path baseFile) throws NoSuchAlgorithmException, IOException {

        final byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(Files.readAllBytes(baseFile));

        final Path sha1File = baseFile.getFileSystem().getPath(baseFile.toString() + ".sh1");

        Files.write(sha1File, sha1);
    }
}
