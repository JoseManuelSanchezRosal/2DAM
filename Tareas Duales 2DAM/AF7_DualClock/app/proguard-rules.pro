# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to the flags specified in
# ${sdk.dir}/tools/proguard/proguard-android-optimize.txt
# You can edit this file to add custom rules.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If you are using some libraries which use reflection, you might want to
# uncomment the following lines and add the fully qualified class name.
#-keep class com.example.MyClass
#-keep class com.example.MyClass { *; }

# If you want to keep all members of a class, you can use the following syntax.
#-keep public class com.example.MyClass { public *; }

# If you want to keep all public classes and members, you can use this syntax.
#-keep public class * { public *; }

# Specifies to write out a mapping file, which lists the obfuscated names for
# classes, methods, and fields. This can be useful for debugging crashes.
-printmapping build/outputs/mapping/release/mapping.txt
