package com.github.andyshao.asm;

import org.objectweb.asm.Opcodes;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 5, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public enum Version {
    V1_1(Opcodes.V1_1) , V1_2(Opcodes.V1_2) , V1_3(Opcodes.V1_3) , V1_4(Opcodes.V1_4) , V1_5(Opcodes.V1_5) ,
    V1_6(Opcodes.V1_6) , V1_7(Opcodes.V1_7) , V1_8(Opcodes.V1_8);
    private int version;

    private Version(int version) {
        this.version = version;
    }

    public int getVersion() {
        return this.version;
    }
}
