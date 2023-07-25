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
    /**java 1.1*/
    V1_1(Opcodes.V1_1) ,
    /**java 1.2*/
    V1_2(Opcodes.V1_2) ,
    /**java 1.3*/
    V1_3(Opcodes.V1_3) ,
    /**java 1.4*/
    V1_4(Opcodes.V1_4) ,
    /**java 1.5*/
    V1_5(Opcodes.V1_5) ,
    /**java 1.6*/
    V1_6(Opcodes.V1_6) ,
    /**java 1.7*/
    V1_7(Opcodes.V1_7) ,
    /**java 8*/
    V1_8(Opcodes.V1_8),
    /**java 9*/
    V9(Opcodes.V9),
    /**java 10*/
    V10(Opcodes.V10),
    /**java 11*/
    V11(Opcodes.V11),
    /**java 12*/
    V12(Opcodes.V12),
    /**java 13*/
    V13(Opcodes.V13),
    /**java 14*/
    V14(Opcodes.V14),
    /**java 15*/
    V15(Opcodes.V15),
    /**java 16*/
    V16(Opcodes.V16),
    /**java 17*/
    V17(Opcodes.V17)
    ;
    private int version;

    private Version(int version) {
        this.version = version;
    }

    /**
     * get Version
     * @return version number
     */
    public int getVersion() {
        return this.version;
    }
}
