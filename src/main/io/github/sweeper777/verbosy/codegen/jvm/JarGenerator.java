package io.github.sweeper777.verbosy.codegen.jvm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarGenerator {
    public static void generateJar(byte[] verbosyProgramClass, OutputStream output) throws IOException {
        var manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "VerbosyProgram");
        try (var jar = new JarOutputStream(output, manifest)) {
            addEntryToJar("VerbosyProgram.class", new ByteArrayInputStream(verbosyProgramClass), jar);
            addVerbosyRuntimeClassToJar("/Util.class", jar);
            addVerbosyRuntimeClassToJar("/VerbosyValue.class", jar);
        }
    }

    private static void addEntryToJar(String name, InputStream content, JarOutputStream jar) throws IOException {
        var entry = new JarEntry(name);
        entry.setTime(Instant.now().toEpochMilli());
        jar.putNextEntry(entry);
        if (content != null) {
            content.transferTo(jar);
        }
        jar.closeEntry();
    }

}
