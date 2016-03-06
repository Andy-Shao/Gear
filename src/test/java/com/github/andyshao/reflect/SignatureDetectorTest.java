package com.github.andyshao.reflect;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.Opcodes;

import com.github.andyshao.reflect.SignatureDetector.ClassSignature;

public class SignatureDetectorTest {
    private SignatureDetector detector;

    @Before
    public void befor() {
        this.detector = new SignatureDetector(Opcodes.ASM5);
    }

    @Test
    public void testForIterable() throws IOException {
        ClassSignature classSignature = this.detector.getSignature(Iterable.class);
        Assert.assertThat(classSignature.classSignature , Matchers.is("<T:Ljava/lang/Object;>Ljava/lang/Object;"));
    }
}
