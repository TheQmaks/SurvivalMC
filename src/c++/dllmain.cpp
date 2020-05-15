#include "jni.h"
#include "jvmti.h"
#include "classes.h"
#include "windows.h"

typedef const char *(*GetClassNameUTF)(JNIEnv *env, jclass cb);
typedef jint(*GetCreatedJavaVMs)(JavaVM **vmBuf, jsize bufLen, jsize *nVMs);
typedef jclass(*FindClassFromCaller)(JNIEnv* env, const char* name, jboolean init, jobject loader, jclass caller);
typedef jclass(*DefineClass)(JNIEnv *env, const char *name, jobject loader, const jbyte *buf, jsize len, jobject pd);

#define getClass(name) {findClassFromCaller(jniEnv, #name, false, classLoader, java_lang_ClassLoader), sizeof(##name) / sizeof(##name[0]), ##name}

void inject() {
	HMODULE jvm = GetModuleHandleA("jvm");
	DefineClass defineClass = (DefineClass)GetProcAddress(jvm, "JVM_DefineClass");
	GetCreatedJavaVMs getVMs = (GetCreatedJavaVMs) GetProcAddress(jvm, "JNI_GetCreatedJavaVMs");
	GetClassNameUTF getClassName = (GetClassNameUTF) GetProcAddress(jvm, "JVM_GetClassNameUTF");
	FindClassFromCaller findClassFromCaller = (FindClassFromCaller) GetProcAddress(jvm, "JVM_FindClassFromCaller");

	jsize vmCount = 1;
	JavaVM** buffer = new JavaVM*[vmCount];
	getVMs(buffer, 1, &vmCount);

	JNIEnv* jniEnv;
	jvmtiEnv* jvmtiEnv;

	int result = buffer[0]->GetEnv((void**)&jniEnv, JNI_VERSION_1_8);
	if (result == JNI_EDETACHED) {
		buffer[0]->AttachCurrentThread((void**)&jniEnv, NULL);
	}
	buffer[0]->GetEnv((void**)&jvmtiEnv, JVMTI_VERSION_1_2);

	delete[] buffer;

	jvmtiCapabilities caps = {0};
	caps.can_redefine_classes = 1;
	jvmtiEnv->AddCapabilities(&caps);

	jobject classLoader = NULL;

	jint classesCount;
	jclass *loadedClasses;
	jvmtiEnv->GetLoadedClasses(&classesCount, &loadedClasses);
	for (int i = 0; i < classesCount; i++) {
		jvmtiEnv->GetClassLoader(loadedClasses[i], &classLoader);
		if (classLoader && strstr(getClassName(jniEnv, jniEnv->GetObjectClass(classLoader)), "minecraft")) {
			break;
		}
	}

	jvmtiEnv->Deallocate((unsigned char*) loadedClasses);

	jmethodID getProtectionDomain = jniEnv->GetMethodID(jniEnv->FindClass("java/lang/Class"), "getProtectionDomain", "()Ljava/security/ProtectionDomain;");
	jobject protectionDomain = jniEnv->CallObjectMethod(jniEnv->GetObjectClass(classLoader), getProtectionDomain);

	defineClass(jniEnv, "H2Eng", classLoader, H2Eng, sizeof(H2Eng) / sizeof(H2Eng[0]), protectionDomain);

	jclass java_lang_ClassLoader = jniEnv->FindClass("java/lang/ClassLoader");
	jvmtiClassDefinition classes[] = {
		getClass(aaj),
		getClass(ahsdq),
		getClass(amj),
		getClass(atk),
		getClass(axz),
		getClass(ayd),
		getClass(ayg),
		getClass(ayk),
		getClass(azv),
		getClass(bak),
		getClass(um),
		getClass(bcd)
	};
	jvmtiEnv->RedefineClasses(sizeof(classes) / sizeof(jvmtiClassDefinition), classes);
}

BOOL APIENTRY DllMain(HMODULE hModule, DWORD dwReason, LPVOID lpReserved) {
	DisableThreadLibraryCalls(hModule);

	switch (dwReason)
	{
		case DLL_PROCESS_ATTACH:
			CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)inject, NULL, 0, NULL);
			break;
	}

	return TRUE;
}
