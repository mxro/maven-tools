package de.mxro.maven.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Hex;

import de.mxro.file.Jre.FilesJre;

public class WriteHashes {

    public static void forFile(final Path baseFile) throws Exception {
        writeMd5(baseFile);
        writeSh1(baseFile);
    }

    public static void writeMd5(final Path baseFile) throws NoSuchAlgorithmException, IOException {
        final FileInputStream fis = new FileInputStream(baseFile.toFile());

        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        final byte[] buffer = new byte[1024];
        int size = fis.read(buffer, 0, 1024);
        while (size >= 0) {
            messageDigest.update(buffer, 0, size);
            size = fis.read(buffer, 0, 1024);
        }

        final String result = new String(Hex.encode(messageDigest.digest()));

        fis.close();

        final Path md5File = baseFile.getFileSystem().getPath(baseFile.toString() + ".md5");

        FilesJre.wrap(md5File.toFile()).setText(result);
    }

    public static void writeSh1(final Path baseFile) throws NoSuchAlgorithmException, IOException {

        final byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(Files.readAllBytes(baseFile));

        final Path sha1File = baseFile.getFileSystem().getPath(baseFile.toString() + ".sh1");

        Files.write(sha1File, sha1);
    }
}
