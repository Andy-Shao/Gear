Some thing about the useful tools of the **JDK 1.8**.
I find out some API is not very well in Java Core API.
I try to repair it with my experience and shall with you.
If there are any problems then send the email to **hj13035952619@126.com**
###############################################################################

* must be JDK1.8 or up.
* must be Maven3 or up.

If you want to let it into the eclipse. Try to use the command:
mvn eclipse:eclipse

You could download from the maven repository, now!<br>
&lt;dependency&gt;<br>
	&lt;groupId&gt;com.github.Andy-Shao&lt;/groupId&gt;<br>
	&lt;artifactId&gt;Gear&lt;/artifactId&gt;<br>
	&lt;version&gt;${Gear.Version.Number}&lt;/version&gt;<br>
&lt;/dependency&gt;<br>
###############################################################################
**What the updating in v3.2.1**

Adding get Generic Type ways<br>
Adding isPrimitiveObject() and isPrimitiveType() in ClassOperation<br>
Adding getParamNamesByAnnotation() in ParameterOperation<br>

**What the updating in v3.2**

Adding two new children projection GearEE and GearEE-Spring in System<br>
adding new package com.github.andyshao.asm<br>
Moving to asm-all<br>
Adding -jvm [--type|--method] commands<br>
Redesign reflecting - separate Reflects class<br> 
Fix bug in Reflects.getMethodParamNames()<br>
Rewriting ArrayOperation.getValue()<br>

**What the updating in v3.1**

cglib 3.2.0-&gt;3.2.1, org.ow2.asm 5.0.4-&gt;5.1<br>
Removing src/main/java/com/github/andyshao/util/ComparatorOperation.java<br>
Adding getMethodParamNames() in Reflects<br>
Adding HttpTask in system package<br>
Adding BlokingTcpServer and others services in TCP/IP<br>

**What the updating in v3.0**

Rewriting the Proxy interfaces<br>
Moving org.hamcrest from 1.1 to 1.3<br>
Moving junit from 4.7 to 4.12<br>
Moving cglib from 2.2 to 3.1<br>
Moving asm from 3.3.1 to 5.0.4<br>
Fix the bug of ArrayWrapper<br>
...
